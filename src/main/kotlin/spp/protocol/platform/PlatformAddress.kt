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
package spp.protocol.platform

object PlatformAddress {
    const val PROCESSOR_CONNECTED = "spp.platform.status.processor-connected"
    const val PROCESSOR_DISCONNECTED = "spp.platform.status.processor-disconnected"
    const val PROBE_CONNECTED = "spp.platform.status.probe-connected"
    const val PROBE_DISCONNECTED = "spp.platform.status.probe-disconnected"
    const val MARKER_CONNECTED = "spp.platform.status.marker-connected"
    const val MARKER_DISCONNECTED = "spp.platform.status.marker-disconnected"
}
