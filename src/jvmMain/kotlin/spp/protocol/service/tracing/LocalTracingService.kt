package spp.protocol.service.tracing

import spp.protocol.artifact.ArtifactQualifiedName
import spp.protocol.artifact.trace.TraceOrderType
import spp.protocol.artifact.trace.TraceResult
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import kotlinx.datetime.Instant

/**
 * todo: description.
 *
 * @since 0.2.1
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@ProxyGen
@VertxGen
interface LocalTracingService {

    fun getTraceResult(
        artifactQualifiedName: ArtifactQualifiedName,
        start: Instant,
        stop: Instant,
        orderType: TraceOrderType,
        pageSize: Int,
        pageNumber: Int,
        handler: Handler<AsyncResult<TraceResult>>
    )
}
