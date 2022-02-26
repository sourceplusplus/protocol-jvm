/*
 * Source++, the open-source live coding platform.
 * Copyright (C) 2022 CodeBrig, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package spp.protocol.marshall

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.guava.GuavaModule
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.core.json.jackson.DatabindCodec
import kotlinx.datetime.Instant
import spp.protocol.artifact.ArtifactQualifiedName
import spp.protocol.artifact.exception.LiveStackTrace
import spp.protocol.artifact.log.LogCountSummary
import spp.protocol.artifact.trace.TraceResult
import spp.protocol.instrument.*
import spp.protocol.instrument.command.CommandType
import spp.protocol.instrument.command.LiveInstrumentCommand
import spp.protocol.instrument.event.LiveBreakpointHit
import spp.protocol.instrument.event.LiveInstrumentRemoved
import spp.protocol.platform.developer.SelfInfo
import spp.protocol.platform.general.Service
import spp.protocol.platform.status.ActiveInstance
import spp.protocol.view.LiveViewSubscription
import java.util.*

/**
 * Used for marshalling and unmarshalling protocol messages.
 *
 * @since 0.2.1
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
object ProtocolMarshaller {
    init {
        try {
            DatabindCodec.mapper().registerModule(GuavaModule())
            DatabindCodec.mapper().registerModule(Jdk8Module())
            DatabindCodec.mapper().registerModule(JavaTimeModule())
            DatabindCodec.mapper().registerModule(KotlinModule())
            DatabindCodec.mapper().enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
            DatabindCodec.mapper().enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
            DatabindCodec.mapper().enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
        } catch (ignore: Throwable) {
        }
    }

    @JvmStatic
    fun serializeArtifactQualifiedName(value: ArtifactQualifiedName): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeArtifactQualifiedName(value: JsonObject): ArtifactQualifiedName {
        return value.mapTo(ArtifactQualifiedName::class.java)
    }

    @JvmStatic
    fun serializeTraceResult(value: TraceResult): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeTraceResult(value: JsonObject): TraceResult {
        return value.mapTo(TraceResult::class.java)
    }

    @JvmStatic
    fun serializeInstant(value: Instant): String {
        return value.toEpochMilliseconds().toString()
    }

    @JvmStatic
    fun deserializeInstant(value: String): Instant {
        return Instant.fromEpochMilliseconds(value.toLong())
    }

    @JvmStatic
    fun serializeLogCountSummary(value: LogCountSummary): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeLogCountSummary(value: JsonObject): LogCountSummary {
        return value.mapTo(LogCountSummary::class.java)
    }

    @JvmStatic
    fun serializeLiveInstrument(value: LiveInstrument): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeLiveInstrument(value: JsonObject): LiveInstrument {
        return if (value.getString("type") == "BREAKPOINT") {
            value.mapTo(LiveBreakpoint::class.java)
        } else if (value.getString("type") == "LOG") {
            value.mapTo(LiveLog::class.java)
        } else if (value.getString("type") == "METER") {
            value.mapTo(LiveMeter::class.java)
        } else if (value.getString("type") == "SPAN") {
            value.mapTo(LiveSpan::class.java)
        } else {
            throw UnsupportedOperationException("Live instrument type: " + value.getString("type"))
        }
    }

    @JvmStatic
    fun serializeLiveBreakpoint(value: LiveBreakpoint): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeLiveBreakpoint(value: JsonObject): LiveBreakpoint {
        return value.mapTo(LiveBreakpoint::class.java)
    }

    @JvmStatic
    fun serializeLiveLog(value: LiveLog): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeLiveLog(value: JsonObject): LiveLog {
        return value.mapTo(LiveLog::class.java)
    }

    @JvmStatic
    fun serializeLiveMeter(value: LiveMeter): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeLiveMeter(value: JsonObject): LiveMeter {
        return value.mapTo(LiveMeter::class.java)
    }

    @JvmStatic
    fun serializeLiveSpan(value: LiveSpan): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeLiveSpan(value: JsonObject): LiveSpan {
        return value.mapTo(LiveSpan::class.java)
    }

    @JvmStatic
    fun serializeLiveSourceLocation(value: LiveSourceLocation): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeLiveSourceLocation(value: JsonObject): LiveSourceLocation {
        return value.mapTo(LiveSourceLocation::class.java)
    }

    @JvmStatic
    fun serializeLiveViewSubscription(value: LiveViewSubscription): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeLiveViewSubscription(value: JsonObject): LiveViewSubscription {
        return value.mapTo(LiveViewSubscription::class.java)
    }

    @JvmStatic
    fun serializeSelfInfo(value: SelfInfo): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeSelfInfo(value: JsonObject): SelfInfo {
        return value.mapTo(SelfInfo::class.java)
    }

    @JvmStatic
    fun serializeService(value: Service): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeService(value: JsonObject): Service {
        return value.mapTo(Service::class.java)
    }

    @JvmStatic
    fun serializeActiveInstance(value: ActiveInstance): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeActiveInstance(value: JsonObject): ActiveInstance {
        return value.mapTo(ActiveInstance::class.java)
    }

    @JvmStatic
    fun serializeLiveInstrumentCommand(value: LiveInstrumentCommand): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeLiveInstrumentCommand(value: JsonObject): LiveInstrumentCommand {
        return LiveInstrumentCommand(
            CommandType.valueOf(value.getString("commandType")),
            value.getJsonArray("instruments").list.map {
                if (it is JsonObject) {
                    deserializeLiveInstrument(it)
                } else {
                    deserializeLiveInstrument(JsonObject.mapFrom(it))
                }
            }.toSet(),
            value.getJsonArray("locations").list.map {
                if (it is JsonObject) {
                    deserializeLiveSourceLocation(it)
                } else {
                    deserializeLiveSourceLocation(JsonObject.mapFrom(it))
                }
            }.toSet()
        )
    }

    @JvmStatic
    fun serializeLiveInstrumentRemoved(value: LiveInstrumentRemoved): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeLiveInstrumentRemoved(value: JsonObject): LiveInstrumentRemoved {
        return LiveInstrumentRemoved(
            deserializeLiveInstrument(value.getJsonObject("liveInstrument")),
            value.let {
                if (it.getValue("occurredAt") is Number) {
                    Instant.fromEpochMilliseconds(value.getLong("occurredAt"))
                } else {
                    Instant.fromEpochSeconds(
                        value.getJsonObject("occurredAt").getLong("epochSeconds"),
                        value.getJsonObject("occurredAt").getInteger("nanosecondsOfSecond")
                    )
                }
            },
            value.getJsonObject("cause")?.let { Json.decodeValue(it.toString(), LiveStackTrace::class.java) }
        )
    }

    @JvmStatic
    fun setupCodecs(vertx: Vertx) {
        vertx.eventBus().registerDefaultCodec(LiveBreakpointHit::class.java, ProtocolMessageCodec())
    }

    class ProtocolMessageCodec<T> : MessageCodec<T, T> {
        override fun encodeToWire(buffer: Buffer?, s: T?) {
            val value = Json.encode(s).toByteArray()
            buffer?.appendInt(value.size)
            buffer?.appendBytes(value)
        }

        override fun decodeFromWire(pos: Int, buffer: Buffer?): T? {
            val len = buffer?.getInt(pos) ?: return null
            val bytes = buffer.getBytes(pos + 4, pos + 4 + len)
            return cast(Json.decodeValue(String(bytes)))
        }

        @Suppress("UNCHECKED_CAST")
        private fun <T> cast(obj: Any?): T? {
            return obj as? T
        }

        override fun transform(o: T): T = o
        override fun name(): String = UUID.randomUUID().toString()
        override fun systemCodecID(): Byte = -1
    }
}
