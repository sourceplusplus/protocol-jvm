/*
 * Source++, the continuous feedback platform for developers.
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
package spp.protocol.insight

enum class InsightType {
    METHOD_DURATION,
    METHOD_CALL_PROBABILITY,
    LOOP_ITERATION_AVG,
    LOOP_ITERATION_MAX,
    LOOP_ITERATION_MIN,
    LOOP_ITERATION_STDDEV,
    LOOP_ITERATION_SUM,
    LOOP_ITERATION_VARIANCE,
    LOOP_DURATION_AVG,
    LOOP_DURATION_MAX,
    LOOP_DURATION_MIN,
    LOOP_DURATION_STDDEV,
    LOOP_DURATION_SUM,
    LOOP_DURATION_VARIANCE,
}
