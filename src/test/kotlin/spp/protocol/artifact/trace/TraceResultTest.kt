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

class TraceResultTest {

    @Test
    fun `trace result deser`() {
        val traceResult = TraceResult(
            artifactQualifiedName = ArtifactQualifiedName("id", "commit", ArtifactType.METHOD),
            artifactSimpleName = "artifactSimpleName",
            orderType = TraceOrderType.LATEST_TRACES,
            start = Instant.now(),
            stop = Instant.now(),
            step = "step",
            traces = listOf(
                Trace(
                    key = "key",
                    operationNames = listOf("operationNames"),
                    duration = 1,
                    start = Instant.now(),
                    error = true,
                    traceIds = listOf("traceIds"),
                    partial = true,
                    segmentId = "segmentId",
                    meta = mutableMapOf(
                        "key" to "value"
                    )
                )
            ),
            total = 0
        )
        val json = traceResult.toJson()
        val traceResult2 = TraceResult(json)
        assertEquals(traceResult, traceResult2)
    }
}
