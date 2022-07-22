/*
 * Source++, the open-source live coding platform.
 * Copyright (C) 2022 CodeBrig, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package spp.protocol.codegen

import io.vertx.codegen.CodeGen
import io.vertx.codegen.CodeGenProcessor
import org.joor.Reflect

/**
 * Better (but still hacky) fix for https://github.com/sourceplusplus/live-platform/issues/430.
 */
@Suppress("unused")
class ProtocolCodeGenProcessor : CodeGenProcessor() {
    init {
        val mappers = mutableListOf<List<CodeGen.Converter>>()
        Reflect.on(this).set("mappers", mappers)
        val inputStream = ProtocolCodeGenProcessor::class.java.getResource(
            "/META-INF/vertx/json-mappers.properties"
        )!!.openStream()
        Reflect.onClass(CodeGenProcessor::class.java).call("loadJsonMappers", mappers, inputStream)
    }
}
