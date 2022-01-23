package spp.protocol.extend

import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.ReplyException
import io.vertx.core.eventbus.ReplyFailure
import io.vertx.core.json.JsonObject
import io.vertx.core.net.NetSocket
import io.vertx.ext.bridge.BridgeEventType
import io.vertx.ext.eventbus.bridge.tcp.impl.protocol.FrameHelper
import spp.protocol.auth.RolePermission
import spp.protocol.error.AccessDenied
import spp.protocol.error.JWTVerificationException
import spp.protocol.error.MissingRemoteException
import spp.protocol.service.error.InstrumentAccessDenied
import spp.protocol.service.error.LiveInstrumentException
import spp.protocol.service.error.LiveInstrumentException.ErrorType
import spp.protocol.service.error.PermissionAccessDenied

class TCPServiceFrameParser(val vertx: Vertx, val socket: NetSocket) : Handler<AsyncResult<JsonObject>> {

//    companion object {
//        private val log = LoggerFactory.getLogger(TCPServiceFrameParser::class.java)
//    }

    override fun handle(event: AsyncResult<JsonObject>) {
        if (event.failed()) {
            //log.error("Failed to receive frame", event.cause())
            return
        }
        val frame = event.result()
        //log.trace("Received frame: {}", frame)

        //todo: revisit this || after fixing below todo
        if ("message" == frame.getString("type") || "send" == frame.getString("type")) {
            if (frame.getString("replyAddress") != null) {
                val deliveryOptions = DeliveryOptions()
                frame.getJsonObject("headers").fieldNames().forEach {
                    deliveryOptions.addHeader(it, frame.getJsonObject("headers").getString(it))
                }
                vertx.eventBus().request<Any>(
                    frame.getString("address"),
                    frame.getJsonObject("body"),
                    deliveryOptions
                ).onComplete {
                    if (it.succeeded()) {
                        FrameHelper.sendFrame(
                            BridgeEventType.SEND.name.toLowerCase(),
                            frame.getString("replyAddress"),
                            JsonObject.mapFrom(it.result().body()),
                            socket
                        )
                    } else {
                        FrameHelper.sendFrame(
                            BridgeEventType.SEND.name.toLowerCase(),
                            frame.getString("replyAddress"),
                            JsonObject.mapFrom(it.cause()),
                            socket
                        )
                    }
                }
            } else {
                val body = frame.getValue("body")
                if (body is JsonObject) {
                    if (body.getString("message")?.startsWith("EventBusException:") == true) {
                        handleErrorFrame(body.put("address", frame.getString("address")))
                    } else {
                        if (body.fieldNames().size == 1 && body.containsKey("value")) {
                            //todo: understand why can't just re-send body like below
                            vertx.eventBus()
                                .send("local." + frame.getString("address"), body.getValue("value"))
                        } else {
                            vertx.eventBus()
                                .send("local." + frame.getString("address"), body)
                        }
                    }
                } else {
                    vertx.eventBus()
                        .send("local." + frame.getString("address"), body)
                }
            }
        } else if ("err" == frame.getString("type")) {
            //directly thrown event bus exceptions
            handleErrorFrame(frame)
        } else {
            throw UnsupportedOperationException(frame.toString())
        }
    }

    private fun handleErrorFrame(frame: JsonObject) {
        if (frame.getString("message")?.startsWith("EventBusException:") == true) {
            val rawFailure = frame.getString("rawFailure")
            val failureCode = frame.getInteger("failureCode") ?: 500
            val error = ReplyException(
                ReplyFailure.RECIPIENT_FAILURE,
                failureCode,
                rawFailure
            )

            val causeMessage = frame.getString("message")!!
            val exceptionType = causeMessage.substringAfter("EventBusException:")
                .substringBefore("[")
            val exceptionParams = causeMessage.substringAfter("[").substringBefore("]")
            val exceptionMessage = causeMessage.substringAfter("]: ").trimEnd()
            when (exceptionType) {
                "LiveInstrumentException" -> error.initCause(
                    LiveInstrumentException(ErrorType.valueOf(exceptionParams), exceptionMessage)
                )
                "InstrumentAccessDenied" -> error.initCause(InstrumentAccessDenied(exceptionParams))
                "PermissionAccessDenied" -> error.initCause(
                    PermissionAccessDenied(RolePermission.valueOf(exceptionParams))
                )
                else -> TODO()
            }
            vertx.eventBus()
                .send("local." + frame.getString("address"), error)
        } else {
            //i think these are service exceptions
            val error = ReplyException(
                ReplyFailure.RECIPIENT_FAILURE,
                frame.getInteger("failureCode"),
                frame.getString("rawFailure")
            )
            var debugInfo = JsonObject(frame.getString("rawFailure")).getJsonObject("debugInfo")
            if (frame.getString("message").contains("JWT")) {
                error.initCause(JWTVerificationException(frame.getString("message")))
            } else if (debugInfo == null) {
                debugInfo = JsonObject().put(
                    "causeMessage", JsonObject(frame.getString("message")).getString("message")
                )
            }

            if (debugInfo.getString("causeName") == MissingRemoteException::class.java.name) {
                error.initCause(MissingRemoteException(debugInfo.getString("causeMessage")))
            } else {
                val causeMessage = debugInfo.getString("causeMessage")
                if (causeMessage?.startsWith("EventBusException:") == true) {
                    val exceptionType = causeMessage.substringAfter("EventBusException:")
                        .substringBefore("[")
                    val exceptionParams = causeMessage.substringAfter("[").substringBefore("]")
                    val exceptionMessage = causeMessage.substringAfter("]: ").trimEnd()
                    when (exceptionType) {
                        LiveInstrumentException::class.simpleName -> {
                            error.initCause(
                                LiveInstrumentException(
                                    ErrorType.valueOf(exceptionParams),
                                    exceptionMessage
                                )
                            )
                        }
                        InstrumentAccessDenied::class.simpleName -> {
                            error.initCause(InstrumentAccessDenied(exceptionParams))
                        }
                        AccessDenied::class.simpleName -> {
                            error.initCause(AccessDenied(exceptionParams))
                        }
                        else -> TODO()
                    }
                } else {
                    TODO()
                }
            }
            vertx.eventBus()
                .send("local." + frame.getString("address"), error)
        }
    }
}
