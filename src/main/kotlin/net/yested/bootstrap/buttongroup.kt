package net.yested.bootstrap

import java.util.ArrayList
import java.util.HashMap
import net.yested.HTMLComponent
import net.yested.Component
import kotlin.js.dom.html.HTMLElement
import net.yested.createElement
import net.yested.appendComponent
import net.yested.with

/**
 * Created by jean on 24.12.2014.
 * <div class="btn-group" role="group" aria-label="...">
<button type="button" class="btn btn-default">Left</button>
<button type="button" class="btn btn-default">Middle</button>
<button type="button" class="btn btn-default">Right</button>
</div>
 */
public class ButtonGroup(val size: ButtonSize = ButtonSize.DEFAULT, val onSelect:Function1<String, Unit>? = null) : Component {

    override val element = createElement("div")

    private val buttons = HashMap<String, BtsButton>()

    init {
        element.setAttribute("class", "btn-group")
        element.setAttribute("role", "group")
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

    public fun button(value:String, look: ButtonLook = ButtonLook.DEFAULT, label : HTMLComponent.() -> Unit) {
        val button = BtsButton(label = label, look = look, size = size) {
            select(value)
        }
        element.appendComponent(button)
        buttons.put(value, button)
    }

}

public fun HTMLComponent.buttonGroup(size: ButtonSize = ButtonSize.DEFAULT, onSelect:Function1<String, Unit>? = null, init:ButtonGroup.() -> Unit) {
    +(ButtonGroup(size = size, onSelect = onSelect) with init)
}