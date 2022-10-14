/*
 * Source++, the continuous feedback platform for developers.
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
package spp.protocol.instrument

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import spp.protocol.instrument.throttle.InstrumentThrottle

/**
 * A live log represents a textual message.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class LiveLog(
    val logFormat: String,
    val logArguments: List<String> = emptyList(),
    override val location: LiveSourceLocation,
    override val condition: String? = null,
    override val expiresAt: Long? = null,
    override val hitLimit: Int = 1,
    override val id: String? = null,
    override val applyImmediately: Boolean = false,
    override val applied: Boolean = false,
    override val pending: Boolean = false,
    override val throttle: InstrumentThrottle = InstrumentThrottle.DEFAULT,
    override val meta: Map<String, Any> = emptyMap()
) : LiveInstrument() {
    override val type: LiveInstrumentType = LiveInstrumentType.LOG

    constructor(json: JsonObject) : this(
        logFormat = json.getString("logFormat"),
        logArguments = json.getJsonArray("logArguments").map { it.toString() },
        location = LiveSourceLocation(json.getJsonObject("location")),
        condition = json.getString("condition"),
        expiresAt = json.getLong("expiresAt"),
        hitLimit = json.getInteger("hitLimit"),
        id = json.getString("id"),
        applyImmediately = json.getBoolean("applyImmediately") ?: false,
        applied = json.getBoolean("applied") ?: false,
        pending = json.getBoolean("pending") ?: false,
        throttle = InstrumentThrottle(json.getJsonObject("throttle")),
        meta = json.getJsonObject("meta").associate { it.key to it.value }
    )

    override fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("type", type.name)
        json.put("logFormat", logFormat)
        json.put("logArguments", JsonArray().apply { logArguments.forEach { add(it) } })
        json.put("location", location.toJson())
        json.put("condition", condition)
        json.put("expiresAt", expiresAt)
        json.put("hitLimit", hitLimit)
        json.put("id", id)
        json.put("applyImmediately", applyImmediately)
        json.put("applied", applied)
        json.put("pending", pending)
        json.put("throttle", throttle.toJson())
        json.put("meta", JsonObject(meta))
        return json
    }

    /**
     * Specify explicitly so Kotlin doesn't override.
     */
    override fun equals(other: Any?): Boolean = super.equals(other)

    /**
     * Specify explicitly so Kotlin doesn't override.
     */
    override fun hashCode(): Int = super.hashCode()
}
