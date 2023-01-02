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

import spp.protocol.instrument.*
import spp.protocol.instrument.event.LiveBreakpointHit
import spp.protocol.instrument.event.LiveInstrumentEvent
import spp.protocol.instrument.event.LiveInstrumentRemoved
import spp.protocol.instrument.event.LiveLogHit

interface LiveInstrumentListener {

    fun onInstrumentEvent(event: LiveInstrumentEvent) {
    }

    fun afterInstrumentEvent(event: LiveInstrumentEvent) {
    }

    fun onLogHitEvent(event: LiveLogHit) {
    }

    fun onBreakpointHitEvent(event: LiveBreakpointHit) {
    }

    fun onBreakpointAddedEvent(event: LiveBreakpoint) {
    }

    fun onInstrumentRemovedEvent(event: LiveInstrumentRemoved) {
    }

    fun onLogAddedEvent(event: LiveLog) {
    }

    fun onInstrumentAppliedEvent(event: LiveInstrument) {
    }

    fun onMeterAddedEvent(event: LiveMeter) {
    }

//    fun onMeterUpdatedEvent(event: LiveMeterUpdated) {
//    }

    fun onSpanAddedEvent(event: LiveSpan) {
    }
}
