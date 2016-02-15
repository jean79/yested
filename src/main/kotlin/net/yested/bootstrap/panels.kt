package net.yested.bootstrap

import net.yested.HTMLComponent
import net.yested.Div
import net.yested.with
import net.yested.Component
import net.yested.createElement
import net.yested.appendComponent
import net.yested.Button

 enum class PanelStyle(val code:String) {
    DEFAULT("default"),
    PRIMARY("primary"),
    SUCCESS("success"),
    INFO("info"),
    WARNING("warning"),
    DANGER("danger")
}

 class Panel(style : PanelStyle = PanelStyle.DEFAULT, val dismissible: Boolean = false) : Component {

    override  val element = createElement("div")

    private val heading = Div() with { clazz = "panel-heading" }
    private val body = Div() with { clazz = "panel-body" }
    private val footer = Div() with { clazz = "panel-footer" }

    /**
     * Default dismiss removes this element from parent element,
     * but sometimes this behaviour must be overridden by a specific parent component.
     */
     var dismissibleHandler: Function1<Panel, Unit>? = null

    init {
        element.setAttribute("class", "panel panel-${style.code}")
        element.appendComponent(heading)
        element.appendComponent(body)
    }

    private fun dismiss() {
        if (dismissibleHandler != null) {
            dismissibleHandler!!(this)
        } else {
            element.parentNode?.removeChild(element)
        }
    }

    private fun addDismissButton() {
        val closeButton = Button() with {
            "class".."close"; "data-dismiss".."alert"; "aria-label".."Close"
            onclick = { dismiss() }
            span {
                "aria-hidden".."true"
                +"&times;"
            }
        }
        heading with {
            +closeButton
        }
    }

     fun heading(init: HTMLComponent.() -> Unit) {
        if (dismissible) {
            addDismissButton()
        }
        heading.init()
    }

     fun content(init: HTMLComponent.() -> Unit) {
        body.init()
    }

     fun footer(init: HTMLComponent.() -> Unit) {
        footer.init()
        element.appendComponent(footer)
    }

}

 fun HTMLComponent.panel(style:PanelStyle = PanelStyle.DEFAULT, dismissible: Boolean = false, init:Panel.() -> Unit) {
    + (Panel(style = style, dismissible = dismissible) with  { init() })
}
