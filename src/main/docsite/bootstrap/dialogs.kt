package bootstrap

import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.ButtonType
import net.yested.bootstrap.Dialog
import net.yested.bootstrap.TextInput
import net.yested.bootstrap.Validator
import net.yested.bootstrap.BtsButton
import net.yested.bootstrap.ButtonLook
import net.yested.with
import net.yested.bootstrap.Form
import net.yested.bootstrap.form
import net.yested.bootstrap.btsButton
import net.yested.bootstrap.textInput

fun createDialogs(): Div {

    val dialog = Dialog()

    dialog with {
        header { + "This is dialog with text input" }
        body {
            form {
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

    return div {
        row {
            col(12) {
                pageHeader { h3 { +"Dialogs" } }
            }
        }
        row {
            col(4) {
                div {
                    +"""This is a wrapper around Bootstrap dialogs."""
                }
                h4 { +"Demo" }
                div {
                    btsButton(label = { +"Open dialog" }, onclick = { dialog.open() })
                }
            }
            col(8) {
                h4 { +"Code" }
                code(lang = "kotlin") {
                    +
                    """val dialog = Dialog()

dialog with {
    header { + "This is dialog with text input" }
    body {
        form {
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
}"""
                }
            }
        }
    }

}