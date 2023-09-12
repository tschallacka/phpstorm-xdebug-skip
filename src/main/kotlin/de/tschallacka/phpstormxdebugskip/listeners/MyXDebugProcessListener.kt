package de.tschallacka.phpstormxdebugskip.listeners

import com.intellij.openapi.application.ApplicationManager
import com.intellij.xdebugger.XDebugProcess
import com.intellij.xdebugger.XDebuggerManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Trinity
import com.intellij.util.containers.stream
import com.intellij.xdebugger.XDebugSessionListener
import com.intellij.xdebugger.XDebuggerManager
import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.breakpoints.XBreakpoint
import com.intellij.xdebugger.breakpoints.XLineBreakpoint
import de.tschallacka.phpstormxdebugskip.settings.Settings // Assuming this is the location of your Settings class
import io.ktor.util.*
import org.jetbrains.annotations.NonNls

class MyXDebugProcessListener(private val project: Project) : XDebuggerManagerListener {
    protected val Ns_FnRegex = Regex("(.*)->(.*)");
    override fun processStarted(debugProcess: XDebugProcess) {
        val session = debugProcess.session
        val debuggerManager = XDebuggerManager.getInstance(project)
        session.addSessionListener(object : XDebugSessionListener {
            override fun sessionPaused() {
                // create empty array XBreakpointBase[0] to avoid null pointer exception
                var breakpoints = arrayOf<XBreakpoint<*>>()
                ApplicationManager.getApplication().runReadAction {
                    breakpoints = debuggerManager.breakpointManager.allBreakpoints
                }
                val frame = session.currentStackFrame
                val currentSourcePosition = frame?.sourcePosition
                val frameDataObject = frame?.getEqualityObject()
                if (frameDataObject is Trinity<*, *, *>) {
                    val frameData = frameDataObject as Trinity<Int, String, String>
                    val path = frameData.second
                    val localpath = frame.sourcePosition?.file?.path
                    val functionName = frameData.third

                    val isBreakpoint = currentSourcePosition != null && breakpoints.stream().anyMatch {
                        if(!it.isEnabled) return@anyMatch false
                        if(!(it is XLineBreakpoint)) {
                            return@anyMatch false
                        }

                        val sourceposition = it.sourcePosition
                        val equals = doSourcePositionsMatch(sourceposition, currentSourcePosition);
                        return@anyMatch equals == true
                    }
                    // this doesn't work yet...
                    if (isBreakpoint && shouldHaltOnBreakpoints()) {
                        return
                    }
                    if(skipIncludes(functionName, path, localpath)) {
                        session.stepInto()
                        return
                    }
                    if (skipConstructor(functionName)) {
                        session.stepInto()
                        return
                    }
//                    if (namespace != null && isSkippableNamespace("\\"+namespace)) {
//                        session.stepInto()
//                        return
//                    }
                    if (path != null && isSkippableFilePath(path)) {
                        session.stepInto()
                        return
                    }
                    if (localpath != null && isSkippableFilePath(localpath)) {
                        session.stepInto()
                        return
                    }
                }
            }
        })
    }

    public fun doSourcePositionsMatch(sourcePosition: XSourcePosition?, currentSourcePosition: XSourcePosition?): Boolean {
        if(sourcePosition == null || currentSourcePosition == null) {
            return false
        }
        if(sourcePosition.file != currentSourcePosition.file) {
            return false
        }
        if(sourcePosition.line != currentSourcePosition.line) {
            return false
        }
        if(sourcePosition.offset != currentSourcePosition.offset) {
            return false
        }
        return true
    }

    private fun shouldHaltOnBreakpoints(): Boolean {
        val settings = Settings.getInstance()
        return settings.settingsState.haltOnBreakpoints
    }

    private fun skipConstructor(functionName: String): Boolean {
        val settings = Settings.getInstance()
        val match = this.Ns_FnRegex.matchEntire(functionName)
        val cleanedName = match?.groupValues?.get(2) ?: return false
        if (!settings.settingsState.skipConstructors) return false
        if (cleanedName == "__construct") {
            return true
        }
        return false
    }
    private fun skipIncludes(functionName: String, path: String, localpath: @NonNls String?): Boolean {
        val settings = Settings.getInstance()
        if(!settings.settingsState.skipIncludes) return false
        if(functionName == "include" || functionName == "require" || functionName == "include_once" || functionName == "require_once") {
            // get extension from path
            if(settings.settingsState.alsoSkipNonPhpIncludes) {
                return true
            }
            val extension = path.substringAfterLast(".").toLowerCasePreservingASCIIRules();
            if(extension == "php") {
                return true
            }
        }
        return false
    }
    private fun isSkippableNamespace(namespace: String): Boolean {
        val settings = Settings.getInstance()
        val namespaces = settings.settingsState.namespaces;
        val match = namespaces.any {
            ns -> namespace.contains(ns)
        }
        return match
    }

    private fun isSkippableFilePath(path: String): Boolean {
        val settings = Settings.getInstance()
        val filepaths = settings.settingsState.filepaths;
        val match = filepaths.any {
            filepath -> path.contains(filepath)
        }
        return match
    }
}