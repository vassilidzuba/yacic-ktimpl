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

import java.io.FileOutputStream
import java.nio.file.Path
import java.util.List
import java.util.Set
import vassilidzuba.yacic.model.AbstractPipeline
import vassilidzuba.yacic.model.Node
import vassilidzuba.yacic.model.PipelineStatus

class KtPipeline : AbstractPipeline<KtPipelineConfiguration>() {
    var name: String = ""
    var desc: String = ""
    val steps = mutableListOf<KtStep>()
    var environment : Map<String, String> = mapOf()

    fun addStep(step: KtStep) {
        steps.add(step)
    }

    fun podmanstep(init: KtPodmanStep.() -> Unit) : Unit {
        var step = KtPodmanStep()
        step.init()
        steps.add(step)
    }

    fun shellstep(init: KtShellStep.() -> Unit) : Unit {
        var step = KtShellStep()
        step.init()
        steps.add(step)
    }

    fun substitute(environment: Map<String, String>) {
        steps.forEach { it.substitute(environment) }
    }

    override fun toString() : String {
        return "[$name($description) : $steps]"
    }

    override fun getType(): String? {
        TODO("Not yet implemented")
    }

    override fun run(
        pconfig: KtPipelineConfiguration?,
        logFile: Path?,
        nodes: MutableList<Node?>?,
        flags: MutableSet<String?>?
    ): PipelineStatus<KtPipelineConfiguration>? {

        var exitcode = "0"
        var failedStep : KtStep? = null

        FileOutputStream(logFile!!.toFile(), true).use { os ->

            for (step in steps ) {
                exitcode = step.run(pconfig, os, nodes)
                if (exitcode != "0") {
                    failedStep = step
                    break;
                }
            }
        }

        val ps =  PipelineStatus<KtPipelineConfiguration>(this)
        ps.id = name
        if (exitcode == "0") {
            ps.status = "ok"
        } else {
            ps.status = "step ${failedStep!!.id}"
        }

        return ps
    }

    override fun initialize(initialStep: String?): PipelineStatus<KtPipelineConfiguration?>? {
        val status = PipelineStatus<KtPipelineConfiguration?>(this)

        return status
    }

    override fun runNextStep(
        ps: PipelineStatus<KtPipelineConfiguration?>?,
        pconfig: KtPipelineConfiguration?,
        logFile: Path?,
        nodes: MutableList<Node?>?,
        flags: MutableSet<String?>?
    ): Boolean {
        TODO(reason)
    }

    override fun setActionContext(data: String?) {
        TODO(reason)
    }

    override fun getActionContext(): String? {
        TODO(reason)
    }

    override fun setDataArea(data: Path?) {
        TODO(reason)
    }

    override fun getDataArea(): Path? {
        TODO(reason)
    }
}
