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
import io.vertx.core.Vertx
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import spp.protocol.platform.general.Service
import spp.protocol.service.SourceServices.Subscribe.toLiveViewSubscription

/**
 * A back-end subscription to events/metrics for a given set of entities.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class LiveView(
    val entityIds: MutableSet<String>,
    val viewConfig: LiveViewConfig,
    val service: Service? = null,
    val serviceInstance: String? = null,
    val subscriptionId: String? = null
) {
    constructor(json: JsonObject) : this(
        subscriptionId = json.getString("subscriptionId"),
        entityIds = json.getJsonArray("entityIds").map { it.toString() }.toMutableSet(),
        viewConfig = LiveViewConfig(json.getJsonObject("viewConfig")),
        service = json.getJsonObject("service")?.let { Service(it) },
        serviceInstance = json.getString("serviceInstance")
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("subscriptionId", subscriptionId)
        json.put("entityIds", JsonArray().apply { entityIds.forEach { add(it) } })
        json.put("viewConfig", viewConfig.toJson())
        service?.let { json.put("service", it.toJson()) }
        json.put("serviceInstance", serviceInstance)
        return json
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as LiveView
        if (subscriptionId != other.subscriptionId) return false
        return true
    }

    override fun hashCode(): Int {
        return subscriptionId?.hashCode() ?: 0
    }

    override fun toString(): String {
        return buildString {
            append("LiveView(")
            if (subscriptionId != null) append("subscriptionId=$subscriptionId, ")
            append("entityIds=$entityIds, ")
            append("viewConfig=$viewConfig")
            if (service != null) append(", service=$service")
            if (serviceInstance != null) append(", serviceInstance=$serviceInstance")
            append(")")
        }
    }

    fun addEventListener(vertx: Vertx, listener: (LiveViewEvent) -> Unit) {
        val viewId = subscriptionId ?: error("Cannot add event listener to view with null subscriptionId")
        vertx.eventBus().consumer<JsonObject>(toLiveViewSubscription(viewId)).handler {
            listener.invoke(LiveViewEvent(it.body()))
        }
    }
}
