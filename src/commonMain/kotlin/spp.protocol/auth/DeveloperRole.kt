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
