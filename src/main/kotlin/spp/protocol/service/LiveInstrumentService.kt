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
import spp.protocol.instrument.*
import java.time.Instant

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@ProxyGen
@VertxGen
interface LiveInstrumentService {

    @GenIgnore
    companion object {
        @JvmStatic
        fun createProxy(vertx: Vertx, authToken: String? = null): LiveInstrumentService {
            val deliveryOptions = DeliveryOptions().apply {
                authToken?.let { addHeader("auth-token", it) }
            }
            return LiveInstrumentServiceVertxEBProxy(vertx, SourceServices.Utilize.LIVE_INSTRUMENT, deliveryOptions)
        }
    }

    fun addLiveInstrument(instrument: LiveInstrument): Future<LiveInstrument>

    @JvmSuppressWildcards
    fun addLiveInstruments(instruments: List<LiveInstrument>): Future<List<LiveInstrument>>
    fun removeLiveInstrument(id: String): Future<LiveInstrument?>
    fun removeLiveInstruments(location: LiveSourceLocation): Future<List<LiveInstrument>>
    fun getLiveInstrumentById(id: String): Future<LiveInstrument?>
    fun getLiveInstrumentsByIds(ids: List<String>): Future<List<LiveInstrument>>
    fun getLiveInstrumentsByLocation(location: LiveSourceLocation): Future<List<LiveInstrument>>
    fun getLiveInstruments(type: LiveInstrumentType?): Future<List<LiveInstrument>>
    fun clearLiveInstruments(type: LiveInstrumentType?): Future<Boolean>
    fun clearAllLiveInstruments(type: LiveInstrumentType?): Future<Boolean>

    fun setupLiveMeter(liveMeter: LiveMeter): Future<JsonObject>
    fun getLiveMeterMetrics(
        liveMeter: LiveMeter,
        start: Instant,
        stop: Instant,
        step: DurationStep
    ): Future<JsonObject>
}
