import net.yested.bootstrap.page
import net.yested.bootstrap.CheckBox
import net.yested.bootstrap.btsButton
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize
import net.yested.text
import net.yested.bootstrap.row

fun main(args: Array<String>) {

    val checkBox = CheckBox()
    checkBox.value = true
    checkBox.addOnChangeListener { println("nova hodnota: ${checkBox.value}") }

    page("page") {
        content {
            row {
                col(12) {
                    +checkBox
                    +btsButton(size = ButtonSize.EXTRA_SMALL, look = ButtonLook.WARNING, label = text("Test")) {}
                }
            }
            row {
                +createGrid()
            }
        }
    }

}