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
package spp.protocol.utils

fun Int.toPrettyDuration(translate: (String) -> String = { it }): String {
    return toLong().toPrettyDuration(translate)
}

@JvmOverloads
fun Double.fromPerSecondToPrettyFrequency(translate: (String) -> String = { it }): String {
    return when {
        this > 1000000.0 -> "${this / 1000000.0.toInt()}M/" + translate("sec")
        this > 1000.0 -> "${this / 1000.0.toInt()}K/" + translate("sec")
        this > 1.0 -> "${this.toInt()}/" + translate("sec")
        else -> "${(this * 60.0).toInt()}/" + translate.invoke("min")
    }
}

fun Long.toPrettyDuration(translate: (String) -> String = { it }): String {
    val days = this / 86400000.0
    if (days > 1) {
        return "${days.toInt()}" + translate("dys")
    } else if (days == 1.0) {
        return "${days.toInt()}" + translate("dy")
    }
    val hours = this / 3600000.0
    if (hours > 1) {
        return "${hours.toInt()}" + translate("hrs")
    } else if (hours == 1.0) {
        return "${hours.toInt()}" + translate("hr")
    }
    val minutes = this / 60000.0
    if (minutes > 1) {
        return "${minutes.toInt()}" + translate("mins")
    } else if (minutes == 1.0) {
        return "${minutes.toInt()}" + translate("min")
    }
    val seconds = this / 1000.0
    if (seconds > 1) {
        return "${seconds.toInt()}" + translate("secs")
    } else if (seconds == 1.0) {
        return "${seconds.toInt()}" + translate("sec")
    }
    return "$this" + translate("ms")
}
