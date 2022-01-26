package spp.protocol.service.logging

import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import kotlinx.datetime.Instant
import spp.protocol.artifact.log.LogCountSummary
import spp.protocol.instrument.DurationStep

/**
 * todo: description.
 *
 * @since 0.2.1
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@ProxyGen
@VertxGen
interface LogCountIndicatorService {

    fun getPatternOccurrences(
        logPatterns: List<String>,
        serviceName: String?,
        start: Instant,
        stop: Instant,
        step: DurationStep,
        handler: Handler<AsyncResult<JsonObject>>
    )
}
