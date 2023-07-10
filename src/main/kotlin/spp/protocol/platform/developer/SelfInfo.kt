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
package spp.protocol.platform.developer

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import spp.protocol.platform.auth.AccessPermission
import spp.protocol.platform.auth.DeveloperRole
import spp.protocol.platform.auth.RolePermission

@DataObject
data class SelfInfo(
    val developer: Developer,
    val roles: List<DeveloperRole>,
    val permissions: List<RolePermission>,
    val access: List<AccessPermission>
) {
    constructor(json: JsonObject) : this(
        developer = Developer(json.getJsonObject("developer")),
        roles = json.getJsonArray("roles").map { DeveloperRole.fromString(it.toString()) },
        permissions = json.getJsonArray("permissions").map { RolePermission.fromString(it.toString()) },
        access = json.getJsonArray("access").map { AccessPermission(JsonObject.mapFrom(it)) }
    )

    fun toJson(): JsonObject {
        return JsonObject().apply {
            put("developer", developer.toJson())
            put("roles", JsonArray(roles.map { it.toString() }))
            put("permissions", JsonArray(permissions.map { it.toString() }))
            put("access", JsonArray(access.map { it.toJson() }))
        }
    }
}
