package spp.protocol.artifact.trace

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TraceSpanRefTest {

    @Test
    fun `trace span ref deser`() {
        val traceSpanRef = TraceSpanRef(
            traceId = "traceId",
            parentSegmentId = "parentSegmentId",
            parentSpanId = 1,
            type = "type"
        )
        val json = traceSpanRef.toJson()
        val traceSpanRef2 = TraceSpanRef(json)
        assertEquals(traceSpanRef, traceSpanRef2)
    }
}
