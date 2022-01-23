package spp.protocol.service.error

import io.vertx.serviceproxy.ServiceException
import spp.protocol.auth.RolePermission

class PermissionAccessDenied(val permission: RolePermission, message: String? = null) : ServiceException(500, message) {

    fun toEventBusException(): PermissionAccessDenied {
        return PermissionAccessDenied(permission, "EventBusException:PermissionAccessDenied[$permission]")
    }
}
