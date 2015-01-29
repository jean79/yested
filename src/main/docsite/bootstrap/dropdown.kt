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
import net.yested.bootstrap.dropdown
import net.yested.bootstrap.badge

fun createDropDownsSection(id: String): Div {

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Dropdowns" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""
Refer to Bootstrap Dropdowns.
"""
                }
                br()
                h4 { +"Demo" }
                div {
                    dropdown(
                            id = "mandatory",
                            label = { +"Mailbox" },
                            look = ButtonLook.PRIMARY,
                            size = ButtonSize.DEFAULT) {

                        link(href = "", onclick = { }) { +"Unread "; badge { +"12"} }
                        link(href = "", onclick = { }) { +"Sent"}
                        divider()
                        link(href = "", onclick = { }) { +"Spam"}
                        header { +"Custom" }
                        link(href = "", onclick = { }) { +"Work"}
                        link(href = "", onclick = { }) { +"Private"}
                    }
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content=
"""dropDown(
        id = "mandatory",
        label = { +"Mailbox" },
        look = ButtonLook.PRIMARY,
        size = ButtonSize.DEFAULT) {

    link(href = "", onclick = { }) { +"Unread "; badge { +"12"} }
    link(href = "", onclick = { }) { +"Sent"}
    divider()
    link(href = "", onclick = { }) { +"Spam"}
    header { +"Custom" }
    link(href = "", onclick = { }) { +"Work"}
    link(href = "", onclick = { }) { +"Private"}
}""")
            }
        }
    }

}