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
            jgen.writeString(value.toEpochMilliseconds().toString())
    }

    class KotlinInstantDeserializer : JsonDeserializer<Instant>() {
        override fun deserialize(p: JsonParser, p1: DeserializationContext): Instant =
            Instant.fromEpochMilliseconds((p.codec.readTree(p) as JsonNode).asText().toLong())
    }
}
