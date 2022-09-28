/*
 * Source++, the open-source live coding platform.
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
package spp.protocol.view

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * Additional configuration for a [LiveView].
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class LiveViewConfig(
    val viewName: String,
    val viewMetrics: List<String>, //todo: change to MetricType after remove monitor module
    val refreshRateLimit: Int = -1 //limit of once per X milliseconds
) {
    constructor(json: JsonObject) : this(
        viewName = json.getString("viewName"),
        viewMetrics = json.getJsonArray("viewMetrics").map { it.toString() },
        refreshRateLimit = json.getInteger("refreshRateLimit")
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("viewName", viewName)
        json.put("viewMetrics", viewMetrics)
        json.put("refreshRateLimit", refreshRateLimit)
        return json
    }
}
