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
package spp.protocol.platform.auth

enum class RolePermission(val manager: Boolean = true) {
    RESET(true),

    //devs
    ADD_DEVELOPER(true),
    REMOVE_DEVELOPER(true),
    GET_DEVELOPERS(true),
    REFRESH_DEVELOPER_TOKEN(true),

    //roles
    ADD_ROLE(true),
    REMOVE_ROLE(true),
    GET_ROLES(true),
    GET_DEVELOPER_ROLES(true),
    ADD_DEVELOPER_ROLE(true),
    REMOVE_DEVELOPER_ROLE(true),

    //permissions
    GET_DEVELOPER_PERMISSIONS(true),
    GET_ROLE_PERMISSIONS(true),
    ADD_ROLE_PERMISSION(true),
    REMOVE_ROLE_PERMISSION(true),

    //instrument access
    GET_ACCESS_PERMISSIONS(true),
    GET_DATA_REDACTIONS(true),
    ADD_DATA_REDACTION(true),
    REMOVE_DATA_REDACTION(true),
    ADD_ACCESS_PERMISSION(true),
    REMOVE_ACCESS_PERMISSION(true),

    //instruments
    ADD_LIVE_BREAKPOINT(false),
    ADD_LIVE_LOG(false),
    ADD_LIVE_METER(false),
    ADD_LIVE_SPAN(false),
    GET_LIVE_INSTRUMENTS(false),
    GET_LIVE_BREAKPOINTS(false),
    GET_LIVE_LOGS(false),
    GET_LIVE_METERS(false),
    GET_LIVE_SPANS(false),
    REMOVE_LIVE_INSTRUMENT(false),
    CLEAR_ALL_LIVE_INSTRUMENTS(false),

    //views
    ADD_LIVE_VIEW_SUBSCRIPTION(false),
    REMOVE_LIVE_VIEW_SUBSCRIPTION(false),
    GET_LIVE_VIEW_SUBSCRIPTIONS(false),
//    VIEW_OVERVIEW(false),
    VIEW_ACTIVITY(false),
    VIEW_TRACES(false),
    VIEW_LOGS(false);

    companion object {
        fun fromString(s: String): RolePermission? {
            val sb = StringBuilder()
            for (i in s.indices) {
                val c = s[i]
                if (c.isUpperCase()) {
                    if (i > 0) {
                        sb.append('_')
                    }
                    sb.append(c.lowercase())
                } else {
                    sb.append(c)
                }
            }
            return values().firstOrNull { it.name.equals(sb.toString(), true) }
        }
    }
}
