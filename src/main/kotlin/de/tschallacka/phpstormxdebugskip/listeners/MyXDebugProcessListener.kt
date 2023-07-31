package de.tschallacka.phpstormxdebugskip.listeners

import com.intellij.xdebugger.XDebugProcess
import com.intellij.xdebugger.XDebuggerManagerListener
import com.intellij.openapi.project.Project
import com.intellij.xdebugger.XDebugSessionListener

class MyXDebugProcessListener(private val project: Project) : XDebuggerManagerListener {
    override fun processStarted(debugProcess: XDebugProcess) {
        val session = debugProcess.session
        session.addSessionListener(object : XDebugSessionListener {
            override fun sessionPaused() {
                var frame = session.currentStackFrame;
                throw RuntimeException("foobar")
            }
        })
    }
}