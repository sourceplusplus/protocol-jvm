package spp.protocol.util

import spp.protocol.auth.AccessPermission
import spp.protocol.auth.AccessType

object AccessChecker {

    fun hasInstrumentAccess(
        permissions: List<AccessPermission>,
        locationPattern: String
    ): Boolean {
        if (permissions.isEmpty()) {
            return true
        }

        val devBlackLists = permissions.filter { it.type == AccessType.BLACK_LIST }
        val inBlackList = devBlackLists.any { it ->
            val patterns = it.locationPatterns.map {
                it.replace(".", "\\.").replace("*", ".+")
            }
            patterns.any { locationPattern.matches(Regex(it)) }
        }

        val devWhiteLists = permissions.filter { it.type == AccessType.WHITE_LIST }
        val inWhiteList = devWhiteLists.any { it ->
            val patterns = it.locationPatterns.map {
                it.replace(".", "\\.").replace("*", ".+")
            }
            patterns.any { locationPattern.matches(Regex(it)) }
        }

        return if (devWhiteLists.isEmpty()) {
            !inBlackList
        } else if (devBlackLists.isEmpty()) {
            inWhiteList
        } else {
            !inBlackList || inWhiteList
        }
    }
}
