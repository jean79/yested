package bootstrap

import net.yested.div
import net.yested.Div
import net.yested.bootstrap.tabs
import net.yested.text
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.Medium
import net.yested.bootstrap.btsButton
import net.yested.bootstrap.Tabs
import net.yested.with
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.BtsCheckBox
import net.yested.bootstrap.btsForm
import net.yested.bootstrap.FormStyle
import net.yested.bootstrap.StringInputField

/**
 * Created by jean on 20.12.2014.
 */
fun createTabs(id: String): Div {

    var tabIndex = 3

    val dismissibleCheckbox = BtsCheckBox(label = {+"Dismissible" }) with { data = true }

    val tabs = Tabs(canChangeOrder = true) with {
        tab(dismissible = true, header = text("First")) {
            div {
                +StringInputField(placeholder = "Placeholder 1")
            }
        }
        tab(dismissible = true, header = text("Second")) {
            div {
                +"Second tab."
            }
        }
        tab(dismissible = true, header = text("Third")) {
            a(href = "http://www.wikipedia.org") { +"Wikipedia" }
        }
    }

    fun addTab(tabIndex:Int) {
        val tabId = tabs.tab(dismissible = dismissibleCheckbox.data, header = { +"Tab${tabIndex}" }) {
            div {
                +"Content of tab: ${tabIndex}"
            }
        }
        tabs.activateTab(tabId)
    }

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Tabs" } }
            }
        }
        row {
            col(Medium(6)) {
                div {
                    +"Tabs are based on Bootstrap Tabs."
                    br()
                    emph { +"Features" }
                    ul {
                        li { +"Lazy render of tab content" }
                        li { +"Rendered content is preserved" }
                        li { +"Optionaly dismissible tabs" }
                        li { +"Programmatic activation of tabs" }
                        li { +"Programmatic tab removal" }
                        li { +"Tab order can be changed via Drag&amp;Drop" }
                    }
                }
                br()
                h4 { +"Demo" }
                btsForm(formStyle = FormStyle.INLINE) {
                    item(label = { } ) {
                        +dismissibleCheckbox
                    }
                    item(label = {nbsp(2)}) {
                        btsButton(label = { +"Add Tab"}, onclick = { addTab(++tabIndex) }, size = ButtonSize.SMALL)
                    }
                }
                br()
                +tabs
            }
            col(Medium(6)) {
                h4 { +"Code" }
                code(lang = "kotlin", content=
"""var tabIndex = 3

    val dismissibleCheckbox = BtsCheckBox(label = {+"Dismissible" }) with { value = true }

    val tabs = Tabs(canChangeOrder = true) with {
        tab(dismissible = true, header = text("First")) {
            div {
                inputField(placeholder = "Placeholder 1") { }
            }
        }
        tab(dismissible = true, header = text("Second")) {
            div {
                +"Second tab."
            }
        }
        tab(dismissible = true, header = text("Third")) {
            a(href = "http://www.wikipedia.org") { +"Wikipedia" }
        }
    }

    fun addTab(tabIndex:Int) {
        val tabId = tabs.tab(dismissible = dismissibleCheckbox.value, header = { +"Tab$\{tabIndex}" }) {
            div {
                +"Content of tab: $\{tabIndex}"
            }
        }
        tabs.activateTab(tabId)
    }
...
div {
    btsForm(formStyle = FormStyle.INLINE) {
        item(label = { } ) {
            +dismissibleCheckbox
        }
        item(label = {nbsp(2)}) {
            btsButton(label = { +"Add Tab"}, onclick = { addTab(++tabIndex) }, size = ButtonSize.SMALL)
        }
    }
    br()
    +tabs
}""")
            }
        }
    }

}
