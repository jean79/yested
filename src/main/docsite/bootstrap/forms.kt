package bootstrap

import net.yested.div
import net.yested.Div
import net.yested.bootstrap.textInput
import net.yested.bootstrap.tabs
import net.yested.text
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.form

fun createForm(): Div {

    return div {
        row {
            col(12) {
                pageHeader { h3 { +"Form" } }
            }
        }
        row {
            col(4) {
                div {
                    +"""
"""
                }
                br()
                h4 { +"Demo" }
                form(labelDef = "col-sm-4", inputDef = "col-sm-8") {
                    item(label = { +"Username" }) {
                        textInput(placeholder = "Enter your username") { }
                    }
                    item(label = { +"Age" }) {
                        textInput(placeholder = "Enter your age") { }
                    }
                }
            }
            col(8) {
                h4 { +"Code" }
                code(lang = "kotlin") {
                    +
                    """form(labelDef = "col-sm-4", inputDef = "col-sm-8") {
    item(label = { +"Username"}) {
        textInput(placeholder = "Enter your username") { }
    }
    item(label = { +"Age"}) {
        textInput(placeholder = "Enter your age") { }
    }
}"""
                }
            }
        }
    }

}
