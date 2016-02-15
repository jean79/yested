package net.yested.bootstrap

import net.yested.HTMLComponent
import net.yested.ButtonType
import net.yested.Button
import net.yested.Anchor
import net.yested.with
import net.yested.Attribute
import net.yested.UL
import net.yested.Component
import org.w3c.dom.HTMLElement
import net.yested.div
import net.yested.Span
import org.w3c.dom.events.Event

 enum class ButtonLook(val code:String) {
    DEFAULT("default"),
    PRIMARY("primary"),
    SUCCESS("success"),
    INFO("info"),
    WARNING("warning"),
    DANGER("danger"),
    LINK("link")
}

 enum class ButtonSize(val code:String) {
    DEFAULT("default"),
    LARGE("lg"),
    SMALL("sm"),
    EXTRA_SMALL("xs")
}

 class BtsButton(type: ButtonType = ButtonType.BUTTON,
             label: HTMLComponent.()-> Unit,
             val look:ButtonLook = ButtonLook.DEFAULT,
             val size:ButtonSize = ButtonSize.DEFAULT,
             val block:Boolean = false,
             badge:String? = null,
             onclick:(Event) -> Unit = {} ) :  HTMLComponent("button") {

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
            span(clazz = "badge") { +badge }
        }
        this.onclick = onclick
    }

    fun setClass() {
        element.setAttribute("class", "btn btn-${look.code} btn-${size.code} ${if (block) "btn-block" else ""} ${if (buttonActive) "active" else ""}")
    }

}


 class BtsAnchor(href:String,
                look:ButtonLook = ButtonLook.DEFAULT,
                size:ButtonSize = ButtonSize.DEFAULT,
                block:Boolean = false) :  HTMLComponent("a") {

     var href:String? by Attribute()

    init {
        this.href = href
        element.setAttribute("class", "btn btn-${look.code} btn-${size.code} ${if (block) "btn-block" else ""}")
    }

}

 open class Dropdown(id:String,
                      label: HTMLComponent.()->Unit,
					  val splitted: Boolean = false,
                      val look:ButtonLook = ButtonLook.DEFAULT,
                      val size:ButtonSize = ButtonSize.DEFAULT,
					  val onClick: (Event)->dynamic = {}) : Component {

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


     fun link(href:String = "#", onclick: Function1<Event, Unit>? = null, init: Anchor.() -> Unit) {
        list.li {
            "role".."presentation"
            a(href = href, onclick = onclick) {
                "role".."menuitem"
                "tabindex".."-1"
                init()
            }
        }
    }

     fun divider() {
        list.li {
            "class".."divider"
        }
    }

     fun header(label: HTMLComponent.()->Unit) {
        list.li {
            "role".."presentation"
            "class".."dropdown-header"
            label()
        }
    }
}

 fun HTMLComponent.btsButton(type: ButtonType = ButtonType.BUTTON,
                                   label: HTMLComponent.()-> Unit,
                                   look:ButtonLook = ButtonLook.DEFAULT,
                                   size:ButtonSize = ButtonSize.DEFAULT,
                                   block:Boolean = false,
                                   badge:String? = null,
                                   onclick:(Event) -> Unit = {}):Unit {
    +BtsButton(type = type, label = label, look = look, size = size, block = block, badge = badge, onclick = onclick)
}

 fun HTMLComponent.btsAnchor(href:String,
                                  look:ButtonLook = ButtonLook.DEFAULT,
                                  size:ButtonSize = ButtonSize.DEFAULT,
                                  block:Boolean = false,
                                  init:BtsAnchor.() -> Unit):Unit {
    +(BtsAnchor(href = href, look = look, size = size, block = block) with  { init() })
}

 fun HTMLComponent.splitButtonDropdown(id: String,
								  label: HTMLComponent.()->Unit,
								  look: ButtonLook = ButtonLook.DEFAULT,
								  size: ButtonSize = ButtonSize.DEFAULT,
								  onClick: (Event)->dynamic,
								  init: Dropdown.()->Unit) {
	+(Dropdown(id = id, label = label, look = look, size = size, splitted = true, onClick = onClick) with { init() })
}

 fun HTMLComponent.dropdown(id: String,
                                  label: HTMLComponent.()->Unit,
                                  look: ButtonLook = ButtonLook.DEFAULT,
                                  size: ButtonSize = ButtonSize.DEFAULT,
                                  init: Dropdown.()->Unit) {
    +(Dropdown(id = id, label = label, look = look, size = size) with { init() })
}

 fun HTMLComponent.badge(init:HTMLComponent.()->Unit) {
    +(Span() with { "class".."badge"; init() })
}
