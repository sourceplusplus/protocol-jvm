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
package spp.protocol.view

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject

/**
 * todo: description
 *
 * @since 0.7.6
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class HistoricalView(
    val entityIds: List<String>,
    val metricIds: List<String>,
    val data: JsonArray = JsonArray() //todo: type out
) {
    constructor(json: JsonObject) : this(
        entityIds = json.getJsonArray("entityIds").map { it.toString() },
        metricIds = json.getJsonArray("metricIds").map { it.toString() },
        data = json.getJsonArray("data")
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("entityIds", JsonArray().apply { entityIds.forEach { add(it) } })
        json.put("metricIds", JsonArray().apply { metricIds.forEach { add(it) } })
        json.put("data", data)
        return json
    }
}
