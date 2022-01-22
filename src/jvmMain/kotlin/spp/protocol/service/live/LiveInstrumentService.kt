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

    fun setupLiveMeter(liveMeter: LiveMeter, handler: Handler<AsyncResult<JsonObject>>)
    fun getLiveMeterMetrics(
        liveMeter: LiveMeter,
        start: Instant,
        stop: Instant,
        step: DurationStep,
        handler: Handler<AsyncResult<JsonObject>>
    )
}
