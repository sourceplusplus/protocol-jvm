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
package spp.protocol.instrument.meter

/**
 * todo: description.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
enum class MetricValueType {
    NUMBER,
    NUMBER_SUPPLIER,
    NUMBER_EXPRESSION,
    VALUE,
    VALUE_SUPPLIER,
    VALUE_EXPRESSION,
    OBJECT_LIFESPAN;

    fun isAlwaysNumeric(): Boolean {
        return this == NUMBER || this == NUMBER_SUPPLIER || this == NUMBER_EXPRESSION || this == OBJECT_LIFESPAN
    }

    fun isExpression(): Boolean {
        return this == NUMBER_EXPRESSION || this == VALUE_EXPRESSION
    }
}
