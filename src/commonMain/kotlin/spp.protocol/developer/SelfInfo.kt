package spp.protocol.developer

import kotlinx.serialization.Serializable
import spp.protocol.auth.AccessPermission
import spp.protocol.auth.DeveloperRole
import spp.protocol.auth.RolePermission

@Serializable
data class SelfInfo(
    val developer: Developer,
    val roles: List<DeveloperRole>,
    val permissions: List<RolePermission>,
    val access: List<AccessPermission>
)
