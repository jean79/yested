package net.yested.bootstrap

import net.yested.ParentComponent
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by jean on 24.12.2014.
 * <div class="btn-group" role="group" aria-label="...">
<button type="button" class="btn btn-default">Left</button>
<button type="button" class="btn btn-default">Middle</button>
<button type="button" class="btn btn-default">Right</button>
</div>
 */
public class ButtonGroup(val size: ButtonSize = ButtonSize.DEFAULT, val onSelect:Function1<String, Unit>? = null) : ParentComponent("div") {

    private val buttons = HashMap<String, BtsButton>();

    {
        setAttribute("class", "btn-group")
        setAttribute("role", "group")
    }

    public var value:String? = null

    public fun select(selectValue:String) {
        value = selectValue
        if (onSelect != null) {
            onSelect!!(selectValue)
        }
        for ((key, button) in buttons) {
            if (key == selectValue) {
                button.active = true
            } else {
                button.active = false
            }
        }
    }

    public fun button(value:String, look: ButtonLook = ButtonLook.DEFAULT, label : ParentComponent.() -> Unit) {
        val button = BtsButton(label = label, look = look, size = size) {
            select(value)
        }
        add(button)
        buttons.put(value, button)
    }

}