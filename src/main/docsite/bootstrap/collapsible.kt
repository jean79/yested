package bootstrap

import net.yested.Div
import net.yested.Span
import net.yested.bootstrap.BtsCheckBox
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.collapsible

/**
 * Created by jean on 21.2.2015.
 */
/**
 * Created by jean on 26.1.2015.
 */
fun createCollapsibleSection(id:String): Div {

    return div(id = id) {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Collapsible" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"Simple collapsible container with link header."
                }
                br()
                h4 { +"Demo" }
                collapsible {
                    header { +"Click me" }
                    content {
                        ul {
                            li { +"some content"}
                            li { +"some content"}
                            li { +"some content"}
                            li { +"some content"}
                        }
                    }
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content=
"""collapsible {
    header { +"Click me" }
    content {
        ul {
            li { +"some content"}
            li { +"some content"}
            li { +"some content"}
            li { +"some content"}
        }
    }
}""")
            }
        }
    }

}