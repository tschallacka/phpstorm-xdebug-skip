package de.tschallacka.phpstormxdebugskip.settings

import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable

@State(
    name = "de.tschallacka.phpstormxdebugskip.Settings",
    storages = [Storage("de.tschallacka.phpstormxdebugskip.xml", // File path relative to component container
        deprecated = false,
        roamingType = RoamingType.DEFAULT,
        storageClass = StateStorage::class,
        exportable = true)]
)
class Settings : PersistentStateComponent<Settings.State> {

    data class State(var namespaces: ArrayList<String> = arrayListOf(),
                     var filepaths: ArrayList<String> = arrayListOf(),
                     var skipIncludes: Boolean = false,
                     var skipConstructors: Boolean = false,
                     var haltOnBreakpoints: Boolean = true
        )

    var settingsState: State = State()

    override fun getState(): State? = settingsState

    override fun loadState(state: State) {
        XmlSerializerUtil.copyBean(state, this.settingsState)
    }

    companion object {
        fun getInstance(): Settings = service()
    }
}