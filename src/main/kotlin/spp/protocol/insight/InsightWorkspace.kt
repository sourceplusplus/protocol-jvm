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
package spp.protocol.insight

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import java.time.Instant

/**
 * Represents a siloed CPG for performing live insight analyses. Every commit
 * to a project is analysed in a new workspace. Workspaces are isolated from
 * each other and can be deleted when no longer needed.
 */
@DataObject
data class InsightWorkspace(
    val id: String,
    val createDate: Instant,
    val config: JsonObject = JsonObject()
) {
    constructor(json: JsonObject) : this(
        id = json.getString("id"),
        createDate = json.getInstant("createDate")
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("id", id)
        json.put("createDate", createDate)
        return json
    }
}
