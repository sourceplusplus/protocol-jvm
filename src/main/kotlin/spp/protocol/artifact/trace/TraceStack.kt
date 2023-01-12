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

/**
 * Represents a list of [TraceSpan]s as the equivalent tree structure.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class TraceStack(val traceSpans: List<TraceSpan>) : Iterable<TraceSegment> {
    private val segmentMap: MutableMap<String, TraceSegment> = mutableMapOf()
    val isEmpty: Boolean = traceSpans.isEmpty()
    fun size(): Int = traceSpans.size
    val segments: Int

    constructor(json: JsonObject) : this(
        traceSpans = json.getJsonArray("traceSpans").map { TraceSpan(JsonObject.mapFrom(it)) }
    )

    fun toJson(): JsonObject {
        return JsonObject.mapFrom(this)
    }

    init {
        for ((segmentId, traceSpans) in traceSpans.groupBy { it.segmentId }) {
            val segment = TraceSegment(segmentId, traceSpans, traceSpans.distinctBy { it.parentSpanId }.size)
            segmentMap[segmentId] = segment
            for (traceSpan in traceSpans) {
                segment.spanMap[traceSpan.spanId] = traceSpan
                val parent = getParent(traceSpan)
                if (parent != null) {
                    if (!segment.childSpanMap.containsKey(parent)) {
                        segment.childSpanMap[parent] = mutableListOf()
                    }
                    segment.childSpanMap[parent]!!.add(traceSpan)
                }
            }
        }
        segments = segmentMap.size
    }

    fun hasParent(traceSpan: TraceSpan): Boolean {
        return getParent(traceSpan) == null
    }

    fun getParent(traceSpan: TraceSpan): TraceSpan? {
        return getSegment(traceSpan).spanMap[traceSpan.parentSpanId]
    }

    fun hasChildren(traceSpan: TraceSpan): Boolean {
        return getChildren(traceSpan).isNotEmpty()
    }

    fun getChildren(traceSpan: TraceSpan): List<TraceSpan> {
        return getSegment(traceSpan).getChildren(traceSpan)
    }

    fun getSegment(segmentId: String): TraceSegment {
        return segmentMap[segmentId]!!
    }

    fun getSegment(traceSpan: TraceSpan): TraceSegment {
        return segmentMap[traceSpan.segmentId]!!
    }

    fun getSegmentIds(): List<String> {
        return segmentMap.keys.toList()
    }

    override fun iterator(): Iterator<TraceSegment> {
        return segmentMap.values.iterator()
    }
}
