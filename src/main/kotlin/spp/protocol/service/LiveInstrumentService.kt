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
import spp.protocol.SourceServices
import spp.protocol.instrument.*

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

    /**
     * Applies the given [LiveInstrument].
     */
    fun addLiveInstrument(instrument: LiveInstrument): Future<LiveInstrument>

    /**
     * Applies the given [LiveBreakpoint].
     */
    fun addLiveBreakpoint(liveBreakpoint: LiveBreakpoint): Future<LiveBreakpoint> {
        return addLiveInstrument(liveBreakpoint).map { it as LiveBreakpoint }
    }

    /**
     * Applies the given [LiveLog].
     */
    fun addLiveLog(liveLog: LiveLog): Future<LiveLog> {
        return addLiveInstrument(liveLog).map { it as LiveLog }
    }

    /**
     * Applies the given [LiveSpan].
     */
    fun addLiveMeter(liveMeter: LiveMeter): Future<LiveMeter> {
        return addLiveInstrument(liveMeter).map { it as LiveMeter }
    }

    /**
     * Applies the given [LiveSpan].
     */
    fun addLiveSpan(liveSpan: LiveSpan): Future<LiveSpan> {
        return addLiveInstrument(liveSpan).map { it as LiveSpan }
    }

    @JvmSuppressWildcards
    fun addLiveInstruments(instruments: List<LiveInstrument>): Future<List<LiveInstrument>>
    fun removeLiveInstrument(id: String): Future<LiveInstrument?>
    fun removeLiveInstruments(location: LiveSourceLocation): Future<List<LiveInstrument>>
    fun getLiveInstrumentById(id: String): Future<LiveInstrument?>
    fun getLiveInstrumentsByIds(ids: List<String>): Future<List<LiveInstrument>>
    fun getLiveInstrumentsByLocation(location: LiveSourceLocation): Future<List<LiveInstrument>>

    /**
     * Gets [LiveInstrument]s with the given [type].
     */
    fun getLiveInstruments(type: LiveInstrumentType?): Future<List<LiveInstrument>>

    /**
     * Removes [LiveInstrument]s with the given [type] created by the developer invoking this method.
     */
    fun clearLiveInstruments(type: LiveInstrumentType?): Future<Boolean>

    /**
     * Removes [LiveInstrument]s with the given [type] created by all developers.
     */
    fun clearAllLiveInstruments(type: LiveInstrumentType?): Future<Boolean>

    /**
     * Gets all [LiveBreakpoint]s.
     */
    fun getLiveBreakpoints(): Future<List<LiveBreakpoint>> {
        return getLiveInstruments(LiveInstrumentType.BREAKPOINT).map { it as List<LiveBreakpoint> }
    }

    /**
     * Gets all [LiveLog]s.
     */
    fun getLiveLogs(): Future<List<LiveLog>> {
        return getLiveInstruments(LiveInstrumentType.LOG).map { it as List<LiveLog> }
    }

    /**
     * Gets all [LiveMeter]s.
     */
    fun getLiveMeters(): Future<List<LiveMeter>> {
        return getLiveInstruments(LiveInstrumentType.METER).map { it as List<LiveMeter> }
    }

    /**
     * Gets all [LiveSpan]s.
     */
    fun getLiveSpans(): Future<List<LiveSpan>> {
        return getLiveInstruments(LiveInstrumentType.SPAN).map { it as List<LiveSpan> }
    }

    /**
     * Removes [LiveBreakpoint]s created by the developer invoking this method.
     */
    fun clearLiveBreakpoints(): Future<Boolean> {
        return clearLiveInstruments(LiveInstrumentType.BREAKPOINT)
    }

    /**
     * Removes [LiveLog]s created by the developer invoking this method.
     */
    fun clearLiveLogs(): Future<Boolean> {
        return clearLiveInstruments(LiveInstrumentType.LOG)
    }

    /**
     * Removes [LiveMeter]s created by the developer invoking this method.
     */
    fun clearLiveMeters(): Future<Boolean> {
        return clearLiveInstruments(LiveInstrumentType.METER)
    }

    /**
     * Removes [LiveSpan]s created by the developer invoking this method.
     */
    fun clearLiveSpans(): Future<Boolean> {
        return clearLiveInstruments(LiveInstrumentType.SPAN)
    }

    /**
     * Removes [LiveBreakpoint]s created by all developers.
     */
    fun clearAllLiveBreakpoints(): Future<Boolean> {
        return clearAllLiveInstruments(LiveInstrumentType.BREAKPOINT)
    }

    /**
     * Removes [LiveLog]s created by all developers.
     */
    fun clearAllLiveLogs(): Future<Boolean> {
        return clearAllLiveInstruments(LiveInstrumentType.LOG)
    }

    /**
     * Removes [LiveMeter]s created by all developers.
     */
    fun clearAllLiveMeters(): Future<Boolean> {
        return clearAllLiveInstruments(LiveInstrumentType.METER)
    }

    /**
     * Removes [LiveSpan]s created by all developers.
     */
    fun clearAllLiveSpans(): Future<Boolean> {
        return clearAllLiveInstruments(LiveInstrumentType.SPAN)
    }
}
