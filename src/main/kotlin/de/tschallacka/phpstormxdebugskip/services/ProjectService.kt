package de.tschallacka.phpstormxdebugskip.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.xdebugger.XDebuggerManager
import de.tschallacka.phpstormxdebugskip.MyBundle
import de.tschallacka.phpstormxdebugskip.listeners.MyXDebugProcessListener

@Service(Service.Level.PROJECT)
class ProjectService(project: Project) {

    init {
        val listener = MyXDebugProcessListener(project)
        project.messageBus.connect().subscribe(XDebuggerManager.TOPIC, listener)

        thisLogger().info(MyBundle.message("projectService", project.name))
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    fun getRandomNumber() = (1..100).random()
}