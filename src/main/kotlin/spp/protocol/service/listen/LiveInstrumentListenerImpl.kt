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

import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import spp.protocol.instrument.*
import spp.protocol.instrument.event.*
import spp.protocol.service.SourceServices.Subscribe.toLiveInstrumentSubscriberAddress

class LiveInstrumentListenerImpl(
    vertx: Vertx,
    developerId: String,
    private val instrumentListener: LiveInstrumentListener
) {

    internal var consumer: MessageConsumer<JsonObject>

    init {
        consumer = vertx.eventBus().consumer(toLiveInstrumentSubscriberAddress(developerId)) {
            val liveEvent = LiveInstrumentEvent(it.body())
            instrumentListener.onInstrumentEvent(liveEvent)

            when (liveEvent.eventType) {
                LiveInstrumentEventType.LOG_HIT -> onLogHitEvent(liveEvent)
                LiveInstrumentEventType.BREAKPOINT_HIT -> onBreakpointHitEvent(liveEvent)
                LiveInstrumentEventType.BREAKPOINT_ADDED -> onBreakpointAddedEvent(liveEvent)
                LiveInstrumentEventType.BREAKPOINT_REMOVED -> onInstrumentRemovedEvent(liveEvent)
                LiveInstrumentEventType.LOG_ADDED -> onLogAddedEvent(liveEvent)
                LiveInstrumentEventType.LOG_REMOVED -> onInstrumentRemovedEvent(liveEvent)
                LiveInstrumentEventType.BREAKPOINT_APPLIED -> onInstrumentAppliedEvent(liveEvent)
                LiveInstrumentEventType.LOG_APPLIED -> onInstrumentAppliedEvent(liveEvent)
                LiveInstrumentEventType.METER_ADDED -> onMeterAddedEvent(liveEvent)
                LiveInstrumentEventType.METER_APPLIED -> onInstrumentAppliedEvent(liveEvent)
                LiveInstrumentEventType.METER_UPDATED -> onMeterUpdatedEvent(liveEvent)
                LiveInstrumentEventType.METER_REMOVED -> onInstrumentRemovedEvent(liveEvent)
                LiveInstrumentEventType.SPAN_ADDED -> onSpanAddedEvent(liveEvent)
                LiveInstrumentEventType.SPAN_APPLIED -> onInstrumentAppliedEvent(liveEvent)
                LiveInstrumentEventType.SPAN_REMOVED -> onInstrumentRemovedEvent(liveEvent)
            }

            instrumentListener.afterInstrumentEvent(liveEvent)
        }
    }

    fun unregister(): Future<Void> {
        return consumer.unregister()
    }

    private fun onLogHitEvent(liveEvent: LiveInstrumentEvent) {
        val logHit = LiveLogHit(JsonObject(liveEvent.data))
        instrumentListener.onLogHitEvent(logHit)
    }

    private fun onBreakpointHitEvent(liveEvent: LiveInstrumentEvent) {
        val breakpointHit = LiveBreakpointHit(JsonObject(liveEvent.data))
        instrumentListener.onBreakpointHitEvent(breakpointHit)
    }

    private fun onBreakpointAddedEvent(liveEvent: LiveInstrumentEvent) {
        val breakpointAdded = LiveBreakpoint(JsonObject(liveEvent.data))
        instrumentListener.onBreakpointAddedEvent(breakpointAdded)
    }

    private fun onInstrumentRemovedEvent(liveEvent: LiveInstrumentEvent) {
        if (liveEvent.data.startsWith("[")) {
            val instrumentsRemoved = JsonArray(liveEvent.data)
            for (i in 0 until instrumentsRemoved.size()) {
                val instrumentRemoved = LiveInstrumentRemoved(instrumentsRemoved.getJsonObject(i))
                instrumentListener.onInstrumentRemovedEvent(instrumentRemoved)
            }
        } else {
            val instrumentRemoved = LiveInstrumentRemoved(JsonObject(liveEvent.data))
            instrumentListener.onInstrumentRemovedEvent(instrumentRemoved)
        }
    }

    private fun onLogAddedEvent(liveEvent: LiveInstrumentEvent) {
        val logAdded = LiveLog(JsonObject(liveEvent.data))
        instrumentListener.onLogAddedEvent(logAdded)
    }

    private fun onInstrumentAppliedEvent(liveEvent: LiveInstrumentEvent) {
        val instrumentApplied = LiveInstrument.fromJson(JsonObject(liveEvent.data))
        instrumentListener.onInstrumentAppliedEvent(instrumentApplied)
    }

    private fun onMeterAddedEvent(liveEvent: LiveInstrumentEvent) {
        val meterAdded = LiveMeter(JsonObject(liveEvent.data))
        instrumentListener.onMeterAddedEvent(meterAdded)
    }

    private fun onMeterUpdatedEvent(liveEvent: LiveInstrumentEvent) {
//        val meterUpdated = LiveMeterUpdated(JsonObject(liveEvent.data))
//        handleMeterUpdatedEvent(meterUpdated)
    }

    private fun onSpanAddedEvent(liveEvent: LiveInstrumentEvent) {
        val spanAdded = LiveSpan(JsonObject(liveEvent.data))
        instrumentListener.onSpanAddedEvent(spanAdded)
    }
}
