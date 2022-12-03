/*
 * Source++, the continuous feedback platform for developers.
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
package spp.protocol.insight

data class InsightValue<T>(
    val value: T,
    val confidence: Double = 1.0,
    val derived: Boolean = false
) {
    fun asDerived() = InsightValue(value, confidence, true)

    companion object {
        fun <T> of(value: T) = InsightValue(value)
        fun <T> of(value: T, confidence: Double) = InsightValue(value, confidence)
        fun <T> of(value: T, confidence: Double, derived: Boolean) = InsightValue(value, confidence, derived)

        fun <T> derived(value: T) = InsightValue(value, derived = true)
    }
}
