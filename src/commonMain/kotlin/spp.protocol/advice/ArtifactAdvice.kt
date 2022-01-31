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
package spp.protocol.advice

import spp.protocol.artifact.ArtifactQualifiedName

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
abstract class ArtifactAdvice(
    val artifact: ArtifactQualifiedName,
    val category: AdviceCategory,
    val type: AdviceType
) {

    private val artifactAdviceListeners = mutableListOf<ArtifactAdviceListener>()

    fun addArtifactAdviceListener(artifactAdviceListener: ArtifactAdviceListener) {
        artifactAdviceListeners.add(artifactAdviceListener)
    }

    protected fun triggerUpdated() {
        artifactAdviceListeners.forEach(ArtifactAdviceListener::updated)
    }

    /**
     * Determine if [artifactAdvice] is the same as this one. If so, the first [ArtifactAdvice] should be updated.
     */
    abstract fun isSameArtifactAdvice(artifactAdvice: ArtifactAdvice): Boolean

    abstract fun updateArtifactAdvice(artifactAdvice: ArtifactAdvice)
}
