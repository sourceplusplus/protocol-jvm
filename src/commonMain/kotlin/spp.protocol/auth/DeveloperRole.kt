/*
 * Source++, the open-source live coding platform.
 * Copyright (C) 2022 CodeBrig, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package spp.protocol.auth

enum class DeveloperRole(var roleName: String, var nativeRole: Boolean) {
    ROLE_MANAGER("role_manager", true),
    ROLE_USER("role_user", true),
    USER("*", false);

    companion object {
        fun fromString(roleName: String): DeveloperRole {
            val nativeRole = values().find { it.name.lowercase() == roleName.lowercase() }
            return if (nativeRole != null) {
                nativeRole
            } else {
                val user = USER
                user.roleName = roleName.toLowerCase().replace(' ', '_').trim()
                user
            }
        }
    }
}
