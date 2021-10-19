package com.sourceplusplus.protocol.artifact.exception

import com.google.common.io.Resources
import io.vertx.core.json.JsonObject
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class JvmStackTraceTest {

    @Test
    fun `from json object`() {
        val jsonObj = JsonObject(
            Resources.toString(Resources.getResource("serialized-service-exception.json"), Charsets.UTF_8)
        ).getString("cause")
        val stackTrace = JvmStackTrace.fromString(jsonObj)
        println(stackTrace)
        //BreakpointRemoved        Assert.assertNotNull(stackTrace)
    }
}