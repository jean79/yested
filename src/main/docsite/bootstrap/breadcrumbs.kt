package bootstrap

import net.yested.div
import net.yested.bootstrap.breadcrumbs
import net.yested.Div
import net.yested.bootstrap.row
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader

fun createBreadcrumbsSection(id: String): Div {

    return div(id = id) {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Breadcrumbs" } }
            }
        }
        row {
            col(Medium(6)) {
                div {
                    +"""Refer to Bootstrap Breadcrumbs."""
                    br()
                    a(href="http://getbootstrap.com/components/#breadcrumbs") { +"http://getbootstrap.com/components/#breadcrumbs"}
                }
                br()
                h4 { +"Demo" }
                div {
                    breadcrumbs {
                        link(href = "#bootstrapComponents_Breadcrumbs", onclick = {}) { +"Top" }
                        link(href = "#bootstrapComponents_Breadcrumbs", onclick = {}) { +"Level 2" }
                        link(href = "#bootstrapComponents_Breadcrumbs", onclick = {}) { +"Level 3" }
                        selected { +"Current" }
                    }
                }
            }
            col(Medium(6)) {
                h4 { +"Code" }
                code(lang = "kotlin", content = """breadcrumbs {
    link(href = "#", onclick = {}) { +"Top" }
    link(href = "#", onclick = {}) { +"Level 2" }
    link(href = "#", onclick = {}) { +"Level 3" }
    selected { +"Current" }
}""")
            }
        }
    }

}