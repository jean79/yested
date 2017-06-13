package net.yested.bootstrap

import net.yested.*

/**
 * Created by jean on 24.12.2014.
 * <div class="btn-group" role="group" aria-label="...">
<button type="button" class="btn btn-default">Left</button>
<button type="button" class="btn btn-default">Middle</button>
<button type="button" class="btn btn-default">Right</button>
</div>
 */
 class ButtonGroup(val size: ButtonSize = ButtonSize.DEFAULT, val onSelect:Function1<String, Unit>? = null) : Component {

    override val element = createElement("div")

    private val buttons = HashMap<String, BtsButton>()

    init {
        element.setAttribute("class", "btn-group")
        element.setAttribute("role", "group")
    }

     var value:String? = null

     fun select(selectValue:String) {
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

     fun button(value:String, look: ButtonLook = ButtonLook.DEFAULT, label : HTMLComponent.() -> Unit) {
        val button = BtsButton(label = label, look = look, size = size) {
            select(value)
        }
        element.appendComponent(button)
        buttons.put(value, button)
    }

}

 fun HTMLComponent.buttonGroup(size: ButtonSize = ButtonSize.DEFAULT, onSelect:Function1<String, Unit>? = null, init:ButtonGroup.() -> Unit) {
    +(ButtonGroup(size = size, onSelect = onSelect) with init)
}