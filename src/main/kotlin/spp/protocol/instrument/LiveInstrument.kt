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
package spp.protocol.instrument

import io.vertx.core.json.JsonObject
import spp.protocol.instrument.throttle.InstrumentThrottle

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
sealed class LiveInstrument {
    abstract val location: LiveSourceLocation
    abstract val condition: String?
    abstract val expiresAt: Long? //todo: can just use -1 like hitLimit?
    abstract val hitLimit: Int
    abstract val id: String?
    abstract val type: LiveInstrumentType
    abstract val applyImmediately: Boolean
    abstract val applied: Boolean
    abstract val pending: Boolean
    abstract val throttle: InstrumentThrottle?
    abstract val meta: Map<String, Any>

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LiveInstrument) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    open fun toJson(): JsonObject {
        return when (this) {
            is LiveBreakpoint -> this.toJson()
            is LiveLog -> this.toJson()
            is LiveSpan -> this.toJson()
            is LiveMeter -> this.toJson()
            else -> error("Unknown live instrument type: $this")
        }
    }

    companion object {
        fun fromJson(json: JsonObject): LiveInstrument {
            return when (LiveInstrumentType.valueOf(json.getString("type"))) {
                LiveInstrumentType.BREAKPOINT -> LiveBreakpoint(json)
                LiveInstrumentType.LOG -> LiveLog(json)
                LiveInstrumentType.SPAN -> LiveSpan(json)
                LiveInstrumentType.METER -> LiveMeter(json)
            }
        }
    }
}
