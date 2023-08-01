package de.tschallacka.phpstormxdebugskip.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class SettingsConfigurable : Configurable {

    private var settingsDialog: SettingsDialog? = null

    override fun createComponent(): JComponent? {
        settingsDialog = SettingsDialog()
        return settingsDialog?.createCenterPanel()
    }

    override fun isModified(): Boolean {
        // Here you can implement a check to see if settings have been modified
        return false
    }

    override fun apply() {
        // Here you should implement applying the settings
    }

    override fun reset() {
        // Here you can implement resetting your settings UI to the stored state
    }

    override fun getDisplayName(): String {
        // The display name for the settings component
        return "PHPStorm Xdebug Skip"
    }
}