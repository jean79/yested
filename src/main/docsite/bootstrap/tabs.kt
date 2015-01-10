package bootstrap

import net.yested.div
import net.yested.bootstrap.form
import net.yested.Div
import net.yested.bootstrap.textInput
import net.yested.bootstrap.tabs
import net.yested.text
import net.yested.bootstrap.row
import org.w3c.dom.Element
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.Medium

/**
 * Created by jean on 20.12.2014.
 */
fun createTabs(): Div {

    return div {
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
                            textInput(placeholder = "Placeholder 1") { }
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
