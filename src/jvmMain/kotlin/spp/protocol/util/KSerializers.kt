package spp.protocol.util

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import kotlinx.datetime.Instant

/**
 * Used to serialize/deserialize Kotlin classes.
 *
 * @since 0.1.0
 */
object KSerializers {
    class KotlinInstantSerializer : JsonSerializer<Instant>() {
        override fun serialize(value: Instant, jgen: JsonGenerator, provider: SerializerProvider) =
            jgen.writeNumber(value.toEpochMilliseconds())
    }

    class KotlinInstantDeserializer : JsonDeserializer<Instant>() {
        override fun deserialize(p: JsonParser, p1: DeserializationContext): Instant =
            Instant.fromEpochMilliseconds((p.codec.readTree(p) as JsonNode).longValue())
    }
}
