package de.tschallacka.phpstormxdebugskip.settings

import com.intellij.openapi.ui.DialogWrapper
import org.jetbrains.annotations.Nullable
import javax.swing.*

class SettingsDialog : DialogWrapper(true) {
    private lateinit var myMainPanel: JPanel
    private lateinit var myNamespaceList: JList<String>
    private lateinit var myFilePathList: JList<String>

    init {
        init()
        title = "Xdebug Skip Settings"
    }

    @Nullable
    public override fun createCenterPanel(): JComponent? {
        return myMainPanel
    }
}