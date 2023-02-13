/*
 * Source++, the continuous feedback platform for developers.
 * Copyright (C) 2022-2023 CodeBrig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spp.protocol.service.extend

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
import spp.protocol.platform.auth.RolePermission
import spp.protocol.service.error.InstrumentAccessDenied
import spp.protocol.service.error.LiveInstrumentException
import spp.protocol.service.error.LiveInstrumentException.ErrorType
import spp.protocol.service.error.PermissionAccessDenied

open class TCPServiceFrameParser(
    private val vertx: Vertx,
    private val socket: NetSocket
) : Handler<AsyncResult<JsonObject>> {

    override fun handle(event: AsyncResult<JsonObject>) {
        if (event.failed()) {
            return
        }
        val frame = event.result()

        if (frame.getString("replyAddress") != null) {
            val deliveryOptions = DeliveryOptions()
            frame.getJsonObject("headers").fieldNames().forEach {
                deliveryOptions.addHeader(it, frame.getJsonObject("headers").getString(it))
            }
            vertx.eventBus().request<Any>(
                frame.getString("address"), frame.getJsonObject("body"), deliveryOptions
            ).onComplete {
                if (it.succeeded()) {
                    FrameHelper.sendFrame(
                        BridgeEventType.SEND.name.lowercase(),
                        frame.getString("replyAddress"),
                        it.result().body(),
                        socket
                    )
                } else {
                    val replyException = it.cause() as ReplyException
                    FrameHelper.writeFrame(
                        JsonObject()
                            .put("type", BridgeEventType.SEND.name.lowercase())
                            .put("address", frame.getString("replyAddress"))
                            .put("failureCode", replyException.failureCode())
                            .put("failureType", replyException.failureType().name)
                            .put("message", replyException.message),
                        socket
                    )
                }
            }
        } else if (frame.getString("address") != null) {
            val body = frame.getValue("body")
            if (body == null && frame.getString("message")?.startsWith("EventBusException:") == true) {
                handleErrorFrame(frame)
            } else if (body is JsonObject && body.getString("message")?.startsWith("EventBusException:") == true) {
                handleErrorFrame(body.put("address", frame.getString("address")))
            } else if (frame.getString("type") == "err") {
                handleErrorFrame(frame)
            } else {
                vertx.eventBus().publish(frame.getString("address"), body)
            }
        } else if (frame.getString("type") == "pong") {
            //no-op
        } else if (frame.getString("message") == "blocked by bridgeEvent handler") {
            //authentication error, disconnect
            socket.close()
        } else {
            //directly thrown event bus exceptions
            //todo: nothing catches this exception, it is just logged
            throw ReplyException(ReplyFailure.ERROR, frame.getString("message"))
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

                "InstrumentAccessDenied" -> error.initCause(
                    InstrumentAccessDenied(exceptionParams)
                )

                "PermissionAccessDenied" -> error.initCause(
                    PermissionAccessDenied(RolePermission.valueOf(exceptionParams))
                )

                else -> error.initCause(
                    Exception(exceptionMessage)
                )
            }
            vertx.eventBus().publish(frame.getString("address"), error)
        } else if (frame.getString("type") == "err") {
            val error = ReplyException(
                ReplyFailure.valueOf(frame.getString("failureType")),
                frame.getInteger("failureCode") ?: 500,
                frame.getString("message")
            )
            vertx.eventBus().publish(frame.getString("address"), error)
        } else {
            throw UnsupportedOperationException(frame.toString())
        }
    }
}
