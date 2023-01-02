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
package spp.protocol.service.listen

import io.vertx.core.Vertx
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import spp.protocol.artifact.ArtifactQualifiedName
import spp.protocol.artifact.ArtifactType
import spp.protocol.artifact.exception.LiveStackTrace
import spp.protocol.artifact.log.LogOrderType
import spp.protocol.artifact.log.LogResult
import spp.protocol.instrument.LiveBreakpoint
import spp.protocol.instrument.LiveSourceLocation
import spp.protocol.instrument.event.*
import spp.protocol.service.SourceServices.Subscribe.toLiveInstrumentSubscriberAddress
import java.time.Instant
import java.util.concurrent.TimeUnit

@ExtendWith(VertxExtension::class)
class LiveInstrumentListenerTest {

    @Test
    fun `test log hit`(vertx: Vertx) {
        val testContext = VertxTestContext()

        val logHit = LiveLogHit(
            "logId",
            Instant.now(),
            "serviceInstance",
            "service",
            LogResult(
                ArtifactQualifiedName("identifier", type = ArtifactType.EXPRESSION),
                LogOrderType.NEWEST_LOGS,
                Instant.now(),
            )
        )
        vertx.addLiveInstrumentListener("system", object : LiveInstrumentListener {
            override fun onLogHitEvent(event: LiveLogHit) {
                testContext.verify {
                    assertEquals(logHit, event)
                }
                testContext.completeNow()
            }
        })
        //todo: DataObjectMessageCodec
        val event = LiveInstrumentEvent(LiveInstrumentEventType.LOG_HIT, logHit.toJson().toString())
        vertx.eventBus().publish(toLiveInstrumentSubscriberAddress("system"), JsonObject.mapFrom(event))

        if (testContext.awaitCompletion(5, TimeUnit.SECONDS)) {
            if (testContext.failed()) {
                throw testContext.causeOfFailure()
            }
        } else {
            throw RuntimeException("Test timed out")
        }
    }

    @Test
    fun `test breakpoint hit`(vertx: Vertx) {
        val testContext = VertxTestContext()

        val bpHit = LiveBreakpointHit(
            "breakpointId",
            "traceId",
            Instant.now(),
            "serviceInstance",
            "service",
            LiveStackTrace(
                "exceptionType",
                "message",
                mutableListOf()
            )
        )
        vertx.addLiveInstrumentListener("system", object : LiveInstrumentListener {
            override fun onBreakpointHitEvent(event: LiveBreakpointHit) {
                testContext.verify {
                    assertEquals(bpHit, event)
                }
                testContext.completeNow()
            }
        })
        //todo: DataObjectMessageCodec
        val event = LiveInstrumentEvent(LiveInstrumentEventType.BREAKPOINT_HIT, bpHit.toJson().toString())
        vertx.eventBus().publish(toLiveInstrumentSubscriberAddress("system"), JsonObject.mapFrom(event))

        if (testContext.awaitCompletion(5, TimeUnit.SECONDS)) {
            if (testContext.failed()) {
                throw testContext.causeOfFailure()
            }
        } else {
            throw RuntimeException("Test timed out")
        }
    }

    @Test
    fun `test multi instrument remove`(vertx: Vertx) {
        val testContext = VertxTestContext()

        val bpRemoved1 = LiveInstrumentRemoved(
            LiveBreakpoint(
                location = LiveSourceLocation("test.location1", 1)
            ),
            Instant.now()
        )
        val bpRemoved2 = LiveInstrumentRemoved(
            LiveBreakpoint(
                location = LiveSourceLocation("test.location2", 2)
            ),
            Instant.now()
        )
        val bpsRemoved = JsonArray()
            .add(bpRemoved1.toJson())
            .add(bpRemoved2.toJson())

        vertx.addLiveInstrumentListener("system", object : LiveInstrumentListener {
            override fun onInstrumentRemovedEvent(event: LiveInstrumentRemoved) {
                testContext.verify {
                    if (event.liveInstrument.location.source.endsWith("1")) {
                        assertEquals(bpRemoved1, event)
                    } else {
                        assertEquals(bpRemoved2, event)
                    }
                }
                testContext.completeNow()
            }
        })
        //todo: DataObjectMessageCodec
        val event = LiveInstrumentEvent(LiveInstrumentEventType.BREAKPOINT_REMOVED, bpsRemoved.toString())
        vertx.eventBus().publish(toLiveInstrumentSubscriberAddress("system"), JsonObject.mapFrom(event))

        if (testContext.awaitCompletion(5, TimeUnit.SECONDS)) {
            if (testContext.failed()) {
                throw testContext.causeOfFailure()
            }
        } else {
            throw RuntimeException("Test timed out")
        }
    }
}
