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

import vassilidzuba.yacic.model.AbstractAction
import vassilidzuba.yacic.model.AbstractPipeline
import vassilidzuba.yacic.model.Node
import vassilidzuba.yacic.model.PipelineConfiguration
import vassilidzuba.yacic.model.PipelineStatus
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.file.Path
import java.util.List
import java.util.Set

val reason = "not yet implemented"



fun pipeline(environment: Map<String, String>,  init: KtPipeline.() -> Unit) : KtPipeline {
    var p = KtPipeline()
    p.environment = environment
    p.init()
    p.substitute(environment)
    return p
}

fun substituteVariables(data: String, environment: Map<String, String> ) : String {
    var s = data
    environment.forEach{ (k,v) ->
       s = s.replace("@{$k}", v)
    }

    return s;
}
