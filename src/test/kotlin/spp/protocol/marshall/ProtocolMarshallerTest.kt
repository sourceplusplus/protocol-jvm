/*
 * Source++, the open-source live coding platform.
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
package spp.protocol.marshall

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import spp.protocol.artifact.exception.LiveStackTrace
import spp.protocol.artifact.exception.LiveStackTraceElement
import spp.protocol.instrument.LiveBreakpoint
import spp.protocol.instrument.LiveLog
import spp.protocol.instrument.LiveMeter
import spp.protocol.instrument.LiveSourceLocation
import spp.protocol.instrument.command.CommandType
import spp.protocol.instrument.command.LiveInstrumentCommand
import spp.protocol.instrument.event.LiveBreakpointHit
import spp.protocol.instrument.event.LiveInstrumentRemoved
import spp.protocol.instrument.meter.MeterType
import spp.protocol.instrument.meter.MetricValue
import spp.protocol.instrument.meter.MetricValueType
import spp.protocol.instrument.throttle.InstrumentThrottle
import spp.protocol.instrument.throttle.ThrottleStep
import spp.protocol.instrument.variable.LiveVariable
import spp.protocol.instrument.variable.LiveVariableScope
import java.time.Instant

class ProtocolMarshallerTest {

    @Test
    fun testLiveInstrument() {
        val liveInstrument = LiveMeter(
            MeterType.COUNT,
            MetricValue(MetricValueType.NUMBER, "1"),
            "meterDescription",
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

        val serialized = liveInstrumentCommand.toJson()
        val deserialized = LiveInstrumentCommand(serialized)
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
            Instant.now(),
            LiveStackTrace("exception", "message", mutableListOf())
        )

        val serialized = liveInstrumentRemoved.toJson()
        val deserialized = LiveInstrumentRemoved(serialized)
        assertEquals(liveInstrumentRemoved, deserialized)
    }

    @Test
    fun testLiveBreakpointHit() {
        val liveBreakpointHit = LiveBreakpointHit(
            "breakpointId",
            "traceId",
            Instant.now(),
            "serviceInstance",
            "service",
            LiveStackTrace(
                "exception",
                "message",
                mutableListOf(
                    LiveStackTraceElement(
                        "method",
                        "source",
                        null,
                        mutableListOf(
                            LiveVariable(
                                "name",
                                "value",
                                1,
                                LiveVariableScope.GLOBAL_VARIABLE,
                                "liveClazz",
                                "liveIdentity",
                                "presentation"
                            )
                        ),
                        "sourceCode"
                    )
                ),
                LiveStackTrace(
                    "exception",
                    "message",
                    mutableListOf(
                        LiveStackTraceElement(
                            "method",
                            "source",
                            null,
                            mutableListOf(
                                LiveVariable(
                                    "name",
                                    "value",
                                    1,
                                    LiveVariableScope.GLOBAL_VARIABLE,
                                    "liveClazz",
                                    "liveIdentity",
                                    "presentation"
                                )
                            ),
                            "sourceCode"
                        )
                    )
                )
            )
        )

        val serialized = liveBreakpointHit.toJson()
        val deserialized = LiveBreakpointHit(serialized)
        assertEquals(liveBreakpointHit, deserialized)
    }
}
