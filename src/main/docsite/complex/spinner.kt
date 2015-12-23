package complex

import net.yested.div
import net.yested.Div
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.Medium
import net.yested.spin.spinner
import net.yested.spin.SpinnerOptions

fun createSpinner(): Div {

    return div {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Spinner" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""
This spinner is based on Spinner library:
"""
                    a(href = "http://fgnass.github.io/spin.js/") { +"http://fgnass.github.io/spin.js/"}
                    br()
                    +"You need to include spin.js library in your html file."
                    br()
                    +"All spinner options are supported."
                }
                br()
                h4 { +"Demo" }
                div {
                    style = "height: 200px"
                    spinner(SpinnerOptions(length = 7))
                }

            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content=
"""div {
    style = "height: 200px"
    spinner(SpinnerOptions(length = 7))
}""")
            }
        }
    }

}
