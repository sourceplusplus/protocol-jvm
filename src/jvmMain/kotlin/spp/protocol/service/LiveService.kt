package spp.protocol.service

import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import spp.protocol.developer.SelfInfo
import spp.protocol.general.Service
import spp.protocol.platform.client.ActiveProbe

/**
 * todo: description.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@ProxyGen
@VertxGen
interface LiveService {

    fun getSelf(handler: Handler<AsyncResult<SelfInfo>>)
    fun getServices(handler: Handler<AsyncResult<List<Service>>>)
    fun getActiveProbes(handler: Handler<AsyncResult<List<ActiveProbe>>>)
}
