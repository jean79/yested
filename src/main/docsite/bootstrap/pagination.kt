package bootstrap

import net.yested.Div
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.panel
import net.yested.bootstrap.PanelStyle
import net.yested.bootstrap.Pagination
import net.yested.Span
import net.yested.bootstrap.pagination


fun createPaginationSection(id: String): Div {

    val result = Span()

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Pagination" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""
Pagination from Bootstrap.
"""
                }
                br()
                h4 { +"Demo" }
                pagination(count = 6, defaultSelection = 2) { result.setContent("Selected: ${it}")}
                +result

            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content=
"""val result = Span()
...
div {
    pagination(count = 6, defaultSelection = 2) { result.replace("Selected: $\{it}")}
    +result
}
""")
            }
        }
    }

}