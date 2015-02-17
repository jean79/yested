package bootstrap

import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.ButtonType
import net.yested.bootstrap.Dialog
import net.yested.bootstrap.ButtonLook
import net.yested.with
import net.yested.bootstrap.btsForm
import net.yested.bootstrap.btsButton
import net.yested.bootstrap.Medium
import net.yested.bootstrap.DialogSize
import net.yested.bootstrap.StringInputField

fun createDialogs(id: String): Div {

    val dialog = Dialog(size = DialogSize.SMALL)

    dialog with {
        header { + "This is dialog with text input" }
        body {
            btsForm {
                item(forId = "nameId", label = { + "Name" }) {
                    +(StringInputField(placeholder = "Name") with {
                        this.id = "nameId"
                    })
                }
            }
        }
        footer {
            btsButton(
                    type = ButtonType.SUBMIT,
                    look = ButtonLook.PRIMARY,
                    label = { +"Submit"},
                    onclick = { dialog.close() })

        }
    }

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Dialogs" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""This is a wrapper around Bootstrap dialogs."""
                }
                h4 { +"Demo" }
                div {
                    btsButton(label = { +"Open dialog" }, onclick = { dialog.open() })
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content="""val dialog = Dialog(size = DialogSize.SMALL)

dialog with {
    header { + "This is dialog with text input" }
    body {
        btsForm {
            item(forId = "nameId", label = { + "Name" }) {
                textInput(placeholder = "Name") { id = "nameId"}
            }
        }
    }
    footer {
        btsButton(
                type = ButtonType.SUBMIT,
                look = ButtonLook.PRIMARY,
                label = { +"Submit"},
                onclick = { dialog.close() })

    }
}

//somewhere in a dom tree:
div {
    btsButton(label = { +"Open dialog" }, onclick = { dialog.open() })
}""")
            }
        }
    }

}