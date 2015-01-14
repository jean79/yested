package bootstrap

import net.yested.div
import net.yested.Div
import net.yested.bootstrap.textInput
import net.yested.bootstrap.tabs
import net.yested.text
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.btsForm
import net.yested.bootstrap.inputAddOn
import net.yested.bootstrap.TextInput
import net.yested.bootstrap.Medium

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
                    +"""
"""
                }
                br()
                h4 { +"Demo" }
                btsForm(labelDef = "col-sm-4", inputDef = "col-sm-8") {
                    item(label = { +"Username" }) {
                        textInput(placeholder = "Enter your username") { }
                    }
                    item(label = { +"Salary" }) {
                        inputAddOn(prefix = "$", suffix = ".00", textInput = TextInput(placeholder = "Your expectation") )
                    }
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content="""btsForm(labelDef = "col-sm-4", inputDef = "col-sm-8") {
    item(label = { +"Username"}) {
        textInput(placeholder = "Enter your username") { }
    }
    item(label = { +"Salary" }) {
        inputAddOn(prefix = "$", suffix = ".00", textInput = TextInput(placeholder = "Your expectation") )
    }
}""")
            }
        }
    }

}
