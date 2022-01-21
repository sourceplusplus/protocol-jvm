package spp.protocol.service.live

import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import spp.protocol.view.LiveViewSubscription

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@ProxyGen
@VertxGen
interface LiveViewService {
    fun addLiveViewSubscription(subscription: LiveViewSubscription, handler: Handler<AsyncResult<LiveViewSubscription>>)
    fun removeLiveViewSubscription(subscriptionId: String, handler: Handler<AsyncResult<LiveViewSubscription>>)
    fun getLiveViewSubscriptions(handler: Handler<AsyncResult<List<LiveViewSubscription>>>)
    fun clearLiveViewSubscriptions(handler: Handler<AsyncResult<List<LiveViewSubscription>>>)
    fun getLiveViewSubscriptionStats(handler: Handler<AsyncResult<JsonObject>>)
}
