/*
 * Source++, the continuous feedback platform for developers.
 * Copyright (C) 2022-2023 CodeBrig, Inc.
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
import io.vertx.core.impl.ContextInternal
import spp.protocol.instrument.*
import spp.protocol.instrument.event.LiveInstrumentEvent
import spp.protocol.instrument.location.LiveSourceLocation
import spp.protocol.instrument.variable.LiveVariableControl
import spp.protocol.instrument.variable.LiveVariableControlBase
import spp.protocol.service.SourceServices.LIVE_INSTRUMENT
import java.time.Instant

/**
 * Back-end service for managing [LiveInstrument]s.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@ProxyGen
@VertxGen
@Suppress("TooManyFunctions") // public API
interface LiveInstrumentService {

    @GenIgnore
    companion object {
        @JvmStatic
        fun createProxy(vertx: Vertx, accessToken: String? = null): LiveInstrumentService {
            val deliveryOptions = DeliveryOptions().apply {
                accessToken?.let { addHeader("auth-token", it) }
                (Vertx.currentContext() as? ContextInternal)?.localContextData()?.forEach {
                    addHeader(it.key.toString(), it.value.toString())
                }
            }
            return LiveInstrumentServiceVertxEBProxy(vertx, LIVE_INSTRUMENT, deliveryOptions)
        }
    }

    @GenIgnore
    fun getOrCreateLiveWatch(
        location: LiveSourceLocation,
        variables: List<String> = emptyList(),
        id: String
    ): Future<LiveBreakpoint> {
        return getLiveInstrument(id).compose { liveInstrument ->
            if (liveInstrument != null) {
                Future.succeededFuture(liveInstrument as LiveBreakpoint)
            } else {
                addLiveBreakpoint(
                    LiveBreakpoint(
                        location = location,
                        variableControl = LiveVariableControl(
                            variableNameConfig = variables.associateWith { LiveVariableControlBase() }
                        ),
                        id = id,
                        hitLimit = -1
                    )
                )
            }
        }
    }

    /**
     * Applies the given [LiveInstrument].
     */
    fun addLiveInstrument(instrument: LiveInstrument): Future<LiveInstrument>

    /**
     * Applies the given [LiveBreakpoint].
     */
    @GenIgnore
    fun addLiveBreakpoint(liveBreakpoint: LiveBreakpoint): Future<LiveBreakpoint> {
        return addLiveInstrument(liveBreakpoint).map { it as LiveBreakpoint }
    }

    /**
     * Applies the given [LiveLog].
     */
    @GenIgnore
    fun addLiveLog(liveLog: LiveLog): Future<LiveLog> {
        return addLiveInstrument(liveLog).map { it as LiveLog }
    }

    /**
     * Applies the given [LiveSpan].
     */
    @GenIgnore
    fun addLiveMeter(liveMeter: LiveMeter): Future<LiveMeter> {
        return addLiveInstrument(liveMeter).map { it as LiveMeter }
    }

    /**
     * Applies the given [LiveSpan].
     */
    @GenIgnore
    fun addLiveSpan(liveSpan: LiveSpan): Future<LiveSpan> {
        return addLiveInstrument(liveSpan).map { it as LiveSpan }
    }

    @JvmSuppressWildcards
    fun addLiveInstruments(instruments: List<LiveInstrument>): Future<List<LiveInstrument>>
    fun removeLiveInstrument(id: String): Future<LiveInstrument?>
    fun removeLiveInstruments(location: LiveSourceLocation): Future<List<LiveInstrument>>

    @GenIgnore
    fun getLiveInstrument(id: String): Future<LiveInstrument?> {
        return getLiveInstrumentById(id)
    }

    @Deprecated("use getLiveInstrument", ReplaceWith("getLiveInstrument(id)"))
    fun getLiveInstrumentById(id: String): Future<LiveInstrument?>
    fun getLiveInstrumentsByIds(ids: List<String>): Future<List<LiveInstrument>>
    fun getLiveInstrumentsByLocation(location: LiveSourceLocation): Future<List<LiveInstrument>>

    @GenIgnore
    fun getLiveInstrumentEvents(
        instrumentId: String
    ): Future<List<LiveInstrumentEvent>> {
        return getLiveInstrumentEvents(listOf(instrumentId), null, null, 0, Int.MAX_VALUE)
    }

    @GenIgnore
    fun getLiveInstrumentEvents(
        instrumentId: String?,
        from: Instant?,
        to: Instant?,
        offset: Int,
        limit: Int
    ): Future<List<LiveInstrumentEvent>> {
        return getLiveInstrumentEvents(listOfNotNull(instrumentId), from, to, offset, limit)
    }

    fun getLiveInstrumentEvents(
        instrumentIds: List<String>,
        from: Instant?,
        to: Instant?,
        offset: Int,
        limit: Int
    ): Future<List<LiveInstrumentEvent>>

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
     * Gets all [LiveInstrument]s.
     */
    @GenIgnore
    fun getLiveInstruments(): Future<List<LiveInstrument>> {
        return getLiveInstruments(null)
    }

    /**
     * Gets all [LiveBreakpoint]s.
     */
    @GenIgnore
    fun getLiveBreakpoints(): Future<List<LiveBreakpoint>> {
        return getLiveInstruments(LiveInstrumentType.BREAKPOINT).map { it as List<LiveBreakpoint> }
    }

    /**
     * Gets all [LiveLog]s.
     */
    @GenIgnore
    fun getLiveLogs(): Future<List<LiveLog>> {
        return getLiveInstruments(LiveInstrumentType.LOG).map { it as List<LiveLog> }
    }

    /**
     * Gets all [LiveMeter]s.
     */
    @GenIgnore
    fun getLiveMeters(): Future<List<LiveMeter>> {
        return getLiveInstruments(LiveInstrumentType.METER).map { it as List<LiveMeter> }
    }

    /**
     * Gets all [LiveSpan]s.
     */
    @GenIgnore
    fun getLiveSpans(): Future<List<LiveSpan>> {
        return getLiveInstruments(LiveInstrumentType.SPAN).map { it as List<LiveSpan> }
    }

    /**
     * Removes [LiveInstrument]s created by the developer invoking this method.
     */
    @GenIgnore
    fun clearLiveInstruments(): Future<Boolean> {
        return clearLiveInstruments(null)
    }

    /**
     * Removes [LiveBreakpoint]s created by the developer invoking this method.
     */
    @GenIgnore
    fun clearLiveBreakpoints(): Future<Boolean> {
        return clearLiveInstruments(LiveInstrumentType.BREAKPOINT)
    }

    /**
     * Removes [LiveLog]s created by the developer invoking this method.
     */
    @GenIgnore
    fun clearLiveLogs(): Future<Boolean> {
        return clearLiveInstruments(LiveInstrumentType.LOG)
    }

    /**
     * Removes [LiveMeter]s created by the developer invoking this method.
     */
    @GenIgnore
    fun clearLiveMeters(): Future<Boolean> {
        return clearLiveInstruments(LiveInstrumentType.METER)
    }

    /**
     * Removes [LiveSpan]s created by the developer invoking this method.
     */
    @GenIgnore
    fun clearLiveSpans(): Future<Boolean> {
        return clearLiveInstruments(LiveInstrumentType.SPAN)
    }

    /**
     * Removes [LiveInstrument]s created by all developers.
     */
    @GenIgnore
    fun clearAllLiveInstruments(): Future<Boolean> {
        return clearAllLiveInstruments(null)
    }

    /**
     * Removes [LiveBreakpoint]s created by all developers.
     */
    @GenIgnore
    fun clearAllLiveBreakpoints(): Future<Boolean> {
        return clearAllLiveInstruments(LiveInstrumentType.BREAKPOINT)
    }

    /**
     * Removes [LiveLog]s created by all developers.
     */
    @GenIgnore
    fun clearAllLiveLogs(): Future<Boolean> {
        return clearAllLiveInstruments(LiveInstrumentType.LOG)
    }

    /**
     * Removes [LiveMeter]s created by all developers.
     */
    @GenIgnore
    fun clearAllLiveMeters(): Future<Boolean> {
        return clearAllLiveInstruments(LiveInstrumentType.METER)
    }

    /**
     * Removes [LiveSpan]s created by all developers.
     */
    @GenIgnore
    fun clearAllLiveSpans(): Future<Boolean> {
        return clearAllLiveInstruments(LiveInstrumentType.SPAN)
    }
}
