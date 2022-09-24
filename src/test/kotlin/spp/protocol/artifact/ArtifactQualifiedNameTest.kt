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

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class ArtifactQualifiedNameTest {

    @Test
    fun `test parent qualified name`() {
        val methodExpression = ArtifactQualifiedName("com.example.TestClass.fun()#22", type = ArtifactType.EXPRESSION)
        val method = methodExpression.asParent()
        assertNotNull(method)
        assertEquals("com.example.TestClass.fun()", method!!.identifier)
        assertEquals(ArtifactType.METHOD, method.type)

        val clazz = method.asParent()
        assertNotNull(clazz)
        assertEquals("com.example.TestClass", clazz!!.identifier)
        assertEquals(ArtifactType.CLASS, clazz.type)

        val fieldExpression = ArtifactQualifiedName("com.example.TestClass#22", type = ArtifactType.EXPRESSION)
        val clazz2 = fieldExpression.asParent()
        assertNotNull(clazz2)
        assertEquals("com.example.TestClass", clazz2!!.identifier)
        assertEquals(ArtifactType.CLASS, clazz2.type)
    }

    @Test
    fun `test parent qualified name with args`() {
        val methodExpression = ArtifactQualifiedName(
            "com.example.TestClass.fun(java.lang.String)#22",
            type = ArtifactType.EXPRESSION
        )
        val method = methodExpression.asParent()
        assertNotNull(method)
        assertEquals("com.example.TestClass.fun(java.lang.String)", method!!.identifier)
        assertEquals(ArtifactType.METHOD, method.type)

        val clazz = method.asParent()
        assertNotNull(clazz)
        assertEquals("com.example.TestClass", clazz!!.identifier)
        assertEquals(ArtifactType.CLASS, clazz.type)
    }
}
