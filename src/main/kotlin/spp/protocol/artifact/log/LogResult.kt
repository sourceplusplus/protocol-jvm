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
package spp.protocol.artifact.log

import spp.protocol.artifact.ArtifactQualifiedName
import java.time.Instant

/**
 * todo: description.
 *
 * @since 0.2.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
data class LogResult(
    val artifactQualifiedName: ArtifactQualifiedName? = null,
    val orderType: LogOrderType,
    val timestamp: Instant,
    val logs: List<Log> = emptyList(),
    val total: Int = 0
) {
    fun mergeWith(logResult: LogResult): LogResult {
        val result: LogResult = logResult
        val combinedLogs: MutableSet<Log> = HashSet(logs)
        combinedLogs.addAll(result.logs)
        val finalLogs = ArrayList(combinedLogs).sortedWith(Comparator { l1: Log, l2: Log ->
            when (orderType) {
                LogOrderType.NEWEST_LOGS -> return@Comparator l2.timestamp.compareTo(l1.timestamp)
                LogOrderType.OLDEST_LOGS -> return@Comparator l1.timestamp.compareTo(l2.timestamp)
            }
        })
        return result.copy(logs = finalLogs, total = finalLogs.size)
    }

    fun truncate(amount: Int): LogResult {
        return if (logs.size > amount) {
            copy(logs = logs.subList(0, amount), total = logs.size)
        } else this
    }
}
