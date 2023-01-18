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
import io.vertx.core.json.JsonObject
import spp.protocol.instrument.LiveInstrument
import spp.protocol.instrument.event.*
import spp.protocol.instrument.event.LiveInstrumentEventType.*
import spp.protocol.service.SourceServices.Subscribe.toLiveInstrumentSubscriberAddress

/**
 * Listen for [LiveInstrument] events.
 *
 * @param vertx the vertx instance
 * @param subscriptionId the subscription id (can be developer id or instrument id)
 * @param listener the listener to be called when an instrument event is received
 */
class LiveInstrumentListenerImpl(
    vertx: Vertx,
    subscriptionId: String,
    private val listener: LiveInstrumentListener
) {

    internal var consumer: MessageConsumer<JsonObject>

    init {
        consumer = vertx.eventBus().consumer(toLiveInstrumentSubscriberAddress(subscriptionId)) {
            val event = LiveInstrumentEvent.fromJson(it.body())
            listener.onInstrumentEvent(event)

            when (event.eventType) {
                LOG_HIT -> listener.onLogHitEvent(event as LiveLogHit)
                BREAKPOINT_HIT -> listener.onBreakpointHitEvent(event as LiveBreakpointHit)
                BREAKPOINT_ADDED -> listener.onBreakpointAddedEvent(event as LiveInstrumentAdded)
                BREAKPOINT_REMOVED -> listener.onInstrumentRemovedEvent(event as LiveInstrumentRemoved)
                LOG_ADDED -> listener.onLogAddedEvent(event as LiveInstrumentAdded)
                LOG_REMOVED -> listener.onInstrumentRemovedEvent(event as LiveInstrumentRemoved)
                BREAKPOINT_APPLIED -> listener.onInstrumentAppliedEvent(event as LiveInstrumentApplied)
                LOG_APPLIED -> listener.onInstrumentAppliedEvent(event as LiveInstrumentApplied)
                METER_ADDED -> listener.onMeterAddedEvent(event as LiveInstrumentAdded)
                METER_APPLIED -> listener.onInstrumentAppliedEvent(event as LiveInstrumentApplied)
                METER_UPDATED -> Unit // TODO
                METER_REMOVED -> listener.onInstrumentRemovedEvent(event as LiveInstrumentRemoved)
                SPAN_ADDED -> listener.onSpanAddedEvent(event as LiveInstrumentAdded)
                SPAN_APPLIED -> listener.onInstrumentAppliedEvent(event as LiveInstrumentApplied)
                SPAN_REMOVED -> listener.onInstrumentRemovedEvent(event as LiveInstrumentRemoved)
            }

            listener.afterInstrumentEvent(event)
        }
    }

    fun unregister(): Future<Void> {
        return consumer.unregister()
    }
}
