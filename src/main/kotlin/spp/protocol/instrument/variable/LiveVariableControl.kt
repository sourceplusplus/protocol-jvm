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
package spp.protocol.instrument.variable

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * Allows for fine-grained control over the serialization of [LiveVariable]s.
 */
@DataObject
data class LiveVariableControl(
    val maxObjectDepth: Int? = null,
    val maxObjectSize: Int? = null,
    val maxCollectionLength: Int? = null,
    val variableTypeConfig: Map<String, LiveVariableControl> = emptyMap(),
    val variableNameConfig: Map<String, LiveVariableControl> = emptyMap()
) {
    constructor(json: JsonObject) : this(
        maxObjectDepth = json.getInteger("maxObjectDepth"),
        maxObjectSize = json.getInteger("maxObjectSize"),
        maxCollectionLength = json.getInteger("maxCollectionLength"),
        variableTypeConfig = json.getJsonObject("variableTypeConfig")?.let {
            it.associate { it.key to LiveVariableControl(it.value as JsonObject) }
        }.orEmpty(),
        variableNameConfig = json.getJsonObject("variableNameConfig")?.let {
            it.associate { it.key to LiveVariableControl(it.value as JsonObject) }
        }.orEmpty()
    )

    fun toJson(): JsonObject {
        return JsonObject.mapFrom(this)
    }
}
