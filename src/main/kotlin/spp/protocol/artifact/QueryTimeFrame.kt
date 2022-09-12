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
package spp.protocol.artifact

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
enum class QueryTimeFrame(val minutes: Int, val id: String) {
    //todo: support custom time frames
    LAST_MINUTE(1, "1_minute"),
    LAST_5_MINUTES(5, "5_minutes"),
    LAST_15_MINUTES(15, "15_minutes"),
    LAST_30_MINUTES(30, "30_minutes"),
    LAST_HOUR(60, "hour"),
    LAST_3_HOURS(60 * 3, "3_hours"); //todo: id = enum name

    val description = id.toUpperCase().replace("_", " ")

    companion object {
        fun valueOf(minutes: Int): QueryTimeFrame {
            for (timeFrame in values()) {
                if (timeFrame.minutes == minutes) {
                    return timeFrame
                }
            }
            throw IllegalArgumentException("No time frame for minutes: $minutes")
        }
    }
}
