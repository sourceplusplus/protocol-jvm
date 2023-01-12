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

@DataObject
data class TraceSegment(
    val segmentId: String,
    val traceSpans: List<TraceSpan>,
    val depth: Int = 0
) {
    internal val spanMap: MutableMap<Int, TraceSpan> = mutableMapOf()
    internal val childSpanMap: MutableMap<TraceSpan, MutableList<TraceSpan>> = mutableMapOf()
    val size: Int
        get() = traceSpans.size

    constructor(json: JsonObject) : this(
        segmentId = json.getString("segmentId"),
        traceSpans = json.getJsonArray("traceSpans").map { TraceSpan(JsonObject.mapFrom(it)) },
        depth = json.getInteger("depth")
    )

    fun toJson(): JsonObject {
        return JsonObject.mapFrom(this)
    }

    fun getTraceSpan(spanId: Int): TraceSpan {
        return spanMap[spanId]!!
    }

    fun getParent(spanId: Int): TraceSpan? {
        return spanMap[getTraceSpan(spanId).parentSpanId]
    }

    fun getParent(traceSpan: TraceSpan): TraceSpan? {
        return spanMap[traceSpan.parentSpanId]
    }

    fun hasChildren(traceSpan: TraceSpan): Boolean {
        return getChildren(traceSpan).isNotEmpty()
    }

    fun getChildren(spanId: Int): List<TraceSpan> {
        return childSpanMap[getTraceSpan(spanId)]?.toList() ?: emptyList()
    }

    fun getChildren(traceSpan: TraceSpan): List<TraceSpan> {
        return childSpanMap[traceSpan]?.toList() ?: emptyList()
    }
}
