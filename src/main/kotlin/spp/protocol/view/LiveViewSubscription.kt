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
import spp.protocol.artifact.ArtifactQualifiedName
import spp.protocol.instrument.LiveSourceLocation

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class LiveViewSubscription(
    val subscriptionId: String? = null, //todo: actual bottom
    val entityIds: List<String>,
    val artifactQualifiedName: ArtifactQualifiedName? = null, //todo: remove, use artifactLocation
    val artifactLocation: LiveSourceLocation? = null, //todo: bottom?
    val liveViewConfig: LiveViewConfig
) {
    constructor(json: JsonObject) : this(
        subscriptionId = json.getString("subscriptionId"),
        entityIds = json.getJsonArray("entityIds").map { it as String },
        artifactQualifiedName = json.getJsonObject("artifactQualifiedName")?.let { ArtifactQualifiedName(it) },
        artifactLocation = json.getJsonObject("artifactLocation")?.let { LiveSourceLocation(it) },
        liveViewConfig = LiveViewConfig(json.getJsonObject("liveViewConfig"))
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("subscriptionId", subscriptionId)
        json.put("entityIds", entityIds)
        artifactQualifiedName?.let { json.put("artifactQualifiedName", it.toJson()) }
        artifactLocation?.let { json.put("artifactLocation", it.toJson()) }
        json.put("liveViewConfig", liveViewConfig.toJson())
        return json
    }
}
