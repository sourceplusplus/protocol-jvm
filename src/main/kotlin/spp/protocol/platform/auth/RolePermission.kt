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
package spp.protocol.platform.auth

import spp.protocol.platform.auth.CommandType.*

enum class RolePermission(val manager: Boolean, val commandType: CommandType) {
    RESET(true, LIVE_MANAGEMENT_SERVICE),

    //clients
    ADD_CLIENT_ACCESS(true, LIVE_MANAGEMENT_SERVICE),
    REMOVE_CLIENT_ACCESS(true, LIVE_MANAGEMENT_SERVICE),
    GET_CLIENT_ACCESSORS(true, LIVE_MANAGEMENT_SERVICE),
    UPDATE_CLIENT_ACCESS(true, LIVE_MANAGEMENT_SERVICE),

    //devs
    ADD_DEVELOPER(true, LIVE_MANAGEMENT_SERVICE),
    REMOVE_DEVELOPER(true, LIVE_MANAGEMENT_SERVICE),
    GET_DEVELOPERS(true, LIVE_MANAGEMENT_SERVICE),
    REFRESH_DEVELOPER_TOKEN(true, LIVE_MANAGEMENT_SERVICE),

    //roles
    ADD_ROLE(true, LIVE_MANAGEMENT_SERVICE),
    REMOVE_ROLE(true, LIVE_MANAGEMENT_SERVICE),
    GET_ROLES(true, LIVE_MANAGEMENT_SERVICE),
    GET_DEVELOPER_ROLES(true, LIVE_MANAGEMENT_SERVICE),
    ADD_DEVELOPER_ROLE(true, LIVE_MANAGEMENT_SERVICE),
    REMOVE_DEVELOPER_ROLE(true, LIVE_MANAGEMENT_SERVICE),

    //permissions
    GET_DEVELOPER_PERMISSIONS(true, LIVE_MANAGEMENT_SERVICE),
    GET_ROLE_PERMISSIONS(true, LIVE_MANAGEMENT_SERVICE),
    ADD_ROLE_PERMISSION(true, LIVE_MANAGEMENT_SERVICE),
    REMOVE_ROLE_PERMISSION(true, LIVE_MANAGEMENT_SERVICE),

    //instrument access
    GET_ACCESS_PERMISSIONS(true, LIVE_MANAGEMENT_SERVICE),
    GET_DATA_REDACTIONS(true, LIVE_MANAGEMENT_SERVICE),
    UPDATE_DATA_REDACTION(true, LIVE_MANAGEMENT_SERVICE),
    ADD_ACCESS_PERMISSION(true, LIVE_MANAGEMENT_SERVICE),
    REMOVE_ACCESS_PERMISSION(true, LIVE_MANAGEMENT_SERVICE),

    //instruments
    ADD_LIVE_BREAKPOINT(false, LIVE_INSTRUMENT),
    ADD_LIVE_LOG(false, LIVE_INSTRUMENT),
    ADD_LIVE_METER(false, LIVE_INSTRUMENT),
    ADD_LIVE_SPAN(false, LIVE_INSTRUMENT),
    GET_LIVE_INSTRUMENTS(false, LIVE_INSTRUMENT),
    GET_LIVE_BREAKPOINTS(false, LIVE_INSTRUMENT),
    GET_LIVE_LOGS(false, LIVE_INSTRUMENT),
    GET_LIVE_METERS(false, LIVE_INSTRUMENT),
    GET_LIVE_SPANS(false, LIVE_INSTRUMENT),
    REMOVE_LIVE_INSTRUMENT(false, LIVE_INSTRUMENT),
    CLEAR_ALL_LIVE_INSTRUMENTS(false, LIVE_INSTRUMENT),

    //views
    ADD_LIVE_VIEW_SUBSCRIPTION(false, LIVE_VIEW),
    REMOVE_LIVE_VIEW_SUBSCRIPTION(false, LIVE_VIEW),
    GET_LIVE_VIEW_SUBSCRIPTIONS(false, LIVE_VIEW),

//    VIEW_OVERVIEW(false, LIVE_VIEW),
    VIEW_ACTIVITY(false, LIVE_VIEW),
    VIEW_TRACES(false, LIVE_VIEW),
    VIEW_LOGS(false, LIVE_VIEW),
    SHOW_QUICK_STATS(false, LIVE_VIEW);

    companion object {
        fun fromString(s: String): RolePermission? {
            val exactMatch = values().firstOrNull { it.name.equals(s, true) }
            if (exactMatch != null) {
                return exactMatch
            }

            //convert function names to permissions (e.g. addLiveBreakpoint -> ADD_LIVE_BREAKPOINT)
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
