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
import vassilidzuba.yacic.model.GlobalConfiguration
import vassilidzuba.yacic.model.Node
import vassilidzuba.yacic.model.Project
import vassilidzuba.yacic.model.ProjectConfiguration
import vassilidzuba.yacic.model.RunStatus
import vassilidzuba.yacic.model.exceptions.NoSuchBranchException
import vassilidzuba.yacic.persistence.PersistenceManager
import java.nio.file.Files
import java.nio.file.Path
import java.util.function.Supplier



class KtProject : Project {
    val log = LoggerFactory.getLogger(KtProject::class.java)
    private var prconfig: ProjectConfiguration? = null
    private var glconfig: GlobalConfiguration? = null
    private var branch: String = ""
    private val pm: PersistenceManager = PersistenceManager()

    override fun initialize(
        config: ProjectConfiguration?,
        glconfig: GlobalConfiguration?,
        branch: String?
    ) {
        this.prconfig = config
        this.glconfig = glconfig
        this.branch = branch!!
    }

    override fun run(): RunStatus? {
        val environment = HashMap<String, String>()

        val branchDir = prconfig!!.getBranchDir(branch)
            .orElseThrow<NoSuchBranchException?>(Supplier { NoSuchBranchException(branch, prconfig!!.getProject()) })

        val properties = prconfig!!.getProperties()

        environment.putAll(properties);
        environment.put("PROJECT", prconfig!!.getProject());
        environment.put("REPO", prconfig!!.getRepo());
        environment.put("ROOT", prconfig!!.getRoot());
        environment.put("BRANCHNAME", branch);

        environment.put("BRANCHDIR", branchDir);
        environment.put("DATAAREA",
            prconfig!!.getRoot() + "/" + prconfig!!.getProject() + "/" + branchDir);
        environment.put("BUILDID", Integer.toString(pm.getNextBuildId(prconfig!!.getProject(), branch)));

        val script = glconfig!!.pipelineDirectory.resolve(prconfig!!.getPipeline(branch) + ".kts")

        val pipeline = Scripting().evalScript(environment, script)

        val pconfig = KtPipelineConfiguration()
        var logFile = Path.of("build/tmp/test/foo.log")
        Files.deleteIfExists(logFile)

        val nodes = glconfig!!.nodes
        val flags : MutableSet<String?>?  = mutableSetOf()

        val pstatus = pipeline.run(pconfig, logFile, nodes, flags)

        var runStatus = RunStatus(prconfig!!.project, branch, "now", pstatus!!.status, 0, pipeline.name )

        return runStatus

    }

    override fun reload() {
        log.info("reload is useless for a Kt project")
    }

}