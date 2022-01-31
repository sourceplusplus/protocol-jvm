/*
 * Source++, the open-source live coding platform.
 * Copyright (C) 2022 CodeBrig, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package spp.protocol

import spp.protocol.SourceMarkerServices.Utilize.LIVE_INSTRUMENT
import spp.protocol.SourceMarkerServices.Utilize.LIVE_VIEW
import spp.protocol.service.LiveService
import spp.protocol.service.live.LiveInstrumentService
import spp.protocol.service.live.LiveViewService
import spp.protocol.service.logging.LogCountIndicatorService
import spp.protocol.service.tracing.LocalTracingService

/**
 * todo: description.
 *
 * @since 0.2.1
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
object SourceMarkerServices {

    object Instance {
        var liveService: LiveService? = null
        var liveInstrument: LiveInstrumentService? = null
        var liveView: LiveViewService? = null
        var localTracing: LocalTracingService? = null
        var logCountIndicator: LogCountIndicatorService? = null
    }

    object Utilize {
        const val LIVE_SERVICE = "spp.service.live-service"
        const val LIVE_INSTRUMENT = "spp.service.live-instrument"
        const val LIVE_VIEW = "spp.service.live-view"
        const val LOCAL_TRACING = "spp.service.local-tracing"
        const val LOG_COUNT_INDICATOR = "spp.service.log-count-indicator"
    }

    object Provide {
        const val LIVE_INSTRUMENT_SUBSCRIBER = "$LIVE_INSTRUMENT.subscriber"
        const val LIVE_VIEW_SUBSCRIBER = "$LIVE_VIEW.subscriber"
    }
}
