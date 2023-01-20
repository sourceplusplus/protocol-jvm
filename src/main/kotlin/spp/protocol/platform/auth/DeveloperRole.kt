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
package spp.protocol.platform.auth

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * Used to define the access role of a developer. Developers can be assigned
 * multiple roles and each role can be granted multiple permissions. The
 * available permissions are defined by the [RolePermission] enum.
 */
@DataObject
data class DeveloperRole(val roleName: String, val nativeRole: Boolean) {

    constructor(json: JsonObject) : this(
        json.getString("roleName"),
        json.getBoolean("nativeRole")
    )

    fun toJson(): JsonObject {
        return JsonObject.mapFrom(this)
    }

    override fun toString(): String {
        return roleName
    }

    companion object {
        val ROLE_MANAGER = DeveloperRole("role_manager", true)
        val ROLE_USER = DeveloperRole("role_user", true)

        fun fromString(roleName: String): DeveloperRole {
            return if (roleName.equals("role_manager", true)) {
                ROLE_MANAGER
            } else if (roleName.equals("role_user", true)) {
                ROLE_USER
            } else {
                DeveloperRole(roleName.lowercase().replace(' ', '_').trim(), false)
            }
        }
    }
}
