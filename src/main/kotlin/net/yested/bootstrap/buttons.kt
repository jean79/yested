package net.yested.bootstrap

import net.yested.HTMLComponent
import net.yested.ButtonType
import net.yested.Button
import net.yested.Anchor
import net.yested.with
import net.yested.Attribute

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
             label: HTMLComponent.()-> Unit,
             val look:ButtonLook = ButtonLook.DEFAULT,
             val size:ButtonSize = ButtonSize.DEFAULT,
             val block:Boolean = false,
             badge:String? = null,
             onclick:() -> Unit ) :  HTMLComponent("button") {

    private var buttonActive:Boolean = false

    var active:Boolean
        get() = buttonActive
        set(value) {
            buttonActive = value
            setClass()
        }

    var disabled:Boolean
        get() = element.getAttribute("disabled") == "disabled"
        set(value) {
            element.setAttribute("disabled", if (value) "disabled" else "")
        }

    {
        setClass()
        element.setAttribute("type", type.code)
        this.label()
        badge?.let {
            nbsp()
            span(clazz = "badge") { +badge!! }
        }
        this.onclick = onclick
    }

    fun setClass() {
        element.setAttribute("class", "btn btn-${look.code} btn-${size.code} ${if (block) "btn-block" else ""} ${if (buttonActive) "active" else ""}")
    }

}

public class BtsAnchor(href:String,
                look:ButtonLook = ButtonLook.DEFAULT,
                size:ButtonSize = ButtonSize.DEFAULT,
                block:Boolean = false) :  HTMLComponent("a") {

    public var href:String by Attribute();

    {
        this.href = href
        element.setAttribute("class", "btn btn-${look.code} btn-${size.code} ${if (block) "btn-block" else ""}")
    }

}

public fun HTMLComponent.btsButton(type: ButtonType = ButtonType.BUTTON,
                                   label: HTMLComponent.()-> Unit,
                                   look:ButtonLook = ButtonLook.DEFAULT,
                                   size:ButtonSize = ButtonSize.DEFAULT,
                                   block:Boolean = false,
                                   badge:String? = null,
                                   onclick:() -> Unit):Unit {
    +BtsButton(type = type, label = label, look = look, size = size, block = block, badge = badge, onclick = onclick)
}

public fun HTMLComponent.btsAnchor(href:String,
                                  look:ButtonLook = ButtonLook.DEFAULT,
                                  size:ButtonSize = ButtonSize.DEFAULT,
                                  block:Boolean = false,
                                  init:BtsAnchor.() -> Unit):Unit {
    +(BtsAnchor(href = href, look = look, size = size, block = block) with  { init() })
}

