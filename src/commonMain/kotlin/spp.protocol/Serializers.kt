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
package spp.protocol

import spp.protocol.artifact.ArtifactType
import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Used to serialize/deserialize protocol messages.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
class Serializers {

    /**
     * Used to serialize/deserialize [Instant] classes.
     *
     * @since 0.1.0
     */
    class InstantKSerializer : KSerializer<Instant> {

        override val descriptor = PrimitiveSerialDescriptor(
            "spp.protocol.InstantKSerializer",
            PrimitiveKind.LONG
        )

        override fun deserialize(decoder: Decoder) = Instant.fromEpochMilliseconds(decoder.decodeLong())
        override fun serialize(encoder: Encoder, value: Instant) = encoder.encodeLong(value.toEpochMilliseconds())
    }

    /**
     * Used to serialize/deserialize [ArtifactType] classes.
     *
     * @since 0.1.0
     */
    class ArtifactTypeSerializer : KSerializer<ArtifactType> {

        override val descriptor = PrimitiveSerialDescriptor(
            "spp.protocol.ArtifactTypeSerializer",
            PrimitiveKind.STRING
        )

        override fun deserialize(decoder: Decoder) = ArtifactType.valueOf(decoder.decodeString())
        override fun serialize(encoder: Encoder, value: ArtifactType) = encoder.encodeString(value.name)
    }
}
