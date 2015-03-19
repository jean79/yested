package net.yested.bootstrap

import net.yested.HTMLComponent
import net.yested.ButtonType
import net.yested.Button
import net.yested.Anchor
import net.yested.with
import net.yested.Attribute
import net.yested.Li
import net.yested.UL
import net.yested.Component
import kotlin.js.dom.html.HTMLElement
import net.yested.div
import net.yested.Div
import net.yested.Span

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
             onclick:() -> Unit = {} ) :  HTMLComponent("button") {

    private var buttonActive:Boolean = false

	fun setLabel(label: HTMLComponent.()-> Unit) {
		removeAllChildren()
		this.label()
	}

    var active:Boolean
        get() = buttonActive
        set(value) {
            buttonActive = value
            setClass()
        }

    var disabled:Boolean
        get() = element.getAttribute("disabled") == "disabled"
        set(value) {
            if (value) {
                element.setAttribute("disabled", "disabled")
            } else {
                element.removeAttribute("disabled")
            }
        }

    init {
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

    public var href:String by Attribute()

    init {
        this.href = href
        element.setAttribute("class", "btn btn-${look.code} btn-${size.code} ${if (block) "btn-block" else ""}")
    }

}

public open class Dropdown(id:String,
                      label: HTMLComponent.()->Unit,
					  val splitted: Boolean = false,
                      val look:ButtonLook = ButtonLook.DEFAULT,
                      val size:ButtonSize = ButtonSize.DEFAULT,
					  val onClick: ()->Unit = {}) : Component {

    private val list = UL() with {
        "class".."dropdown-menu"
        "role".."menu"
        "aria-labelledby"..id
    }

    override val element: HTMLElement = (div(clazz = "dropdown") {
		if (!splitted) {
            +(Button(type = ButtonType.BUTTON) with {
                "class".."btn btn-${look.code} btn-${size.code} dropdown-toggle"
                "data-toggle".."dropdown"
                "aria-expanded".."true"
                this.id = id
                label()
                nbsp()
                span(clazz = "caret")
            })
		} else {
			+(Button(type = ButtonType.BUTTON) with {
				"class".."btn btn-${look.code} btn-${size.code}"
				this.id = id
				label()
				onclick = onClick
			})
			+(Button(type = ButtonType.BUTTON) with {
				"class".."btn btn-${look.code} btn-${size.code} dropdown-toggle"
				"data-toggle".."dropdown"
				"aria-expanded".."true"
				span(clazz = "caret")
			})
		}
		+list
	}).element


    public fun link(href:String = "#", onclick: Function0<Unit>? = null, init: Anchor.() -> Unit) {
        list.li {
            "role".."presentation"
            a(href = href, onclick = onclick) {
                "role".."menuitem"
                "tabindex".."-1"
                init()
            }
        }
    }

    public fun divider() {
        list.li {
            "class".."divider"
        }
    }

    public fun header(label: HTMLComponent.()->Unit) {
        list.li {
            "role".."presentation"
            "class".."dropdown-header"
            label()
        }
    }
}

public fun HTMLComponent.btsButton(type: ButtonType = ButtonType.BUTTON,
                                   label: HTMLComponent.()-> Unit,
                                   look:ButtonLook = ButtonLook.DEFAULT,
                                   size:ButtonSize = ButtonSize.DEFAULT,
                                   block:Boolean = false,
                                   badge:String? = null,
                                   onclick:() -> Unit = {}):Unit {
    +BtsButton(type = type, label = label, look = look, size = size, block = block, badge = badge, onclick = onclick)
}

public fun HTMLComponent.btsAnchor(href:String,
                                  look:ButtonLook = ButtonLook.DEFAULT,
                                  size:ButtonSize = ButtonSize.DEFAULT,
                                  block:Boolean = false,
                                  init:BtsAnchor.() -> Unit):Unit {
    +(BtsAnchor(href = href, look = look, size = size, block = block) with  { init() })
}

public fun HTMLComponent.splitButtonDropdown(id: String,
								  label: HTMLComponent.()->Unit,
								  look: ButtonLook = ButtonLook.DEFAULT,
								  size: ButtonSize = ButtonSize.DEFAULT,
								  onClick: ()->Unit,
								  init: Dropdown.()->Unit) {
	+(Dropdown(id = id, label = label, look = look, size = size, splitted = true, onClick = onClick) with { init() })
}

public fun HTMLComponent.dropdown(id: String,
                                  label: HTMLComponent.()->Unit,
                                  look: ButtonLook = ButtonLook.DEFAULT,
                                  size: ButtonSize = ButtonSize.DEFAULT,
                                  init: Dropdown.()->Unit) {
    +(Dropdown(id = id, label = label, look = look, size = size) with { init() })
}

public fun HTMLComponent.badge(init:HTMLComponent.()->Unit) {
    +(Span() with { "class".."badge"; init() })
}
