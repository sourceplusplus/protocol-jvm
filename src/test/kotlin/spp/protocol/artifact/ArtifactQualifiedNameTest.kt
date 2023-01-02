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
package spp.protocol.artifact

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import spp.protocol.artifact.ArtifactType.*

class ArtifactQualifiedNameTest {

    @Test
    fun `test js parent chain`() {
        ArtifactQualifiedName(
            identifier = "C:/test/main.js#SlNDYWxsRXhwcmVzc2lvbg==",
            type = EXPRESSION
        ).let {
            val fileParent = it.asParent()
            assertNotNull(fileParent)
            assertEquals(
                "C:/test/main.js",
                fileParent!!.identifier
            )
            assertEquals(fileParent.type, FILE)

            assertNull(fileParent.asParent())
        }

        ArtifactQualifiedName(
            identifier = "C:/test/main.js:executeDemos()",
            type = METHOD
        ).let {
            val fileParent = it.asParent()
            assertNotNull(fileParent)
            assertEquals(
                "C:/test/main.js",
                fileParent!!.identifier
            )
            assertEquals(fileParent.type, FILE)

            assertNull(fileParent.asParent())
        }

        ArtifactQualifiedName(
            identifier = "C:/test/main.js:executeDemos()#SlNDYWxsRXhwcmVzc2lvbg==",
            type = EXPRESSION
        ).let {
            val methodParent = it.asParent()
            assertNotNull(methodParent)
            assertEquals(
                "C:/test/main.js:executeDemos()",
                methodParent!!.identifier
            )
            assertEquals(methodParent.type, METHOD)

            val fileParent = methodParent.asParent()
            assertNotNull(fileParent)
            assertEquals(
                "C:/test/main.js",
                fileParent!!.identifier
            )
            assertEquals(fileParent.type, FILE)

            assertNull(fileParent.asParent())
        }

        ArtifactQualifiedName(
            identifier = "C:/test/main.js:TestClass.executeDemos()",
            type = METHOD
        ).let {
            val methodParent = it.asParent()
            assertNotNull(methodParent)
            assertEquals(
                "C:/test/main.js:TestClass",
                methodParent!!.identifier
            )
            assertEquals(methodParent.type, CLASS)

            val classParent = methodParent.asParent()
            assertNotNull(classParent)
            assertEquals(
                "C:/test/main.js",
                classParent!!.identifier
            )
            assertEquals(classParent.type, FILE)

            assertNull(classParent.asParent())
        }

        ArtifactQualifiedName(
            identifier = "C:/test/main.js:TestClass.executeDemos()#SlNDYWxsRXhwcmVzc2lvbg==",
            type = EXPRESSION
        ).let {
            val expressionParent = it.asParent()
            assertNotNull(expressionParent)
            assertEquals(
                "C:/test/main.js:TestClass.executeDemos()",
                expressionParent!!.identifier
            )
            assertEquals(expressionParent.type, METHOD)

            val methodParent = expressionParent.asParent()
            assertNotNull(methodParent)
            assertEquals(
                "C:/test/main.js:TestClass",
                methodParent!!.identifier
            )
            assertEquals(methodParent.type, CLASS)

            val classParent = methodParent.asParent()
            assertNotNull(classParent)
            assertEquals(
                "C:/test/main.js",
                classParent!!.identifier
            )
            assertEquals(classParent.type, FILE)

            assertNull(classParent.asParent())
        }
    }

    @Test
    fun `test parent qualified name`() {
        val methodExpression = ArtifactQualifiedName("com.example.TestClass.fun()#22", type = EXPRESSION)
        val method = methodExpression.asParent()
        assertNotNull(method)
        assertEquals("com.example.TestClass.fun()", method!!.identifier)
        assertEquals(METHOD, method.type)

        val clazz = method.asParent()
        assertNotNull(clazz)
        assertEquals("com.example.TestClass", clazz!!.identifier)
        assertEquals(CLASS, clazz.type)

        val fieldExpression = ArtifactQualifiedName("com.example.TestClass#22", type = EXPRESSION)
        val clazz2 = fieldExpression.asParent()
        assertNotNull(clazz2)
        assertEquals("com.example.TestClass", clazz2!!.identifier)
        assertEquals(CLASS, clazz2.type)
    }

    @Test
    fun `test parent qualified name with args`() {
        val methodExpression = ArtifactQualifiedName(
            "com.example.TestClass.fun(java.lang.String)#22",
            type = EXPRESSION
        )
        val method = methodExpression.asParent()
        assertNotNull(method)
        assertEquals("com.example.TestClass.fun(java.lang.String)", method!!.identifier)
        assertEquals(METHOD, method.type)

        val clazz = method.asParent()
        assertNotNull(clazz)
        assertEquals("com.example.TestClass", clazz!!.identifier)
        assertEquals(CLASS, clazz.type)
    }

    @Test
    fun `test is child of`() {
        val clazz = ArtifactQualifiedName("com.example.TestClass", type = CLASS)
        val method = ArtifactQualifiedName("com.example.TestClass.fun()", type = METHOD)
        val methodExpression = ArtifactQualifiedName("com.example.TestClass.fun()#22", type = EXPRESSION)

        assertTrue(methodExpression.isChildOf(clazz))
        assertTrue(methodExpression.isChildOf(method))
        assertFalse(methodExpression.isChildOf(methodExpression))

        assertTrue(method.isChildOf(clazz))
        assertFalse(method.isChildOf(method))

        assertFalse(clazz.isChildOf(clazz))
        assertFalse(clazz.isChildOf(method))
        assertFalse(method.isChildOf(methodExpression))
    }

    @Test
    fun `test is parent of`() {
        val clazz = ArtifactQualifiedName("com.example.TestClass", type = CLASS)
        val method = ArtifactQualifiedName("com.example.TestClass.fun()", type = METHOD)
        val methodExpression = ArtifactQualifiedName("com.example.TestClass.fun()#22", type = EXPRESSION)

        assertTrue(clazz.isParentOf(methodExpression))
        assertTrue(method.isParentOf(methodExpression))
        assertFalse(methodExpression.isParentOf(methodExpression))

        assertTrue(clazz.isParentOf(method))
        assertFalse(method.isParentOf(method))

        assertFalse(clazz.isParentOf(clazz))
        assertFalse(method.isParentOf(clazz))
        assertFalse(methodExpression.isParentOf(method))
    }

    @Test
    fun `test to class`() {
        val expression = ArtifactQualifiedName("com.example.TestClass.fun()#22", type = EXPRESSION)
        val clazz = expression.toClass()
        assertNotNull(clazz)
        assertEquals("com.example.TestClass", clazz!!.identifier)

        val method = ArtifactQualifiedName("com.example.TestClass.fun()", type = METHOD)
        val clazz2 = method.toClass()
        assertNotNull(clazz2)
        assertEquals("com.example.TestClass", clazz2!!.identifier)
    }

    @Test
    fun `test to inner class`() {
        val expression = ArtifactQualifiedName("com.example.TestClass\$InnerClass.fun()#22", type = EXPRESSION)
        val clazz = expression.toClass()
        assertNotNull(clazz)
        assertEquals("com.example.TestClass\$InnerClass", clazz!!.identifier)

        val method = ArtifactQualifiedName("com.example.TestClass\$InnerClass.fun()", type = METHOD)
        val clazz2 = method.toClass()
        assertNotNull(clazz2)
        assertEquals("com.example.TestClass\$InnerClass", clazz2!!.identifier)
    }
}
