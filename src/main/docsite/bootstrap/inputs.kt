package bootstrap

import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.TextInput
import net.yested.bootstrap.Validator
import net.yested.bootstrap.form
import net.yested.bootstrap.BtsButton

fun createInputs(): Div {

    val textInput = TextInput(placeholder = "Mandatory field")

    val validator = Validator(textInput, errorText = "At least 3 chars!!") { value -> value.size > 2 }

    fun submit() {
        if (validator.isValid()) {
            println("submit")
        }
    }

    val button = BtsButton(label = { +"Send" }, onclick = ::submit)

    return div {
        row {
            col(12) {
                pageHeader { h3 { +"Text Input with Validation" } }
            }
        }
        row {
            col(4) {
                div {
                    +"""
This example demonstrates simple text input with custom validation.
Please note that validator is also attached to form item.
"""
                }
                br()
                h4 { +"Demo" }

                form(labelDef = "col-sm-3", inputDef = "col-sm-9") {
                    item(label = { +"Name" }, validator = validator) {
                        +textInput
                    }
                    item(label = {}) {
                        +button
                    }
                }
            }
            col(8) {
                h4 { +"Code" }
                code(lang = "kotlin", content="""val textInput = TextInput(placeholder = "Mandatory field")

val validator = Validator(textInput, errorText = "At least 3 chars!!") { value -> value.size > 2 }

fun submit() {
    if (validator.isValid()) {
        println("submit")
    }
}

val button = BtsButton(label = { +"Send"}, onclick = ::submit)

form(labelDef = "col-sm-3", inputDef = "col-sm-9") {
    item(label = { +"Name"}, validator = validator) {
        +textInput
    }
    item(label = {}) {
        +button
    }
}
""")
            }
        }
    }

}