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

import vassilidzuba.yacic.model.GlobalConfiguration
import vassilidzuba.yacic.model.Node
import vassilidzuba.yacic.model.ProjectConfiguration
import java.nio.file.Path
import java.util.List
import kotlin.test.Test


class KtProjectTest {

    @Test
    fun test1() {
        val gc: GlobalConfiguration = GlobalConfiguration()
        gc.actionDefinitionDirectory = Path.of("config/actionDefinitions")
        gc.setPipelineDirectory(Path.of("config/pipelines"))
        gc.setProjectDirectory(Path.of("config/projects"))
        val logsDirectory = Path.of("build/tmp/test/")
        gc.setLogsDirectory(logsDirectory)
        gc.setNodes(List.of<Node?>(Node("odin", "java", "git", "docker")))
        gc.setMaxNbLogs(5)

        val projectPath = Path.of("config/projects/hellojavakt.json")
        val projectConfiguration = ProjectConfiguration.readProjectConfiguration(projectPath);

        val project = KtProject()
        project.initialize(projectConfiguration, gc, "feature/initial")

        val runstatus = project.run()

        println("runstatus = ${runstatus}")
    }

}