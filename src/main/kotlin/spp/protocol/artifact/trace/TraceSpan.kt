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
package spp.protocol.artifact.trace

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import spp.protocol.artifact.ArtifactQualifiedName
import java.time.Instant

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class TraceSpan(
    val traceId: String,
    val segmentId: String,
    val spanId: Int,
    val parentSpanId: Int,
    val refs: List<TraceSpanRef> = emptyList(),
    val serviceCode: String,
    val serviceInstanceName: String? = null,
    val startTime: Instant,
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

    constructor(json: JsonObject) : this(
        traceId = json.getString("traceId"),
        segmentId = json.getString("segmentId"),
        spanId = json.getInteger("spanId"),
        parentSpanId = json.getInteger("parentSpanId"),
        refs = json.getJsonArray("refs").map { TraceSpanRef(JsonObject.mapFrom(it)) },
        serviceCode = json.getString("serviceCode"),
        serviceInstanceName = json.getString("serviceInstanceName"),
        startTime = Instant.parse(json.getString("startTime")),
        endTime = Instant.parse(json.getString("endTime")),
        endpointName = json.getString("endpointName"),
        artifactQualifiedName = json.getJsonObject("artifactQualifiedName")?.let { ArtifactQualifiedName(it) },
        type = json.getString("type"),
        peer = json.getString("peer"),
        component = json.getString("component"),
        error = json.getBoolean("error"),
        childError = json.getBoolean("childError"),
        hasChildStack = json.getBoolean("hasChildStack"),
        layer = json.getString("layer"),
        tags = json.getJsonObject("tags").map { it.key to it.value.toString() }.toMap(),
        logs = json.getJsonArray("logs").map { TraceSpanLogEntry(JsonObject.mapFrom(it)) },
        meta = json.getJsonObject("meta").map { it.key to it.value.toString() }.toMap().toMutableMap()
    )

    fun toJson(): JsonObject {
        return JsonObject.mapFrom(this)
    }
}
