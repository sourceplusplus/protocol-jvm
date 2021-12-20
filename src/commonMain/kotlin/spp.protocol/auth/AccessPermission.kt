package spp.protocol.auth

import kotlinx.serialization.Serializable

@Serializable
data class AccessPermission(
    val id: String,
    val locationPatterns: List<String>,
    val type: AccessType
)
