/*
 * Source++, the open-source live coding platform.
 * Copyright (C) 2022 CodeBrig, Inc.
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

import io.vertx.codegen.annotations.GenIgnore
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.json.JsonObject
import spp.protocol.SourceServices
import spp.protocol.view.LiveViewSubscription

/**
 * Back-end service for managing [LiveView]s.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@ProxyGen
@VertxGen
interface LiveViewService {

    @GenIgnore
    companion object {
        @JvmStatic
        fun createProxy(vertx: Vertx, authToken: String? = null): LiveViewService {
            val deliveryOptions = DeliveryOptions().apply {
                authToken?.let { addHeader("auth-token", it) }
            }
            return LiveViewServiceVertxEBProxy(vertx, SourceServices.Utilize.LIVE_VIEW, deliveryOptions)
        }
    }

    fun addLiveViewSubscription(subscription: LiveViewSubscription): Future<LiveViewSubscription>
    fun updateLiveViewSubscription(id: String, subscription: LiveViewSubscription): Future<LiveViewSubscription>
    fun removeLiveViewSubscription(id: String): Future<LiveViewSubscription>
    fun getLiveViewSubscription(id: String): Future<LiveViewSubscription>
    fun getLiveViewSubscriptions(): Future<List<LiveViewSubscription>>
    fun clearLiveViewSubscriptions(): Future<List<LiveViewSubscription>>
    fun getLiveViewSubscriptionStats(): Future<JsonObject>
}
