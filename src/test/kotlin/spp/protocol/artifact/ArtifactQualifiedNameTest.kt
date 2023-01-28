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
            type = FUNCTION
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
            val functionParent = it.asParent()
            assertNotNull(functionParent)
            assertEquals(
                "C:/test/main.js:executeDemos()",
                functionParent!!.identifier
            )
            assertEquals(functionParent.type, FUNCTION)

            val fileParent = functionParent.asParent()
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
            type = FUNCTION
        ).let {
            val functionParent = it.asParent()
            assertNotNull(functionParent)
            assertEquals(
                "C:/test/main.js:TestClass",
                functionParent!!.identifier
            )
            assertEquals(functionParent.type, CLASS)

            val classParent = functionParent.asParent()
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
            assertEquals(expressionParent.type, FUNCTION)

            val functionParent = expressionParent.asParent()
            assertNotNull(functionParent)
            assertEquals(
                "C:/test/main.js:TestClass",
                functionParent!!.identifier
            )
            assertEquals(functionParent.type, CLASS)

            val classParent = functionParent.asParent()
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
        val functionExpression = ArtifactQualifiedName("com.example.TestClass.fun()#22", type = EXPRESSION)
        val function = functionExpression.asParent()
        assertNotNull(function)
        assertEquals("com.example.TestClass.fun()", function!!.identifier)
        assertEquals(FUNCTION, function.type)

        val clazz = function.asParent()
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
        val functionExpression = ArtifactQualifiedName(
            "com.example.TestClass.fun(java.lang.String)#22",
            type = EXPRESSION
        )
        val function = functionExpression.asParent()
        assertNotNull(function)
        assertEquals("com.example.TestClass.fun(java.lang.String)", function!!.identifier)
        assertEquals(FUNCTION, function.type)

        val clazz = function.asParent()
        assertNotNull(clazz)
        assertEquals("com.example.TestClass", clazz!!.identifier)
        assertEquals(CLASS, clazz.type)
    }

    @Test
    fun `test is child of`() {
        val clazz = ArtifactQualifiedName("com.example.TestClass", type = CLASS)
        val function = ArtifactQualifiedName("com.example.TestClass.fun()", type = FUNCTION)
        val functionExpression = ArtifactQualifiedName("com.example.TestClass.fun()#22", type = EXPRESSION)

        assertTrue(functionExpression.isChildOf(clazz))
        assertTrue(functionExpression.isChildOf(function))
        assertFalse(functionExpression.isChildOf(functionExpression))

        assertTrue(function.isChildOf(clazz))
        assertFalse(function.isChildOf(function))

        assertFalse(clazz.isChildOf(clazz))
        assertFalse(clazz.isChildOf(function))
        assertFalse(function.isChildOf(functionExpression))
    }

    @Test
    fun `test is parent of`() {
        val clazz = ArtifactQualifiedName("com.example.TestClass", type = CLASS)
        val function = ArtifactQualifiedName("com.example.TestClass.fun()", type = FUNCTION)
        val functionExpression = ArtifactQualifiedName("com.example.TestClass.fun()#22", type = EXPRESSION)

        assertTrue(clazz.isParentOf(functionExpression))
        assertTrue(function.isParentOf(functionExpression))
        assertFalse(functionExpression.isParentOf(functionExpression))

        assertTrue(clazz.isParentOf(function))
        assertFalse(function.isParentOf(function))

        assertFalse(clazz.isParentOf(clazz))
        assertFalse(function.isParentOf(clazz))
        assertFalse(functionExpression.isParentOf(function))
    }

    @Test
    fun `test to class`() {
        val expression = ArtifactQualifiedName("com.example.TestClass.fun()#22", type = EXPRESSION)
        val clazz = expression.toClass()
        assertNotNull(clazz)
        assertEquals("com.example.TestClass", clazz!!.identifier)

        val function = ArtifactQualifiedName("com.example.TestClass.fun()", type = FUNCTION)
        val clazz2 = function.toClass()
        assertNotNull(clazz2)
        assertEquals("com.example.TestClass", clazz2!!.identifier)
    }

    @Test
    fun `test to inner class`() {
        val expression = ArtifactQualifiedName("com.example.TestClass\$InnerClass.fun()#22", type = EXPRESSION)
        val clazz = expression.toClass()
        assertNotNull(clazz)
        assertEquals("com.example.TestClass\$InnerClass", clazz!!.identifier)

        val function = ArtifactQualifiedName("com.example.TestClass\$InnerClass.fun()", type = FUNCTION)
        val clazz2 = function.toClass()
        assertNotNull(clazz2)
        assertEquals("com.example.TestClass\$InnerClass", clazz2!!.identifier)
    }
}
