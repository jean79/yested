import net.yested.bootstrap.btsButton
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.ButtonLook
import net.yested.text
import net.yested.div
import net.yested.bootstrap.CheckBox
import net.yested.Div

/**
 * Created by jean on 20.12.2014.
 */
fun basicPage(): Div {

    val checkBox = CheckBox()
    checkBox.value = true
    checkBox.addOnChangeListener { println("nova hodnota: ${checkBox.value}") }

    return div {
        +checkBox
        +btsButton(size = ButtonSize.EXTRA_SMALL, look = ButtonLook.WARNING, label = text("Test")) {}
    }

}