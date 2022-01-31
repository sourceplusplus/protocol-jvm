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
package spp.protocol.service.live

import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import kotlinx.datetime.Instant
import spp.protocol.instrument.DurationStep
import spp.protocol.instrument.LiveInstrument
import spp.protocol.instrument.LiveInstrumentBatch
import spp.protocol.instrument.LiveSourceLocation
import spp.protocol.instrument.breakpoint.LiveBreakpoint
import spp.protocol.instrument.log.LiveLog
import spp.protocol.instrument.meter.LiveMeter
import spp.protocol.instrument.span.LiveSpan

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@ProxyGen
@VertxGen
interface LiveInstrumentService {
    fun addLiveInstrument(instrument: LiveInstrument, handler: Handler<AsyncResult<LiveInstrument>>)
    fun addLiveInstruments(batch: LiveInstrumentBatch, handler: Handler<AsyncResult<List<LiveInstrument>>>)
    fun removeLiveInstrument(id: String, handler: Handler<AsyncResult<LiveInstrument?>>)
    fun removeLiveInstruments(location: LiveSourceLocation, handler: Handler<AsyncResult<List<LiveInstrument>>>)
    fun getLiveInstrumentById(id: String, handler: Handler<AsyncResult<LiveInstrument?>>)
    fun getLiveInstrumentsByIds(ids: List<String>, handler: Handler<AsyncResult<List<LiveInstrument>>>)
    fun getLiveInstruments(handler: Handler<AsyncResult<List<LiveInstrument>>>)
    fun getLiveBreakpoints(handler: Handler<AsyncResult<List<LiveBreakpoint>>>)
    fun getLiveLogs(handler: Handler<AsyncResult<List<LiveLog>>>)
    fun getLiveMeters(handler: Handler<AsyncResult<List<LiveMeter>>>)
    fun getLiveSpans(handler: Handler<AsyncResult<List<LiveSpan>>>)
    fun clearLiveInstruments(handler: Handler<AsyncResult<Boolean>>)
    fun clearLiveBreakpoints(handler: Handler<AsyncResult<Boolean>>)
    fun clearLiveLogs(handler: Handler<AsyncResult<Boolean>>)
    fun clearLiveMeters(handler: Handler<AsyncResult<Boolean>>)
    fun clearLiveSpans(handler: Handler<AsyncResult<Boolean>>)
    fun clearAllLiveInstruments(handler: Handler<AsyncResult<Boolean>>)

    fun setupLiveMeter(liveMeter: LiveMeter, handler: Handler<AsyncResult<JsonObject>>)
    fun getLiveMeterMetrics(
        liveMeter: LiveMeter,
        start: Instant,
        stop: Instant,
        step: DurationStep,
        handler: Handler<AsyncResult<JsonObject>>
    )
}
