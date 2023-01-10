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
package spp.protocol.service

/**
 * Back-end services available to developers.
 *
 * @since 0.2.1
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
object SourceServices {

    const val LIVE_MANAGEMENT_SERVICE = "spp.service.live-management-service"
    const val LIVE_INSTRUMENT = "spp.service.live-instrument"
    const val LIVE_VIEW = "spp.service.live-view"
    const val LIVE_INSIGHT = "spp.service.live-insight"

    object Subscribe {
        const val LIVE_INSTRUMENT_SUBSCRIBER = "$LIVE_INSTRUMENT.subscriber"
        const val LIVE_VIEW_SUBSCRIBER = "$LIVE_VIEW.subscriber"

        fun toLiveInstrumentSubscription(instrumentId: String): String = "$LIVE_INSTRUMENT_SUBSCRIBER:$instrumentId"
        fun toLiveViewSubscription(viewId: String): String = "$LIVE_INSTRUMENT_SUBSCRIBER:$viewId"
        fun toLiveInstrumentSubscriberAddress(selfId: String): String = "$LIVE_INSTRUMENT_SUBSCRIBER:$selfId"
        fun toLiveViewSubscriberAddress(selfId: String): String = "$LIVE_VIEW_SUBSCRIBER:$selfId"
    }
}
