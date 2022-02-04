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