package bootstrap

import net.yested.bootstrap.Validator
import net.yested.bootstrap.BtsButton
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.btsForm
import net.yested.Div
import net.yested.bootstrap.BtsCheckBox
import net.yested.Span

/**
 * Created by jean on 26.1.2015.
 */
fun createCheckboxSection(id:String): Div {

    val placeholder = Span()

    val checkbox = BtsCheckBox() { +"Select me" }

    fun checkboxClicked() {
        placeholder.setContent("Is checkbox checked: ${if (checkbox.checked) "Yes" else "No"}")
    }

    checkbox.addOnChangeListener { checkboxClicked() }

    checkboxClicked()

    return div(id = id) {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Checkbox" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"Simple Checkbox that provides selection changes listeners and expose writable value:Boolean property."
                }
                br()
                h4 { +"Demo" }
                +checkbox
                +placeholder
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content="""val placeholder = Span()

val checkbox = BtsCheckBox() { +"Select me" }

fun checkboxClicked() {
    placeholder.setContent("Is checkbox checked: ${if (checkbox.checked) "Yes" else "No"}")
}

checkbox.addOnChangeListener { checkboxClicked() }

checkboxClicked()
...
+checkbox
+placeholder""")
            }
        }
    }

}