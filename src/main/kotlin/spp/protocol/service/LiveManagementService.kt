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
package spp.protocol.service

import io.vertx.codegen.annotations.GenIgnore
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.json.JsonObject
import spp.protocol.platform.auth.ClientAccess
import spp.protocol.platform.auth.DeveloperRole
import spp.protocol.platform.auth.RolePermission
import spp.protocol.platform.developer.Developer
import spp.protocol.platform.developer.SelfInfo
import spp.protocol.platform.general.Service
import spp.protocol.platform.general.ServiceEndpoint
import spp.protocol.platform.general.ServiceInstance
import spp.protocol.platform.status.InstanceConnection
import spp.protocol.service.SourceServices.LIVE_MANAGEMENT_SERVICE

/**
 * Back-end service for general and administrative tasks.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@ProxyGen
@VertxGen
@Suppress("TooManyFunctions") // public API
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

    fun getVersion(): Future<String>

    fun getAuthToken(accessToken: String): Future<String>
    fun addDeveloper(id: String): Future<Developer>
    fun addRole(role: DeveloperRole): Future<Boolean>
    fun addDeveloperRole(developerId: String, role: DeveloperRole): Future<Void>
    fun addRolePermission(role: DeveloperRole, permission: RolePermission): Future<Void>

    //fun reset(): Future<Void> //todo: handle permission via JWT
    fun getRolePermissions(role: String): Future<List<RolePermission>>
    fun getClientAccessors(): Future<List<ClientAccess>>
    fun getClientAccess(id: String): Future<ClientAccess?>
    fun addClientAccess(): Future<ClientAccess>
    fun removeClientAccess(id: String): Future<Boolean>
    fun refreshClientAccess(id: String): Future<ClientAccess>

    fun getClients(): Future<JsonObject>
    fun getStats(): Future<JsonObject>
    fun getSelf(): Future<SelfInfo>

    @GenIgnore
    fun getServices(): Future<List<Service>> {
        return getServices(null)
    }

    fun getServices(layer: String?): Future<List<Service>>
    fun getInstances(serviceId: String): Future<List<ServiceInstance>>
    fun getEndpoints(serviceId: String): Future<List<ServiceEndpoint>>

    fun getActiveProbes(): Future<List<InstanceConnection>>
    fun getActiveProbe(id: String): Future<InstanceConnection?>
    fun updateActiveProbeMetadata(id: String, metadata: JsonObject): Future<InstanceConnection>
}
