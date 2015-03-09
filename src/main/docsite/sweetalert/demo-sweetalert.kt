package sweetalert

import net.yested.Div
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.btsButton
import net.yested.ext.sweetalert.swal
import net.yested.ext.sweetalert.SwalOptions

fun sweetAlertDemo(): Div {

    return div {
        row {
            col() {
                pageHeader {
                    h3 { +"Sweet Alerts" }
                }
            }
        }
        row {
            col(Medium(6)) {
                a(href="http://tristanedwards.me/sweetalert") {
                    +"http://tristanedwards.me/sweetalert"
                }
                br()
                +"Javascript and CSS file from given project must be included in your HTML page."
                br()
                br()
                btsButton(label = { +"Simple Alert" }) {
                    swal(SwalOptions(
                            title = "Hello",
                            text = "Some text",
                            type = "success"))
                }
                nbsp()
                btsButton(label = { +"Confirmation" }) {
                    swal(SwalOptions(
                            title = "Confirm",
                            text = "Are you sure you want to erase your Hard Drive?",
                            type = "warning",
                            showCancelButton = true,
                            confirmButtonText = "Erase It!",
                            cancelButtonText = "Please No!",
                            confirmButtonColor = "#DD6B55",
                            closeOnConfirm = false,
                            closeOnCancel = false)) { success ->
                                swal(SwalOptions(
                                        title = "Result",
                                        text = "You answered: ${success}",
                                        type = "success",
                                        confirmButtonText = "Ok"))
                    }
                }
            }
            col(Medium(6)) {
                code(lang = "kotlin", content =
"""btsButton(label = { +"Simple Alert" }) {
    swal(SwalOptions(
            title = "Hello",
            text = "Some text",
            type = "success"))
}
nbsp()
btsButton(label = { +"Confirmation" }) {
    swal(SwalOptions(
            title = "Confirm",
            text = "Are you sure you want to erase your Hard Drive?",
            type = "warning",
            showCancelButton = true,
            confirmButtonText = "Erase It!",
            cancelButtonText = "Please No!",
            confirmButtonColor = "#DD6B55",
            closeOnConfirm = false,
            closeOnCancel = false)) { success ->
                swal(SwalOptions(
                        title = "Result",
                        text = "You answered: $\{success}",
                        type = "success",
                        confirmButtonText = "Ok"))
    }
}""")
            }
        }
    }
}