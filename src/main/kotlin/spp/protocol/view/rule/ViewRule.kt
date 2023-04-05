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
package spp.protocol.view.rule

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject

@DataObject
data class ViewRule(
    val name: String,
    val exp: String,
    val partitions: List<RulePartition> = emptyList(),
) {

    constructor(json: JsonObject) : this(
        name = json.getString("name"),
        exp = json.getString("exp"),
        partitions = json.getJsonArray("partitions").map { RulePartition(it as JsonObject) }
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("name", name)
        json.put("exp", exp)
        json.put("partitions", JsonArray().apply { partitions.forEach { add(it.toJson()) } })
        return json
    }
}
