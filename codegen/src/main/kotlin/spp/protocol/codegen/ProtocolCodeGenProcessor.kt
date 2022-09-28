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
package spp.protocol.codegen

import io.vertx.codegen.CodeGen
import io.vertx.codegen.CodeGenProcessor
import org.joor.Reflect

/**
 * Better (but still hacky) fix for https://github.com/sourceplusplus/sourceplusplus/issues/430.
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
