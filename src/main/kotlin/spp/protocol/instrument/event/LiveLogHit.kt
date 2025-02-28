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
package spp.protocol.instrument.event

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import spp.protocol.artifact.log.LogResult
import spp.protocol.instrument.LiveInstrument
import spp.protocol.instrument.LiveLog
import java.time.Instant

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class LiveLogHit(
    override val instrument: LiveInstrument,
    val logResult: LogResult,
    override val occurredAt: Instant,
    override val serviceInstance: String,
    override val service: String
) : LiveInstrumentHit {
    override val eventType: LiveInstrumentEventType = LiveInstrumentEventType.LOG_HIT

    constructor(json: JsonObject) : this(
        LiveLog(json.getJsonObject("instrument")),
        LogResult(json.getJsonObject("logResult")),
        Instant.parse(json.getString("occurredAt")),
        json.getString("serviceInstance"),
        json.getString("service")
    )

    override fun toJson(): JsonObject {
        return JsonObject.mapFrom(this)
    }

    override fun withInstrument(instrument: LiveInstrument): LiveInstrumentEvent {
        return copy(instrument = instrument)
    }
}
