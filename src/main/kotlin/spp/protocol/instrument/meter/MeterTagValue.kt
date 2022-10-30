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
package spp.protocol.instrument.meter

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * todo: description.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class MeterTagValue(
    val key: String,
    val valueType: MeterTagValueType,
    val value: String
) {
    constructor(json: JsonObject) : this(
        json.getString("key"),
        MeterTagValueType.valueOf(json.getString("valueType")),
        json.getString("value")
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("key", key)
        json.put("valueType", valueType.name)
        json.put("value", value)
        return json
    }
}
