package de.tschallacka.phpstormxdebugskip.listeners

import com.intellij.xdebugger.XDebugProcess
import com.intellij.xdebugger.XDebuggerManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Trinity
import com.intellij.xdebugger.XDebugSessionListener

class MyXDebugProcessListener(private val project: Project) : XDebuggerManagerListener {
    override fun processStarted(debugProcess: XDebugProcess) {
        val session = debugProcess.session;
        session.addSessionListener(object : XDebugSessionListener {
            override fun sessionPaused() {
                var frame = session.currentStackFrame;
                var frameDataObject = frame?.getEqualityObject();
                if (frameDataObject is Trinity<*, *, *>) {
                    var frameData = frameDataObject as Trinity<Integer, String, String>
                    var path = frameData.second;
                    var functioName = frameData.third;
                    var regex = Regex("(.*)->.*");
                    var match = regex.matchEntire(functioName);
                    var namespace = match?.groupValues?.get(1);
                    if (namespace != null && isSkippableNamespace(namespace)) {
                        session.stepOver(false);
                    }
                }
            }
        })
    }

    public fun isSkippableNamespace(namespace: String): Boolean {
      return namespace.contains("FOO\\Bar");

    }
}