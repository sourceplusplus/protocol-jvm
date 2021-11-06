package spp.protocol.artifact.trace

import com.google.common.io.Resources
import spp.protocol.artifact.exception.LiveStackTrace
import io.vertx.core.json.JsonObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

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
}
