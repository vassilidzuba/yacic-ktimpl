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

import org.junit.jupiter.api.Test
import vassilidzuba.yacic.model.Node
import java.nio.file.Files
import java.nio.file.Path

class KtPipelineTest {

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

        val p = pipeline(environment) {
            name = "test"
            description = "test pipeline"

            podmanstep {
                id = "testpodman"
                description = "test podman step"
                image = "docker.io/library/debian:stable"
                command = " @{IMAGE} ls"
                role = "test"
            }

            shellstep {
                id = "testshell"
                description = "test shell step"
                command = "ls"
                role = "test"
            }
        }

        val pconfig = KtPipelineConfiguration()
        var logFile = Path.of("build/tmp/test/foo.log")
        Files.deleteIfExists(logFile)
        val nodes : MutableList<Node?>? = mutableListOf(Node("odin", "git", "test"))
        val flags : MutableSet<String?>?  = mutableSetOf()

        val status = p.run(pconfig, logFile, nodes, flags)

        println("status ! $status")
    }
}