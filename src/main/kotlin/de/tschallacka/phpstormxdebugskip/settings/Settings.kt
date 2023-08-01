package de.tschallacka.phpstormxdebugskip.settings

import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable

@State(
    name = "de.tschallacka.phpstormxdebugskip.Settings",
    storages = [Storage(StoragePathMacros.WORKSPACE_FILE)]
)
class Settings : PersistentStateComponent<Settings.State> {

    data class State(var namespaces: ArrayList<String> = arrayListOf(),
                     var filepaths: ArrayList<String> = arrayListOf())

    var settingsState: State = State()

    override fun getState(): State? = settingsState

    override fun loadState(state: State) {
        XmlSerializerUtil.copyBean(state, this.settingsState)
    }

    companion object {
        fun getInstance(): Settings = service()
    }
}