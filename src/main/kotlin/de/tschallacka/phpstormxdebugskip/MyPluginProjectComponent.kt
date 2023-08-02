package de.tschallacka.phpstormxdebugskip

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.xdebugger.XDebuggerManager
import de.tschallacka.phpstormxdebugskip.listeners.MyXDebugProcessListener

class MyPluginProjectComponent(private val project: Project) : ProjectComponent {

    override fun projectOpened() {
        val listener = MyXDebugProcessListener(project)
        project.messageBus.connect().subscribe(XDebuggerManager.TOPIC, listener)
    }
}