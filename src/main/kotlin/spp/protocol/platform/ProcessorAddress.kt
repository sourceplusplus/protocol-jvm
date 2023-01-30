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
package spp.protocol.platform

object ProcessorAddress {
    const val REMOTE_REGISTERED = "spp.processor.status.remote-registered"
    const val LIVE_INSTRUMENT_APPLIED = "spp.processor.status.live-instrument-applied"
    const val LIVE_INSTRUMENT_REMOVED = "spp.processor.status.live-instrument-removed"
}
