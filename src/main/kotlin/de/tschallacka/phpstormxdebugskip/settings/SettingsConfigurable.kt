package de.tschallacka.phpstormxdebugskip.settings

import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.ui.ColoredListCellRenderer
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import javax.swing.*

class SettingsConfigurable(private val project: Project) : Configurable {

    private val settings = Settings.getInstance()

    private val filePathModel = DefaultListModel<String>().apply { addAll(settings.settingsState.filepaths) }
    private val namespaceModel = DefaultListModel<String>().apply { addAll(settings.settingsState.namespaces) }
    private var skipIncludes = settings.settingsState.skipIncludes;
    private var alsoSkipNonPhpIncludes = settings.settingsState.alsoSkipNonPhpIncludes;
    private var skipConstructors = settings.settingsState.skipConstructors;
    private var haltOnBreakpoints = settings.settingsState.haltOnBreakpoints;
    private val filePathList = JBList<String>(filePathModel)
    private val namespaceList = JBList<String>(namespaceModel)

    override fun createComponent(): JComponent {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
        JLabel("Don't halt the debugger in these file paths").also { panel.add(it) }
        // Create a decorator for file paths
        val filePathDecorator = ToolbarDecorator.createDecorator(filePathList)
        filePathDecorator.setAddAction { addButton ->
            FileChooser.chooseFiles(
                FileChooserDescriptor(true, true, false, false, false, true),
                project,
                null
            ) { selectedFiles ->
                // Assuming you want to add all selected files to the list
                selectedFiles.forEach { file ->
                    filePathModel.addElement(file.path)
                }
            }
        }.setRemoveAction { removeButton ->
            val selectedFiles = filePathList.selectedValuesList
            selectedFiles.forEach { filePath ->
                filePathModel.removeElement(filePath)
            }
        }
        panel.add(filePathDecorator.createPanel())
        val skipincludes = JCheckBox("skip .php include/requires")
        skipincludes.isSelected = skipIncludes
        skipincludes.addActionListener() {
            skipIncludes = skipincludes.isSelected
        }
        panel.add(skipincludes)
        val alsoSkipNonphpIncludes = JCheckBox("Also skip non .php extension includes(template files, etc...)");
        alsoSkipNonphpIncludes.isSelected = alsoSkipNonPhpIncludes
        alsoSkipNonphpIncludes.addActionListener() {
            alsoSkipNonPhpIncludes = alsoSkipNonphpIncludes.isSelected
        }
        panel.add(alsoSkipNonphpIncludes)
        val skipconstructors = JCheckBox("skip constructors")
        skipconstructors.isSelected = skipConstructors
        skipconstructors.addActionListener() {
            skipConstructors = skipconstructors.isSelected
        }
        panel.add(skipconstructors)
        val haltonbreakpoints = JCheckBox("halt on breakpoints, when skipping")
        haltonbreakpoints.isSelected = haltOnBreakpoints
        haltonbreakpoints.addActionListener() {
            haltOnBreakpoints = haltonbreakpoints.isSelected
        }
        panel.add(haltonbreakpoints)
        return panel
    }

    override fun isModified(): Boolean {
        val filePaths = (0 until filePathModel.size()).map { i -> filePathModel.getElementAt(i) }
        val state = settings.settingsState;
        // compare filePaths to settings.settingsState.filepaths
        val similarFilePaths = filePaths == state.filepaths

        //val namespaces = (0 until namespaceModel.size()).map { i -> namespaceModel.getElementAt(i) }
        //val similarNamespaces = namespaces == state.namespaces
        val similarSkipIncludes = skipIncludes == state.skipIncludes
        val similarSkipConstructors = skipConstructors == state.skipConstructors
        val similarHaltOnBreakpoints = haltOnBreakpoints == state.haltOnBreakpoints
        val similarAlsoSkipNonPhpIncludes = alsoSkipNonPhpIncludes == state.alsoSkipNonPhpIncludes

        return !similarFilePaths
                || !similarSkipIncludes
                || !similarSkipConstructors
                || !similarHaltOnBreakpoints
                || !similarAlsoSkipNonPhpIncludes
    }

    override fun apply() {
        val filePathModel = filePathList.model as? DefaultListModel<String>
        val namespaceModel = namespaceList.model as? DefaultListModel<String>
        val state = settings.settingsState;

        state.filepaths =
            filePathModel?.let { (0 until it.size()).map { i -> it.getElementAt(i) } }?.let { ArrayList(it) }!!
        state.namespaces =
            namespaceModel?.let { (0 until it.size()).map { i -> it.getElementAt(i) } }?.let { ArrayList(it) }!!
        state.skipIncludes = skipIncludes
        state.skipConstructors = skipConstructors
        state.haltOnBreakpoints = haltOnBreakpoints
        state.alsoSkipNonPhpIncludes = alsoSkipNonPhpIncludes
    }

    override fun reset() {
        filePathModel.clear()
        val state = settings.settingsState;
        filePathModel.addAll(state.filepaths)
        namespaceModel.clear()
        namespaceModel.addAll(state.namespaces)
        skipIncludes = state.skipIncludes
        skipConstructors = state.skipConstructors
        haltOnBreakpoints = state.haltOnBreakpoints
        alsoSkipNonPhpIncludes = state.alsoSkipNonPhpIncludes
    }

    override fun getDisplayName(): String = "Xdebug Skip"

    private fun createListRenderer(): ColoredListCellRenderer<String> {
        return object : ColoredListCellRenderer<String>() {
            override fun customizeCellRenderer(list: JList<out String>, value: String?, index: Int, selected: Boolean, hasFocus: Boolean) {
                append(value ?: "")
            }
        }
    }
}
