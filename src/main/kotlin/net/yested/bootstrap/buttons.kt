package net.yested.bootstrap

import net.yested.ParentComponent
import net.yested.HTMLParentComponent
import net.yested.ButtonType
import net.yested.Button
import net.yested.Anchor

public enum class ButtonLook(val code:String) {
    DEFAULT: ButtonLook("default")
    PRIMARY: ButtonLook("primary")
    SUCCESS: ButtonLook("success")
    INFO: ButtonLook("info")
    WARNING: ButtonLook("warning")
    DANGER: ButtonLook("danger")
    LINK: ButtonLook("link")
}

public enum class ButtonSize(val code:String) {
    DEFAULT: ButtonSize("default")
    LARGE: ButtonSize("lg")
    SMALL: ButtonSize("sm")
    EXTRA_SMALL: ButtonSize("xs")
}

public class BtsButton(type: ButtonType = ButtonType.BUTTON,
             label:HTMLParentComponent.()-> Unit,
             val look:ButtonLook = ButtonLook.DEFAULT,
             val size:ButtonSize = ButtonSize.DEFAULT,
             val block:Boolean = false,
             onclick:() -> Unit ) :  HTMLParentComponent("button") {

    private var _active:Boolean = false

    var active:Boolean
        get() = _active
        set(value) {
            _active = value
            setClass()
        }

    var disabled:Boolean
        get() = element.getAttribute("disabled") == "disabled"
        set(value) {
            element.setAttribute("disabled", if (value) "disabled" else "")
        }

    {
        setClass()
        setAttribute("type", type.code)
        this.label()
        this.onclick = onclick
    }

    fun setClass() {
        setAttribute("class", "btn btn-${look.code} btn-${size.code} ${if (block) "btn-block" else ""} ${if (_active) "active" else ""}")
    }

}

public class BtsAnchor(href:String,
                look:ButtonLook = ButtonLook.DEFAULT,
                size:ButtonSize = ButtonSize.DEFAULT,
                block:Boolean = false) :  Anchor(href) {

    {
        setAttribute("class", "btn btn-${look.code} btn-${size.code} ${if (block) "btn-block" else ""}")
    }

}

public fun HTMLParentComponent.btsButton(type: ButtonType = ButtonType.BUTTON,
                                   label:HTMLParentComponent.()-> Unit,
                                   look:ButtonLook = ButtonLook.DEFAULT,
                                   size:ButtonSize = ButtonSize.DEFAULT,
                                   block:Boolean = false,
                                   onclick:() -> Unit):Unit {
    val btn = BtsButton(type = type, label = label, look = look, size = size, block = block, onclick = onclick)
    this.add(btn)
}

public fun HTMLParentComponent.btsAnchor(href:String,
                                  look:ButtonLook = ButtonLook.DEFAULT,
                                  size:ButtonSize = ButtonSize.DEFAULT,
                                  block:Boolean = false,
                                  init:Anchor.() -> Unit):Unit {
    val btn = BtsAnchor(href = href, look = look, size = size, block = block)
    btn.init()
    add(btn)
}

