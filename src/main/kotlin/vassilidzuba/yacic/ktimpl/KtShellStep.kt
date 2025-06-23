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


class KtShellStep : KtStep() {
    override val category = "shell"


    override fun run(
        pconfig: KtPipelineConfiguration?,
        os: OutputStream?,
        nodes: MutableList<Node?>?
    ): String? {
        println("running the step $id ($category)")
        if (description != "") {
            println("        description: $description")
        }
        if (command != "") {
            println("        command: $command")
        }

        val podmanutil = Podmanutil()
        podmanutil.nodes = nodes

        podmanutil.runLocalOrRemote(os, command, role)


        return "ok"
    }

    override fun toString(): String {
        return "[$id $category]"
    }
}

