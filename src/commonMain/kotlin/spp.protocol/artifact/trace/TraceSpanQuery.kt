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

import kotlinx.datetime.Instant

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
data class TraceSpanQuery(
    val traceId: String? = null,
    val segmentId: String? = null,
    val spanId: Int? = null,
    val parentSpanId: Int? = null,
//    val refs: List<TraceSpanRef> = emptyList(),
    val serviceCode: String? = null,
    val serviceInstanceName: String? = null,
    val startTime: Instant? = null,
    val endTime: Instant? = null,
    val endpointName: String? = null,
    val artifactQualifiedName: String? = null,
    val type: String? = null,
    val peer: String? = null,
    val component: String? = null,
    val error: Boolean? = null,
    val childError: Boolean? = null,
    val hasChildStack: Boolean? = null,
    val layer: String? = null,
    val tags: Set<String> = emptySet(),
//    val logs: List<TraceSpanLogEntry> = emptyList()
)
