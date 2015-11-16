package bootstrap

import net.yested.Component
import net.yested.bootstrap.DateField
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.row
import net.yested.div
import org.w3c.dom.HTMLElement


class DateFieldSection(id: String): Component {

    private val dateField = DateField { year.fourDigits + "." + month.twoDigits + "." + dayOfMonth.twoDigits + " " + hour24.twoDigits + ":" + minutes.twoDigits }

    override val element: HTMLElement = (div(id = id) {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"DateField" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"DateField"
                    +"uses: http://tarruda.github.io/bootstrap-datetimepicker/"
                }
                br()
                h4 { +"Demo" }
                +dateField
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content =
                """+DateField{year.fourDigits + "." + month.twoDigits + "." + dayOfMonth.twoDigits + " " + hour24.twoDigits + ":" + minutes.twoDigits}""")
            }
        }
    }).element

}
