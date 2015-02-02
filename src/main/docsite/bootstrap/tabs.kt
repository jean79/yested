package bootstrap

import net.yested.div
import net.yested.Div
import net.yested.bootstrap.inputField
import net.yested.bootstrap.tabs
import net.yested.text
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.Medium

/**
 * Created by jean on 20.12.2014.
 */
fun createTabs(id: String): Div {

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Tabs" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""
Tabs are based on Bootstrap Tabs.
Content of tab is rendedered upon click on a tab link. When clicking on anoother link, content is preserved.
"""
                }
                br()
                h4 { +"Demo" }
                tabs {
                    tab(header = text("First")) {
                        div {
                            inputField(placeholder = "Placeholder 1") { }
                        }
                    }
                    tab(active = true, header = text("Second")) {
                        div {
                            +"This tab is selected by default."
                        }
                    }
                    tab(header = text("Third")) {
                        a(href = "http://www.wikipedia.org") { +"Wikipedia" }
                    }
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content="""tabs {
    tab(header = text("First")) {
        div {
            textInput(placeholder = "Placeholder 1") { }
        }
    }
    tab(active = true, header = text("Second")) {
        div {
            +"This tab is selected by default."
        }
    }
    tab(header = text("Third")) {
        a(href = "http://www.wikipedia.org") { +"Wikipedia"}
    }
}""")
            }
        }
    }

}
