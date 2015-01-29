package bootstrap

import net.yested.bootstrap.Grid
import net.yested.bootstrap.Column
import net.yested.text
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.Medium

fun createButtons(id: String): Div {

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Buttons" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""
Refer to Bootstrap buttons.
"""
                }
                br()
                h4 { +"Demo" }
                div {
                    btsButton(
                            type = ButtonType.BUTTON,
                            label = { +"Primary" },
                            look = ButtonLook.PRIMARY,
                            size = ButtonSize.LARGE,
                            onclick = { println("First Button pressed.") })
                    nbsp()
                    btsButton(
                            type = ButtonType.BUTTON,
                            label = { +"Success" },
                            look = ButtonLook.SUCCESS,
                            size = ButtonSize.LARGE,
                            badge = "42",
                            onclick = { println("Second Button pressed.") })
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content="""div {
    btsButton(
            type = ButtonType.BUTTON,
            label = { +"Primary" },
            look = ButtonLook.PRIMARY,
            size = ButtonSize.LARGE,
            onclick = { println("First Button pressed.") })
    nbsp()
    btsButton(
            type = ButtonType.BUTTON,
            label = { +"Success" },
            look = ButtonLook.SUCCESS,
            size = ButtonSize.LARGE,
            badge = "42",
            onclick = { println("Second Button pressed.") })
}""")
            }
        }
    }

}