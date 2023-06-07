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
package spp.protocol.view.rule

import spp.protocol.instrument.LiveMeter

class MethodTimerAvgRule(private val meter: LiveMeter) : ViewRule(
    "${meter.id}_avg",
    buildString {
        append("(")
        append(meter.id).append("_timer_duration_sum")
        append("/")
        append(meter.id).append("_timer_meter")
        append(").avg(['service']).service(['service'], Layer.GENERAL)")
    },
    meterIds = listOf(meter.id!!)
)
