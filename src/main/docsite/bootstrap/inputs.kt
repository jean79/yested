package bootstrap

import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.Validator
import net.yested.bootstrap.btsForm
import net.yested.bootstrap.BtsButton
import net.yested.bootstrap.Medium
import net.yested.with
import net.yested.bootstrap.InputField
import net.yested.bootstrap.StringInputField
import org.w3c.dom.events.Event

fun createInputs(id: String): Div {

    val textInput = StringInputField(placeholder = "Mandatory field")

    val validator = Validator(textInput, errorText = "At least 3 chars!!") { value -> value.size > 2 }

    fun submit() {
        if (validator.isValid()) {
            println("submit")
        }
    }

    val button = BtsButton(label = { +"Send" }, onclick = { submit() })

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Text Input with Validation" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""
This example demonstrates simple text input with custom validation.
Please note that validator is also attached to form item.
"""
                }
                br()
                h4 { +"Demo" }
                btsForm {
                    item(label = { +"Name" }, validator = validator) {
                        +textInput
                    }
                    item(label = {}) {
                        +button
                    }
                    item(label = { +"Disabled input" }) {
                        +(StringInputField() with { data = "Some value"; disabled = true })
                    }
                    item(label = { +"Readonly input" }) {
                        +(StringInputField() with { data = "Some value"; readOnly = true })
                    }
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content="""val textInput = TextInput(placeholder = "Mandatory field")

val validator = Validator(textInput, errorText = "At least 3 chars!!") { value -> value.size > 2 }

fun submit() {
    if (validator.isValid()) {
        println("submit")
    }
}

val button = BtsButton(label = { +"Send"}, onclick = ::submit)
...
btsForm {
    item(label = { +"Name"}, validator = validator) {
        +textInput
    }
    item(label = {}) {
        +button
    }
    item(label = { +"Disabled input" }) {
        +(TextInput() with { value = "Some value"; disabled = true })
    }
}
""")
            }
        }
    }

}