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
package spp.protocol.artifact.trace

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import java.time.Instant

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class Trace(
    val key: String? = null,
    val operationNames: List<String>,
    val duration: Int,
    val start: Instant,
    val error: Boolean? = null,
    val traceIds: List<String>,
    val partial: Boolean = false,
    val segmentId: String? = null,
    val meta: MutableMap<String, String> = mutableMapOf()
) {

    constructor(json: JsonObject) : this(
        json.getString("key"),
        json.getJsonArray("operationNames").map { it.toString() },
        json.getInteger("duration"),
        Instant.parse(json.getString("start")),
        json.getBoolean("error"),
        json.getJsonArray("traceIds").map { it.toString() },
        json.getBoolean("partial"),
        json.getString("segmentId"),
        json.getJsonObject("meta").associate { it.key.toString() to it.value.toString() }.toMutableMap()
    )

    fun toJson(): JsonObject {
        return JsonObject.mapFrom(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Trace) return false
        if (traceIds != other.traceIds) return false
        return true
    }

    override fun hashCode(): Int {
        return traceIds.hashCode()
    }
}
