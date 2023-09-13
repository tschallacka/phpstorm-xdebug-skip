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
    }
}