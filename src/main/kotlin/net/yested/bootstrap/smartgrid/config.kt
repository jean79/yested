package net.yested.bootstrap.smartgrid

import net.yested.ButtonType
import net.yested.CheckBox
import net.yested.bootstrap.*
import net.yested.with

/**
 * Opens configuration dialog for SmartGrid
 */
fun <T> openConfigurationDialog(
        columns: Collection<GridColumn<T>>,
        selectedColumns: Collection<String>,
        changeLayout:(columnIds:List<String>)->Unit) {

    val sortedColumns = columns.sortBy { it.label }
    val dialog = Dialog(size = DialogSize.SMALL)

    val listGroup = ListGroup<GridColumn<T>>() { +it.label }

    listGroup.dataProvider = sortedColumns
    listGroup.data = columns.filter { selectedColumns.contains(it.id) }

    fun getSelectedColumnIds() = listGroup.data.map { it.id }

    fun getAllColumnIds(): List<String> = columns.map { it.id }

    dialog with {
        header { + "Grid configuration" }
        body {
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


