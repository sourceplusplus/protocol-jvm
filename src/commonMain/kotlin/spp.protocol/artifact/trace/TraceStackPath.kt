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
package spp.protocol.artifact.trace

import kotlinx.serialization.Serializable

/**
 * Used to keep track of the path taken in a [TraceStack].
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class TraceStackPath(
    val traceStack: TraceStack,
    val path: List<TraceSpan> = mutableListOf(),
    val orderType: TraceOrderType,
    val localTracing: Boolean = false
) {

    fun getCurrentSegment(): TraceStack.Segment? {
        val segmentId = path.lastOrNull()?.segmentId
        return if (segmentId != null) {
            traceStack.getSegment(segmentId)
        } else null
    }

    fun getCurrentRoot(): TraceSpan? {
        return path.lastOrNull()
    }

    fun follow(segmentId: String, spanId: Int) {
        (path as MutableList).add(traceStack.getSegment(segmentId).getTraceSpan(spanId))
    }

    fun removeLastRoot() {
        (path as MutableList).removeLast()
    }

    fun autoFollow(artifactQualifiedName: String) {
        val shortestPath = mutableListOf<TraceSpan>()
        val startSpan = traceStack.traceSpans.find { it.endpointName == artifactQualifiedName }
        if (startSpan != null) {
            var currentSpan: TraceSpan = startSpan
            while (currentSpan.spanId != 0 || currentSpan.parentSpanId != -1) {
                val prevSpan = traceStack.getSegment(currentSpan.segmentId).getTraceSpan(currentSpan.spanId - 1)
                shortestPath.add(prevSpan)
                currentSpan = prevSpan
            }
            shortestPath.add(startSpan)

            shortestPath.forEach {
                follow(it.segmentId, it.spanId)
            }
        }
    }
}
