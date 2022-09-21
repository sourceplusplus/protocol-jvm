/*
 * Source++, the open-source live coding platform.
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

object AccessChecker {

    fun hasInstrumentAccess(
        permissions: List<AccessPermission>,
        locationPattern: String
    ): Boolean {
        if (permissions.isEmpty()) {
            return true
        }

        val devBlackLists = permissions.filter { it.type == AccessType.BLACK_LIST }
        val inBlackList = devBlackLists.any {
            val patterns = it.locationPatterns.map {
                it.replace(".", "\\.").replace("*", ".+")
            }
            patterns.any { locationPattern.matches(Regex(it)) }
        }

        val devWhiteLists = permissions.filter { it.type == AccessType.WHITE_LIST }
        val inWhiteList = devWhiteLists.any {
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
