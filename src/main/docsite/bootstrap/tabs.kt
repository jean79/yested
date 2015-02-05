package bootstrap

import net.yested.div
import net.yested.Div
import net.yested.bootstrap.inputField
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

/**
 * Created by jean on 20.12.2014.
 */
fun createTabs(id: String): Div {

    var tabIndex = 3

    val dismissibleCheckbox = BtsCheckBox(label = {+"Dismissible" })

    val tabs = Tabs() with {
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
        val tabId = tabs.tab(dismissible = dismissibleCheckbox.value, header = { +"Tab${tabIndex}" }) {
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
                    +"""
Tabs are based on Bootstrap Tabs.
Content of tab is rendedered upon click on a tab link. When clicking on anoother link, content is preserved.
Tabs can optionally be dismissible. Tabs can be also removed and added programatically.
"""
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

    val dismissibleCheckbox = BtsCheckBox(label = {+"Dismissible" })

    val tabs = Tabs() with {
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
