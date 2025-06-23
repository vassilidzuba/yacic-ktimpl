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

import org.slf4j.LoggerFactory
import vassilidzuba.yacic.model.Node
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.Test

class KtPodmanStepTest {
    val log = LoggerFactory.getLogger(KtPodmanStepTest::class.java)

    @Test
    fun test1() {
        val pconfig = KtPipelineConfiguration()
        val nodes : MutableList<Node?>? = mutableListOf(Node("odin", "git", "test"))
        var logFile = Path.of("build/tmp/test/foo.log")
        Files.deleteIfExists(logFile)

        var step = KtPodmanStep()
        step.id = "test"
        step.description = "test podman test"
        step.image = "docker.io/library/debian:stable"
        step.command = "podman run -it docker.io/library/debian:stable ls"
        step.role = "test"

        FileOutputStream(logFile!!.toFile(), true).use { os ->

            val status = step.run( pconfig, os, nodes)
            os.flush()

            println("exist status = $status")
        }

        val logdata = Files.readString(logFile)
        log.info("log : {}", logdata)

    }
}