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

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.script.ScriptEngineManager
import vassilidzuba.yacic.model.Node;
import java.util.ArrayList
import java.util.HashSet

class Scripting {

    fun evalScript( environment : Map<String, String>, script: Path) : KtPipeline {
        val config = KtPipelineConfiguration()
        val logFile = Paths.get("target/test.log")
        val nodes : List<Node?> = ArrayList<Node>()
        val flags : Set<String?> = HashSet()

        val strPipeline = Files.readString(script, StandardCharsets.UTF_8)

        val prelim = """
        package vassilidzuba.yacic.ktimpl
        
        val environment = mapOf<String, String>(
          
        """

        val sb = StringBuilder()

        sb.append(prelim)

        environment.forEach { (key, value) ->
            sb.append("      \"${key}\" to \"${value}\",\n")
        }
        sb.append("       \"foo\" to \"bar\")\n")


        sb.append(strPipeline);
        println(sb.toString())

        val engine = ScriptEngineManager().getEngineByExtension("main.kts")

        println("Going to run ze skript")
        val ret = engine.eval(sb.toString())

        println(ret.javaClass)
        println(ret)

        if (ret is KtPipeline) {
            println("ret is KtPipeline")
            return ret
        } else {
            throw RuntimeException("failed to evaluate script")
        }
    }
}