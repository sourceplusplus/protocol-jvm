package spp.protocol

import kotlinx.datetime.Clock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import spp.protocol.artifact.exception.LiveStackTrace
import spp.protocol.instrument.LiveBreakpoint
import spp.protocol.instrument.LiveLog
import spp.protocol.instrument.LiveMeter
import spp.protocol.instrument.LiveSourceLocation
import spp.protocol.instrument.command.CommandType
import spp.protocol.instrument.command.LiveInstrumentCommand
import spp.protocol.instrument.event.LiveInstrumentRemoved
import spp.protocol.instrument.meter.MeterType
import spp.protocol.instrument.meter.MetricValue
import spp.protocol.instrument.meter.MetricValueType
import spp.protocol.instrument.throttle.InstrumentThrottle
import spp.protocol.instrument.throttle.ThrottleStep

@RunWith(JUnit4::class)
class ProtocolMarshallerTest {

    @Test
    fun testLiveInstrument() {
        val liveInstrument = LiveMeter(
            "meterName",
            MeterType.COUNT,
            MetricValue(MetricValueType.NUMBER, "1"),
            LiveSourceLocation("source", 1),
            "condition",
            System.currentTimeMillis(),
            1,
            "id",
            applyImmediately = false,
            applied = true,
            pending = false,
            InstrumentThrottle(5, ThrottleStep.SECOND),
            mapOf("key" to "value")
        )

        val serialized = ProtocolMarshaller.serializeLiveInstrument(liveInstrument)
        val deserialized = ProtocolMarshaller.deserializeLiveInstrument(serialized)
        assertEquals(liveInstrument, deserialized)
    }

    @Test
    fun testLiveInstrumentCommand() {
        val liveInstrumentCommand = LiveInstrumentCommand(
            CommandType.ADD_LIVE_INSTRUMENT,
            setOf(
                LiveBreakpoint(
                    LiveSourceLocation("source", 1),
                    "condition",
                    System.currentTimeMillis(),
                    1,
                    "id",
                    applyImmediately = false,
                    applied = true,
                    pending = false,
                    InstrumentThrottle(5, ThrottleStep.SECOND),
                    mapOf("key" to "value")
                )
            ),
            setOf(
                LiveSourceLocation("source", 1)
            )
        )

        val serialized = ProtocolMarshaller.serializeLiveInstrumentCommand(liveInstrumentCommand)
        val deserialized = ProtocolMarshaller.deserializeLiveInstrumentCommand(serialized)
        assertEquals(liveInstrumentCommand, deserialized)
    }

    @Test
    fun testLiveInstrumentRemoved() {
        val liveInstrumentRemoved = LiveInstrumentRemoved(
            LiveLog(
                "log {}",
                listOf("foo"),
                LiveSourceLocation("source", 2),
                "foo == bar",
                System.currentTimeMillis(),
                2,
                "id",
                applyImmediately = true,
                applied = false,
                pending = true,
                InstrumentThrottle(1, ThrottleStep.DAY),
                mapOf("key2" to "value2")
            ),
            Clock.System.now(),
            LiveStackTrace("exception", "message", mutableListOf())
        )

        val serialized = ProtocolMarshaller.serializeLiveInstrumentRemoved(liveInstrumentRemoved)
        val deserialized = ProtocolMarshaller.deserializeLiveInstrumentRemoved(serialized)
        assertEquals(liveInstrumentRemoved, deserialized)
    }
}