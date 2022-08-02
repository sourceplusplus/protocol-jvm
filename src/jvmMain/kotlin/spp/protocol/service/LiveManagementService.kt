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
package spp.protocol.service

import io.vertx.codegen.annotations.GenIgnore
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import spp.protocol.SourceServices.Utilize.LIVE_MANAGEMENT_SERVICE
import spp.protocol.platform.auth.ClientAccess


/**
 * todo: description.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@ProxyGen
@VertxGen
interface LiveManagementService {

    @GenIgnore
    companion object {
        @JvmStatic
        fun createProxy(vertx: Vertx, authToken: String? = null): LiveManagementService {
            val deliveryOptions = DeliveryOptions().apply {
                authToken?.let { addHeader("auth-token", it) }
            }
            return LiveManagementServiceVertxEBProxy(vertx, LIVE_MANAGEMENT_SERVICE, deliveryOptions)
        }
    }

    fun getClientAccessors(): Future<List<ClientAccess>>
    fun getClientAccess(id: String): Future<ClientAccess?>
    fun addClientAccess(): Future<ClientAccess>
    fun removeClientAccess(id: String): Future<Boolean>
    fun updateClientAccess(id: String): Future<ClientAccess>
}