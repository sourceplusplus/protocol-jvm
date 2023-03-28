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
import io.vertx.core.impl.ContextInternal
import io.vertx.core.json.JsonObject
import spp.protocol.platform.auth.*
import spp.protocol.platform.developer.Developer
import spp.protocol.platform.developer.SelfInfo
import spp.protocol.platform.general.Service
import spp.protocol.platform.general.ServiceEndpoint
import spp.protocol.platform.general.ServiceInstance
import spp.protocol.platform.general.TimeInfo
import spp.protocol.platform.status.InstanceConnection
import spp.protocol.service.SourceServices.LIVE_MANAGEMENT

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
        fun createProxy(vertx: Vertx, accessToken: String? = null): LiveManagementService {
            val deliveryOptions = DeliveryOptions().apply {
                accessToken?.let { addHeader("auth-token", it) }
                (Vertx.currentContext() as? ContextInternal)?.localContextData()?.forEach {
                    addHeader(it.key.toString(), it.value.toString())
                }
            }
            return LiveManagementServiceVertxEBProxy(vertx, LIVE_MANAGEMENT, deliveryOptions)
        }
    }

    fun getVersion(): Future<String>
    fun getTimeInfo(): Future<TimeInfo>

    fun getAccessPermissions(): Future<List<AccessPermission>>
    fun getAccessPermission(id: String): Future<AccessPermission>
    fun addAccessPermission(locationPatterns: List<String>, type: AccessType): Future<AccessPermission>
    fun removeAccessPermission(id: String): Future<Void>
    fun getRoleAccessPermissions(role: DeveloperRole): Future<List<AccessPermission>>
    fun addRoleAccessPermission(role: DeveloperRole, id: String): Future<Void>
    fun removeRoleAccessPermission(role: DeveloperRole, id: String): Future<Void>
    fun getDeveloperAccessPermissions(developerId: String): Future<List<AccessPermission>>

    fun getDataRedactions(): Future<List<DataRedaction>>
    fun getDataRedaction(id: String): Future<DataRedaction>
    fun addDataRedaction(id: String, type: RedactionType, lookup: String, replacement: String): Future<DataRedaction>
    fun updateDataRedaction(id: String, type: RedactionType, lookup: String, replacement: String): Future<DataRedaction>
    fun removeDataRedaction(id: String): Future<Void>
    fun getRoleDataRedactions(role: DeveloperRole): Future<List<DataRedaction>>
    fun addRoleDataRedaction(role: DeveloperRole, id: String): Future<Void>
    fun removeRoleDataRedaction(role: DeveloperRole, id: String): Future<Void>
    fun getDeveloperDataRedactions(developerId: String): Future<List<DataRedaction>>

    @GenIgnore
    fun getAccessToken(): Future<String> {
        return getAccessToken(null)
    }

    fun getAccessToken(authorizationCode: String?): Future<String>
    fun getDevelopers(): Future<List<Developer>>

    @GenIgnore
    fun addDeveloper(id: String): Future<Developer> {
        return addDeveloper(id, null)
    }

    fun addDeveloper(id: String, authorizationCode: String?): Future<Developer>
    fun removeDeveloper(id: String): Future<Void>
    fun refreshAuthorizationCode(developerId: String): Future<Developer>
    fun getRoles(): Future<List<DeveloperRole>>
    fun addRole(role: DeveloperRole): Future<Boolean>
    fun removeRole(role: DeveloperRole): Future<Boolean>
    fun getDeveloperRoles(developerId: String): Future<List<DeveloperRole>>
    fun addDeveloperRole(developerId: String, role: DeveloperRole): Future<Void>
    fun removeDeveloperRole(developerId: String, role: DeveloperRole): Future<Void>
    fun getDeveloperPermissions(developerId: String): Future<List<RolePermission>>

    fun reset(): Future<Void>
    fun getRolePermissions(role: DeveloperRole): Future<List<RolePermission>>
    fun addRolePermission(role: DeveloperRole, permission: RolePermission): Future<Void>
    fun removeRolePermission(role: DeveloperRole, permission: RolePermission): Future<Void>
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
