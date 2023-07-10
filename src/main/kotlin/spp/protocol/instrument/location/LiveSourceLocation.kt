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
package spp.protocol.instrument.location

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import spp.protocol.platform.general.Service
import spp.protocol.platform.status.InstanceConnection

/**
 * Represents a location in source code.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class LiveSourceLocation @JvmOverloads constructor(
    val source: String,
    val line: Int = -1,
    override val service: Service? = null,
    override val probeId: String? = null, //todo: impl
    val scope: LocationScope = LocationScope.LINE
) : LiveLocation, Comparable<LiveSourceLocation> {

    init {
        require(source.isNotBlank()) { "source is required" }
    }

    constructor(json: JsonObject) : this(
        source = json.getString("source"),
        line = json.getInteger("line") ?: -1,
        service = json.getValue("service")?.let {
            if (it is JsonObject) {
                Service(it)
            } else {
                //todo: remove v0.8.0+
                Service.fromName(it.toString())
            }
        },
        probeId = json.getString("probeId"),
        scope = json.getString("scope")?.let { LocationScope.valueOf(it) } ?: LocationScope.LINE
    )

    override fun isSameLocation(location: LiveLocation): Boolean {
        if (location !is LiveSourceLocation) return false
        if (source != location.source) return false
        if (line != location.line && line != -1 && location.line != -1) return false //-1 is wildcard
        if (service != null && (location.service == null || !service.isSameLocation(location.service!!))) return false
        if (probeId != null && probeId != location.probeId) return false
        return true
    }

    override fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("source", source)
        json.put("line", line)
        json.put("service", service?.toJson())
        json.put("probeId", probeId)
        json.put("scope", scope.name)
        return json
    }

    override fun compareTo(other: LiveSourceLocation): Int {
        val sourceCompare = source.compareTo(other.source)
        if (sourceCompare != 0) return sourceCompare
        return line.compareTo(other.line)
    }

    fun isSameLocation(other: LiveSourceLocation): Boolean {
        if (source != other.source) return false
        if (line != other.line && line != -1 && other.line != -1) return false //-1 is wildcard
        if (service != null && (other.service == null || !service.isSameLocation(other.service))) return false
        if (probeId != null && probeId != other.probeId) return false
        return true
    }

    fun isSameLocation(other: InstanceConnection): Boolean {
        if (service != null && service.name != other.meta["service"]) return false
        if (probeId != null && probeId != other.instanceId) return false
        return true
    }

    override fun toString(): String {
        return buildString {
            append("LiveSourceLocation(")
            append("source=$source")
            if (line != -1) append(", line=$line")
            if (service != null) append(", service=$service")
            if (probeId != null) append(", probeId=$probeId")
            if (scope != LocationScope.LINE) append(", scope=$scope")
            append(")")
        }
    }
}
