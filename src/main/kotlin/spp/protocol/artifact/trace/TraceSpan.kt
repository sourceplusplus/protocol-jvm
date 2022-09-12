/*
 * Source++, the open-source live coding platform.
 * Copyright (C) 2022 CodeBrig, Inc.
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
package spp.protocol.artifact.trace

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.Serializable
import spp.protocol.artifact.ArtifactQualifiedName

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class TraceSpan(
    val traceId: String,
    val segmentId: String,
    val spanId: Int,
    val parentSpanId: Int,
    val refs: List<TraceSpanRef> = emptyList(),
    val serviceCode: String,
    val serviceInstanceName: String? = null,
    @Serializable(with = InstantIso8601Serializer::class)
    val startTime: Instant,
    @Serializable(with = InstantIso8601Serializer::class)
    val endTime: Instant,
    val endpointName: String? = null,
    val artifactQualifiedName: ArtifactQualifiedName? = null,
    val type: String,
    val peer: String? = null,
    val component: String? = null,
    val error: Boolean? = null,
    val childError: Boolean = false,
    val hasChildStack: Boolean? = null,
    val layer: String? = null,
    val tags: Map<String, String> = emptyMap(),
    val logs: List<TraceSpanLogEntry> = emptyList(),
    val meta: MutableMap<String, String> = mutableMapOf()
) {
    fun putMetaInt(tag: String, value: Int) {
        meta[tag] = value.toString()
    }

    fun putMetaLong(tag: String, value: Long) {
        meta[tag] = value.toString()
    }

    fun putMetaDouble(tag: String, value: Double) {
        meta[tag] = value.toString()
    }

    fun putMetaString(tag: String, value: String) {
        meta[tag] = value
    }

    fun getMetaInt(tag: String): Int? {
        return meta[tag]?.toIntOrNull()
    }

    fun getMetaLong(tag: String): Long {
        return meta[tag]!!.toLong()
    }

    fun getMetaDouble(tag: String): Double {
        return meta[tag]!!.toDouble()
    }

    fun getMetaString(tag: String): String {
        return meta[tag]!!
    }
}
