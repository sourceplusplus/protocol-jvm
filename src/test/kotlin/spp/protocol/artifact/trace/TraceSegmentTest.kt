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

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import spp.protocol.artifact.ArtifactQualifiedName
import spp.protocol.artifact.ArtifactType
import java.time.Instant

class TraceSegmentTest {

    @Test
    fun `trace segment deser`() {
        val traceSegment = TraceSegment(
            segmentId = "segmentId",
            traceSpans = listOf(
                TraceSpan(
                    traceId = "traceId",
                    segmentId = "segmentId",
                    spanId = 1,
                    parentSpanId = 2,
                    refs = listOf(),
                    serviceCode = "serviceCode",
                    serviceInstanceName = "serviceInstanceName",
                    startTime = Instant.now(),
                    endTime = Instant.now(),
                    endpointName = "endpointName",
                    artifactQualifiedName = ArtifactQualifiedName("id", "commit", ArtifactType.METHOD),
                    type = "type",
                    peer = "peer",
                    component = "component",
                    error = true,
                    childError = true,
                    hasChildStack = true,
                    layer = "layer",
                    tags = emptyMap(),
                    logs = emptyList(),
                    meta = mutableMapOf()
                )
            ),
            depth = 0
        )
        val json = traceSegment.toJson()
        val traceSegment2 = TraceSegment(json)
        assertEquals(traceSegment, traceSegment2)
    }
}
