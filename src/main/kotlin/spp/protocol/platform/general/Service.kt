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
package spp.protocol.platform.general

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * Represents a service.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class Service(
    val id: String,
    val name: String,
    val group: String = "",
    val shortName: String? = null,
    val layers: List<String> = emptyList(),
    val normal: Boolean = true
) {
    constructor(json: JsonObject) : this(
        json.getString("id"),
        json.getString("name"),
        json.getString("group"),
        json.getString("shortName"),
        json.getJsonArray("layers").map { it as String },
        json.getBoolean("normal")
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("id", id)
        json.put("name", name)
        json.put("group", group)
        json.put("shortName", shortName)
        json.put("layers", layers)
        json.put("normal", normal)
        return json
    }
}
