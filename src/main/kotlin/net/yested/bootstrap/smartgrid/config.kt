package net.yested.bootstrap.smartgrid

import net.yested.*
import net.yested.bootstrap.*
import net.yested.layout.containers.horizontalContainer
import net.yested.layout.containers.verticalContainer

class ConfigurationDialog<T>(
        private val columns: Collection<GridColumn<T>>,
        private val selectedColumnIds: Collection<String>,
        private val changeLayoutHandler:(columnIds:List<String>)->Unit) {

    private val listGroupAvailableColumns = ListGroup<GridColumn<T>>(selectionMode = SelectionMode.MULTI) { +it.label }
    private val listGroupSelectedColumns = ListGroup<GridColumn<T>>(selectionMode = SelectionMode.MULTI, sortable = true) { +it.label }

    private val buttonToSelect = BtsButton(size = ButtonSize.SMALL, label = { glyphicon("arrow-right")}, onclick = { moveToSelected() } )
    private val buttonToDeselect = BtsButton(size = ButtonSize.SMALL, label = { glyphicon("arrow-left")}, onclick = { moveToAvailable() })

    private val fieldFilterAvailableColumns = StringInputField()

    private val availableColumns = arrayListOf<GridColumn<T>>()

    private val dialog = Dialog(size = DialogSize.DEFAULT) with {
        header  { + "Grid configuration" }
        body {
            horizontalContainer(width = 100.pct(), gap = 5) {
                column(width = 50.pct()) {
                    +fieldFilterAvailableColumns
                    div {
                        "style".."overflow: scroll;  height: 210px; overflow-x: hidden"
                        +listGroupAvailableColumns
                    }
                }
                column(width = 35.px()) {
                    verticalContainer(height = 100.pct()) {
                        row {
                            +buttonToSelect
                        }
                        row {
                            +buttonToDeselect
                        }
                    }
                }
                column(width = 50.pct(), height = 100.pct()) {
                    div {
                        "style".."overflow: scroll;  height: 100%; overflow-x: hidden"
                        +listGroupSelectedColumns
                    }
                }
            }
        }
        footer {
            btsButton(
                    type = ButtonType.SUBMIT, look = ButtonLook.DEFAULT, size = ButtonSize.SMALL,
                    label = { +"Show All"},onclick = { showAll() })
            btsButton(
                    type = ButtonType.SUBMIT, look = ButtonLook.DEFAULT, size = ButtonSize.SMALL,
                    label = { +"Hide All"}, onclick = { hideAll() })
            btsButton(
                    type = ButtonType.SUBMIT, look = ButtonLook.PRIMARY, size = ButtonSize.SMALL,
                    label = { +"Submit"}, onclick = { applySelected() })
        }
    }

    init {
        initialDistribution()
        dialog.open(fade = false)
        buttonToSelect.disabled = true
        buttonToDeselect.disabled = true

        listGroupAvailableColumns.addOnChangeListener {
            buttonToSelect.disabled = listGroupAvailableColumns.data.size == 0
        }

        listGroupSelectedColumns.addOnChangeListener {
            buttonToDeselect.disabled = listGroupSelectedColumns.data.size == 0
        }

        fieldFilterAvailableColumns.addOnChangeLiveListener {
            populateAvailableListGroup()
        }

    }

    private fun getSelectedColumnIds() = listGroupSelectedColumns.dataProvider.map { it.id }

    private fun getAllColumnIds(): List<String> = columns.map { it.id }

    private fun applySelected() {
        dialog.close()
        changeLayoutHandler( getSelectedColumnIds() )
    }

    private fun showAll() {
        listGroupSelectedColumns.dataProvider = columns
        availableColumns.clear()
        populateAvailableListGroup()
    }

    private fun hideAll() {
        availableColumns.clear();
        availableColumns.addAll(columns)
        listGroupSelectedColumns.dataProvider = arrayListOf()
        populateAvailableListGroup()
    }

    private fun moveToSelected() {
        listGroupAvailableColumns.data.forEach {
            listGroupAvailableColumns.removeItem(it)
            listGroupSelectedColumns.addItem(it)
        }
    }

    private fun moveToAvailable() {
        listGroupSelectedColumns.data.forEach {
            listGroupSelectedColumns.removeItem(it)
            availableColumns.add(it)
            populateAvailableListGroup()
        }
    }

    private fun initialDistribution() {
        availableColumns.addAll( columns.filter { !selectedColumnIds.contains(it.id) }.sortedBy { it.label } )
        val columnsById = columns.associateBy { it.id }
        val selectedColumns = selectedColumnIds.map { columnsById.get(it)!! }
        listGroupSelectedColumns.dataProvider = selectedColumns
        populateAvailableListGroup()
    }

    private fun populateAvailableListGroup() {
        listGroupAvailableColumns.dataProvider = getVisibleAvailableColumns().sortedBy { it.label }
    }

    private fun getVisibleAvailableColumns(): Collection<GridColumn<T>> {
        val filterText = fieldFilterAvailableColumns.data.toLowerCase()
        if (filterText.length > 0) {
            return availableColumns.filter { it.label.toLowerCase().contains(filterText) }
        } else {
            return availableColumns
        }
    }

}

/**
 * Opens configuration dialog for SmartGrid
 */
fun <T> openConfigurationDialog(
        columns: Collection<GridColumn<T>>,
        selectedColumnIds: Collection<String>,
        changeLayoutHandler:(columnIds:List<String>)->Unit) {

    ConfigurationDialog(columns = columns, selectedColumnIds = selectedColumnIds, changeLayoutHandler = changeLayoutHandler)

}


