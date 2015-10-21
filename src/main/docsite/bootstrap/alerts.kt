package bootstrap

import net.yested.Div
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.alert
import net.yested.bootstrap.AlertStyle

fun createAlertsSection(id: String): Div {

    return div(id = id) {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Alerts" } }
            }
        }
        row {
            col(Medium(6)) {
                div {
                    +"""Refer to Bootstrap Alerts."""
                    br()
                    a(href="http://getbootstrap.com/components/#alerts") { +"http://getbootstrap.com/components/#alerts"}
                }
                br()
                h4 { +"Demo" }
                div {
                    alert(style = AlertStyle.WARNING, dismissible = true) {
                        emph { +"Warning!" }
                        + " Better check yourself, you're not looking too good."
                    }
                    alert(style = AlertStyle.DANGER, dismissible = true) {
                        emph { +"Oh snap!" }
                        + " Change a few things up and try submitting again."
                    }
                }
            }
            col(Medium(6)) {
                h4 { +"Code" }
                code(lang = "kotlin", content =
"""div {
    alert(style = AlertStyle.WARNING, dismissible = true) {
        emph { +"Warning!" }
        + " Better check yourself, you're not looking too good."
    }
    br()
    alert(style = AlertStyle.DANGER, dismissible = true) {
        emph { +"Oh snap!" }
        + " Change a few things up and try submitting again."
    }
}""")
            }
        }
    }

}