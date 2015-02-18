package net.yested.bootstrap.smartgrid

import net.yested.bootstrap.Dialog
import net.yested.bootstrap.DialogSize
import net.yested.CheckBox
import net.yested.with
import net.yested.bootstrap.ListGroup
import net.yested.bootstrap.InputField
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize
import net.yested.TextInput
import net.yested.bootstrap.StringInputField

/**
 * Opens configuration dialog for SmartGrid
 */
fun <T> openConfigurationDialog(
        columns: Collection<GridColumn<T>>,
        selectedColumns: Collection<String>,
        changeLayout:(newLayout:List<String>)->Unit) {

    val sortedColumns = columns.sortBy { it.label }
    val dialog = Dialog(size = DialogSize.SMALL)

    val checkboxes = columns
            .map { column ->
                CheckBox() with {
                    checked = selectedColumns.contains(column.id)
                    value = column.id
                    disabled = true
                }
            }
            .toMap { it.value }

    val listGroup = ListGroup<GridColumn<T>>() { column->
        val checkbox = checkboxes.get(column.id)!!
        +checkbox
        nbsp()
        +column.label
        "style".."cursor: pointer;"
        onclick = { checkbox.checked = !checkbox.checked }
    }

    val inputField = StringInputField()

    fun setColumnsData() {
        val text = inputField.data.toLowerCase()
        if (text.length() > 0) {
            listGroup.setData( sortedColumns.filter { it.label.toLowerCase().contains(text) })
        } else {
            listGroup.setData(sortedColumns)
        }
    }

    fun getSelectedColumnIds() =
            checkboxes
                    .filter { it.getValue().checked }
                    .map { it.getKey() }

    fun getAllColumnIds(): List<String> =
        checkboxes
            .map { it.getKey() }

    inputField.addOnChangeLiveListener { setColumnsData() }

    setColumnsData()

    dialog with {
        header { + "Grid configuration" }
        body {
            +inputField
            div {
                "style".."overflow: scroll;  height: 210px; overflow-x: hidden"
                +listGroup
            }
        }
        footer {
            btsButton(
                    type = ButtonType.SUBMIT,
                    look = ButtonLook.PRIMARY,
                    size = ButtonSize.SMALL,
                    label = { +"Submit"},
                    onclick = {
                        changeLayout( getSelectedColumnIds() )
                        dialog.close()
                    })
            btsButton(
                    type = ButtonType.SUBMIT,
                    look = ButtonLook.DEFAULT,
                    size = ButtonSize.SMALL,
                    label = { +"Show All"},
                    onclick = {
                        changeLayout( getAllColumnIds() )
                        dialog.close()
                    })
        }
    }

    dialog.open(fade = false)
}


