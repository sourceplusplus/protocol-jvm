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
package spp.protocol.portal

import spp.protocol.Serializers
import kotlinx.serialization.Serializable

/**
 * Contains the available possible portal tabs.
 *
 * @since 0.1.0
 * @author <a href="mailto:bfergerson@apache.org">Brandon Fergerson</a>
 */
@Serializable(with = Serializers.PageTypeSerializer::class)
enum class PageType(val icon: String, var hasChildren: Boolean) {
    OVERVIEW("icon demo-icon satellite", false),
    ACTIVITY("icon demo-icon dashboard", false),
    TRACES("icon demo-icon code", true),
    LOGS("icon demo-icon align left", false),
    CONFIGURATION("icon configure", false);

    val title = name.toLowerCase().capitalize()
}
