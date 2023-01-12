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
import java.time.Instant

class TraceTest {

    @Test
    fun `trace deser`() {
        val trace = Trace(
            key = "key",
            operationNames = listOf("operationNames"),
            duration = 1,
            start = Instant.now(),
            error = false,
            traceIds = listOf("traceIds"),
            partial = false,
            segmentId = "segmentId",
            meta = mutableMapOf(
                "key" to "value"
            )
        )
        val json = trace.toJson()
        val trace2 = Trace(json)
        assertEquals(trace, trace2)
    }
}
