package spp.protocol.util

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import kotlinx.datetime.Instant
import kotlinx.serialization.ContextualSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.SerializersModule
import spp.protocol.instrument.LiveVariable
import java.util.concurrent.atomic.AtomicInteger

/**
 * Used to serialize/deserialize Kotlin classes.
 *
 * @since 0.1.0
 */
object KSerializers {

    val json = Json {
        encodeDefaults = true
        serializersModule = SerializersModule {
            contextual(LiveVariable::class, LiveVariable.serializer())
            contextual(String::class, String.serializer())
            contextual(Long::class, Long.serializer())
            contextual(Int::class, Int.serializer())
            contextual(AtomicInteger::class, AtomicIntegerKSerializer())
            contextual(Any::class, AnySerializer())
        }
    }

    class KotlinInstantSerializer : JsonSerializer<Instant>() {
        override fun serialize(value: Instant, jgen: JsonGenerator, provider: SerializerProvider) =
            jgen.writeNumber(value.toEpochMilliseconds())
    }

    class KotlinInstantDeserializer : JsonDeserializer<Instant>() {
        override fun deserialize(p: JsonParser, p1: DeserializationContext): Instant =
            Instant.fromEpochMilliseconds((p.codec.readTree(p) as JsonNode).longValue())
    }

    class AtomicIntegerKSerializer : KSerializer<AtomicInteger> {
        override val descriptor = PrimitiveSerialDescriptor(
            "spp.protocol.util.AtomicIntegerKSerializer",
            PrimitiveKind.INT
        )

        override fun deserialize(decoder: Decoder) = AtomicInteger(decoder.decodeInt())
        override fun serialize(encoder: Encoder, value: AtomicInteger) = encoder.encodeInt(value.get())
    }

    class AnySerializer : KSerializer<Any> {
        override val descriptor: SerialDescriptor =
            ContextualSerializer(Any::class, null, emptyArray()).descriptor

        override fun deserialize(decoder: Decoder): Any {
            val json = decoder as? JsonDecoder
                ?: throw IllegalStateException("Only JsonDecoder is supported.")
            return fromJson(json.decodeJsonElement())!!
        }

        override fun serialize(encoder: Encoder, value: Any) {
            val json = encoder as? JsonEncoder
                ?: throw IllegalStateException("Only JsonEncoder is supported.")
            json.encodeJsonElement(toJson(value))
        }

        fun toJson(item: Any?): JsonElement = when (item) {
            null -> JsonNull
            is String -> JsonPrimitive(item)
            is Number -> JsonPrimitive(item)
            is Boolean -> JsonPrimitive(item)
            is Map<*, *> -> {
                val content = item.map { (k, v) -> k.toString() to toJson(v) }
                JsonObject(content.toMap())
            }
            is List<*> -> {
                val content = item.map { toJson(it) }
                JsonArray(content)
            }
            is JsonElement -> item
            is LiveVariable -> json.encodeToJsonElement(LiveVariable.serializer(), item)
            else -> throw IllegalArgumentException("Unable to encode $item")
        }

        fun fromJson(item: JsonElement): Any? = when (item) {
            JsonNull -> null
            is JsonPrimitive -> when {
                item.isString -> item.content
                item.content == "true" || item.content == "false" -> {
                    item.content == "true"
                }
                item.content.contains('.') -> item.content.toDouble()
                else -> item.content.toLong()
            }
            is JsonObject -> item.mapValues { fromJson(it.value) }
            is JsonArray -> item.map { fromJson(it) }
            else -> throw IllegalArgumentException("Unable to decode $item")
        }
    }
}
