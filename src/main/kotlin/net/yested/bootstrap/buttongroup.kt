package net.yested.bootstrap

import java.util.ArrayList
import java.util.HashMap
import net.yested.ComponentContainer
import net.yested.HTMLComponent

/**
 * Created by jean on 24.12.2014.
 * <div class="btn-group" role="group" aria-label="...">
<button type="button" class="btn btn-default">Left</button>
<button type="button" class="btn btn-default">Middle</button>
<button type="button" class="btn btn-default">Right</button>
</div>
 */
public class ButtonGroup(val size: ButtonSize = ButtonSize.DEFAULT, val onSelect:Function1<String, Unit>? = null) : HTMLComponent("div") {

    private val buttons = HashMap<String, BtsButton>();

    {
        setAttribute("class", "btn-group")
        setAttribute("role", "group")
    }

    public var value:String? = null

    public fun select(selectValue:String) {
        value = selectValue
        onSelect?.invoke(selectValue)
        for ((key, button) in buttons) {
            if (key == selectValue) {
                button.active = true
            } else {
                button.active = false
            }
        }
    }

    public fun button(value:String, look: ButtonLook = ButtonLook.DEFAULT, label : ComponentContainer.() -> Unit) {
        val button = BtsButton(label = label, look = look, size = size) {
            select(value)
        }
        appendChild(button)
        buttons.put(value, button)
    }

}