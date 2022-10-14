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
package spp.protocol.view

import io.vertx.core.json.JsonArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LiveViewTest {

    @Test
    fun `test mutable entity ids`() {
        val sub = LiveView(
            "id",
            mutableSetOf("entity-1"),
            viewConfig = LiveViewConfig("name", listOf("metrics"))
        )
        val subJson = sub.toJson()
        assertEquals(JsonArray(listOf("entity-1")), subJson.getJsonArray("entityIds"))

        sub.entityIds.add("entity-2")
        val subJson2 = sub.toJson()
        assertEquals(JsonArray(listOf("entity-1", "entity-2")), subJson2.getJsonArray("entityIds"))

        val fromJson = LiveView(subJson2)
        assertEquals(mutableSetOf("entity-1", "entity-2"), fromJson.entityIds)
    }
}
