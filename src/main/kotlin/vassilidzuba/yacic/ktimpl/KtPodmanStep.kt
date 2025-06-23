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
import vassilidzuba.yacic.podmanutil.Podmanutil
import java.io.OutputStream

class KtPodmanStep : KtStep() {
    var image: String = ""
    var setup: String = ""
    override val category = "podman"

    override fun toString(): String {
        return "[$id $category '$image']"
    }

    override fun substitute(environment: Map<String, String>)  {
        super.substitute(environment)
        setup = substituteVariables(setup, environment)
        setup = substituteVariables(setup, mapOf<String, String>("ACTIONID" to id!!))
        command = substituteVariables(command, mapOf<String, String>("IMAGE" to image))

    }

    override fun run(
        pconfig: KtPipelineConfiguration?,
        os: OutputStream?,
        nodes: MutableList<Node?>?
    ): String? {
        println("running step $id ($category)")
        if (description != "") {
            println("        description: $description")
        }
        if (command != "") {
            println("        command: $command")
        }
        if (image != "") {
            println("        image: $image")
        }
        if (setup != "") {
            println("        setup: $setup")
        }

        var podmanutil = Podmanutil()
        podmanutil.nodes = nodes

        val fullcommand = "podman run -it --rm ${command} ; echo PODMANTERMINATION \$?; "

        val status = podmanutil.runLocalOrRemote(os, command, role)

        return status
    }

}
