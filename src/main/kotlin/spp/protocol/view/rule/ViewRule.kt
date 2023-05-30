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
open class ViewRule(
    val name: String,
    val exp: String,
    val partitions: List<RulePartition> = emptyList(),
    val meterIds: List<String> = emptyList()
) {

    constructor(json: JsonObject) : this(
        name = json.getString("name"),
        exp = json.getString("exp"),
        partitions = json.getJsonArray("partitions").map { RulePartition(it as JsonObject) },
        meterIds = json.getJsonArray("meterIds").map { it as String }
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("name", name)
        json.put("exp", exp)
        json.put("partitions", JsonArray().apply { partitions.forEach { add(it.toJson()) } })
        json.put("meterIds", JsonArray().apply { meterIds.forEach { add(it) } })
        return json
    }

    fun copy(name: String) = ViewRule(
        name = name,
        exp = exp,
        partitions = partitions,
        meterIds = meterIds
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ViewRule
        if (name != other.name) return false
        if (exp != other.exp) return false
        if (partitions != other.partitions) return false
        return meterIds == other.meterIds
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
