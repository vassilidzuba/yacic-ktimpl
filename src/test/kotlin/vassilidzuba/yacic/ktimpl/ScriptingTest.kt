/**
Copyright 2025 Vassili Dzuba

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 **/
package vassilidzuba.yacic.ktimpl

import vassilidzuba.yacic.model.Node
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.test.Test

class ScriptingTest {

    @Test
    fun test1() {

        val environment = mapOf(
            "PROJECT" to "hello",
            "REPO" to "https://git.lan/foo.git",
            "ROOT" to "/mnt/yacic",
            "BRANCHNAME" to "main",
            "BRANCHDIR" to "b0",
            "DATAAREA" to "/mnt/yacic/hello",
            "BUILDID" to ""
        )

        val pconfig = KtPipelineConfiguration()
        val nodes : MutableList<Node?>? = mutableListOf(Node("odin", "git", "java"))
        val flags : MutableSet<String?>?  = mutableSetOf("NODOCKER")
        val logFile = Paths.get("build/tmp/test/foo.log")

        val pipeline = Scripting().evalScript(environment, Path.of("config/pipelines/java-maven.kts"))

        pipeline.run(
            pconfig,
            logFile,
            nodes,
            flags
        )
    }
}