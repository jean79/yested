package bootstrap

import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.Medium
import net.yested.bootstrap.PanelStyle
import net.yested.Div
import net.yested.bootstrap.DeviceSize
import net.yested.bootstrap.Panel
import net.yested.with
import net.yested.bootstrap.btsForm
import net.yested.bootstrap.FormStyle
import net.yested.bootstrap.Select
import net.yested.bootstrap.btsButton
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.alert
import net.yested.bootstrap.AlertStyle
import net.yested.TextArea
import net.yested.bootstrap.RowPanelContainer

fun createRowPanelContainerSection(id: String): Div {

    val panelContainer = RowPanelContainer()

    var counter = 1

    fun addPanel(size:DeviceSize, panelStyle: PanelStyle) {

        val textArea = TextArea(2) with  { data = "Resize me!" }
        val panel = Panel(style = panelStyle, dismissible = true) with {
            heading { +"A panel ${counter++} ($size)" }
            content { +textArea }
        }
        panelContainer.add(panel, size)

    }

    addPanel(Medium(4), PanelStyle.PRIMARY)
    addPanel(Medium(4), PanelStyle.DEFAULT)
    addPanel(Medium(6), PanelStyle.SUCCESS)
    addPanel(Medium(4), PanelStyle.INFO)

    val sizes = arrayListOf(Medium(4), Medium(6), Medium(8))
    val selectSize = Select<DeviceSize>(options = sizes) { "${it.size}" }

    val looks = arrayListOf(PanelStyle.DEFAULT, PanelStyle.PRIMARY, PanelStyle.SUCCESS, PanelStyle.INFO, PanelStyle.WARNING, PanelStyle.DANGER)
    //PanelStyle.values.toList()
    
    val selectLook = Select(options = looks) { it.name }

    return div(id = id) {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Row Panel Container" } }
            }
            col(Medium(6)) {
                +"""Panel Container is based on JQuery.sortable function (from JQuery UI).
                It allows user to change layout of panels or remove panels from the container.
                This implementation allows you to specify width of panels via Bootstrap columns sizes."""
            }
            col(Medium(6)) {
                alert(style = AlertStyle.WARNING, dismissible = true) {
                    +"Try Drag&amp;Drop the Panels below!"
                }
                a(href = "https://github.com/jean79/yested/blob/master/src/main/docsite/bootstrap/rowpanelcontainer.kt") {+"Source code"}
            }
        }
        row {
            btsForm(formStyle = FormStyle.INLINE) {
                item(label = { +"Size:"; nbsp() }) {
                    +selectSize
                }
                item(label = { nbsp(); +"Look:" ; nbsp() }) {
                    +selectLook
                }
                item(label = { nbsp() }) {
                    btsButton(label = {+"Add Panel"}, size = ButtonSize.SMALL, look = ButtonLook.PRIMARY) {
                        addPanel(selectSize.selectedItems.first(), selectLook.selectedItems.first())
                    }
                }
            }
        }
        br()
        row {
            col(Medium(12)) {
                +panelContainer
            }
        }
    }
}