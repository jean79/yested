package net.yested.bootstrap

import net.yested.HTMLComponent
import net.yested.Div
import net.yested.with
import net.yested.Component
import net.yested.createElement
import net.yested.appendComponent

public enum class PanelStyle(val code:String) {
    DEFAULT : PanelStyle("default")
    PRIMARY : PanelStyle("primary")
    SUCCESS : PanelStyle("success")
    INFO : PanelStyle("info")
    WARNING : PanelStyle("warning")
    DANGER : PanelStyle("danger")
}

public class Panel(style : PanelStyle = PanelStyle.DEFAULT) : Component {

    override public val element = createElement("div")

    private val heading = Div() with { clazz = "panel-heading" }
    private val body = Div() with { clazz = "panel-body" }
    private val footer = Div() with { clazz = "panel-footer" }

    {
        element.setAttribute("class", "panel panel-${style.code}")
        element.appendComponent(heading)
        element.appendComponent(body)
    }

    public fun heading(init: HTMLComponent.() -> Unit) {
        heading.init()
    }

    public fun content(init: HTMLComponent.() -> Unit) {
        body.init()
    }

    public fun footer(init: HTMLComponent.() -> Unit) {
        footer.init()
        element.appendComponent(footer)
    }

}

public fun HTMLComponent.panel(style:PanelStyle = PanelStyle.DEFAULT, init:Panel.() -> Unit) {
    + (Panel(style = style) with  { init() })
}
