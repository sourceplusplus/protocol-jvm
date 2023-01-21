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

import spp.protocol.instrument.event.*

@Suppress("TooManyFunctions") // public API
interface LiveInstrumentListener {

    fun onInstrumentEvent(event: LiveInstrumentEvent) {
    }

    fun afterInstrumentEvent(event: LiveInstrumentEvent) {
    }

    fun onInstrumentAddedEvent(event: LiveInstrumentAdded) {
    }

    fun onInstrumentHitEvent(event: LiveInstrumentHit) {
    }

    fun onLogHitEvent(event: LiveLogHit) {
    }

    fun onBreakpointHitEvent(event: LiveBreakpointHit) {
    }

    fun onBreakpointAddedEvent(event: LiveInstrumentAdded) {
    }

    fun onInstrumentRemovedEvent(event: LiveInstrumentRemoved) {
    }

    fun onLogAddedEvent(event: LiveInstrumentAdded) {
    }

    fun onInstrumentAppliedEvent(event: LiveInstrumentApplied) {
    }

    fun onMeterAddedEvent(event: LiveInstrumentAdded) {
    }

//    fun onMeterUpdatedEvent(event: LiveMeterUpdated) {
//    }

    fun onSpanAddedEvent(event: LiveInstrumentAdded) {
    }
}
