/*
 * Source++, the continuous feedback platform for developers.
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

import com.google.common.io.Resources
import io.vertx.core.json.JsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import spp.protocol.artifact.exception.LiveStackTrace
import spp.protocol.artifact.exception.sourceAsLineNumber

class LiveStackTraceTest {

    @Test
    fun parseJsonJVMStackTrace() {
        val jsonObj = JsonObject(
            Resources.toString(Resources.getResource("serialized-service-exception.json"), Charsets.UTF_8)
        ).getString("cause")
        val stackTrace = LiveStackTrace.fromString(jsonObj)
        assertNotNull(stackTrace)
    }

    @Test
    fun parsePythonStackTrace() {
        val stackTrace = LiveStackTrace.fromString(
            Resources.toString(Resources.getResource("pythonStackTrace.txt"), Charsets.UTF_8)
        )
        assertNotNull(stackTrace)
        assertEquals(23, stackTrace!!.elements.size)
        assertEquals(16, stackTrace.getElements(true).size)
    }

    @Test
    fun parseNodeJsStackTrace() {
        val stackTrace = LiveStackTrace.fromString(
            Resources.toString(Resources.getResource("nodejsStackTrace.txt"), Charsets.UTF_8)
        )
        assertNotNull(stackTrace)
        assertEquals(stackTrace!!.exceptionType, "Error")
        assertEquals(stackTrace.message, "Something unexpected has occurred.")
        assertEquals(8, stackTrace.elements.size)
        assertEquals(8, stackTrace.getElements(true).size)

        assertEquals(9, stackTrace.elements[0].sourceAsLineNumber())
        assertEquals(17, stackTrace.elements[1].sourceAsLineNumber())
        assertEquals(460, stackTrace.elements[2].sourceAsLineNumber())
        assertEquals(478, stackTrace.elements[3].sourceAsLineNumber())
        assertEquals(355, stackTrace.elements[4].sourceAsLineNumber())
        assertEquals(310, stackTrace.elements[5].sourceAsLineNumber())
        assertEquals(501, stackTrace.elements[6].sourceAsLineNumber())
        assertEquals(129, stackTrace.elements[7].sourceAsLineNumber())
    }
}
