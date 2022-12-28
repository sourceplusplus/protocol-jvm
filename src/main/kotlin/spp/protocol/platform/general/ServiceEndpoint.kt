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
package spp.protocol.platform.general

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * Represents a service endpoint.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class ServiceEndpoint(
    val id: String,
    val name: String
) {
    constructor(json: JsonObject) : this(
        json.getString("id"),
        json.getString("name")
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("id", id)
        json.put("name", name)
        return json
    }
}
