package bootstrap

import net.yested.div
import net.yested.Div
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.btsForm
import net.yested.bootstrap.inputAddOn
import net.yested.bootstrap.Medium
import net.yested.bootstrap.FormStyle
import net.yested.bootstrap.InputField
import net.yested.bootstrap.StringInputField

fun createForm(id: String): Div {

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Form" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""Default, Inline and Horizontal layout are supported. It is possible to define widths
of label and input for Horizontal layout."""
                }
                br()
                h4 { +"Demo" }
                btsForm(formStyle = FormStyle.DEFAULT) {
                    item(label = { +"Username" }) {
                        +StringInputField(placeholder = "Enter your username")
                    }
                    item(label = { +"Salary" }) {
                        inputAddOn(prefix = "$", suffix = ".00", textInput = StringInputField(placeholder = "Salary") )
                    }
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content="""btsForm(formStyle = FormStyle.DEFAULT) {
    item(label = { +"Username" }) {
        textInput(placeholder = "Enter your username") { }
    }
    item(label = { +"Salary" }) {
        inputAddOn(prefix = "$", suffix = ".00", textInput = TextInput(placeholder = "Salary") )
    }
}""")
            }
        }
    }

}
