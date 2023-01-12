package spp.protocol.artifact.trace

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant

class TraceSpanLogEntryTest {

    @Test
    fun `trace span log entry deser`() {
        val traceSpanLogEntry = TraceSpanLogEntry(
            time = Instant.now(),
            data = "data"
        )
        val json = traceSpanLogEntry.toJson()
        val traceSpanLogEntry2 = TraceSpanLogEntry(json)
        assertEquals(traceSpanLogEntry, traceSpanLogEntry2)
    }
}
