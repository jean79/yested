package net.yested.bootstrap

import net.yested.ComponentContainer
import net.yested.Div
import net.yested.with
import net.yested.bootstrap.ButtonSize
import net.yested.HTMLComponent

public enum class PanelStyle(val code:String) {
    DEFAULT : PanelStyle("default")
    PRIMARY : PanelStyle("primary")
    SUCCESS : PanelStyle("success")
    INFO : PanelStyle("info")
    WARNING : PanelStyle("warning")
    DANGER : PanelStyle("danger")
}

public class Panel(style : PanelStyle = PanelStyle.DEFAULT) : HTMLComponent("div") {

    private val heading = Div() with { clazz = "panel-heading" }
    private val body = Div() with { clazz = "panel-body" }
    private val footer = Div() with { clazz = "panel-footer" }

    {
        setAttribute("class", "panel panel-${style.code}")
        appendChild(heading)
        appendChild(body)
    }

    public fun heading(init: ComponentContainer.() -> Unit) {
        heading.init()
    }

    public fun content(init: ComponentContainer.() -> Unit) {
        body.init()
    }

    public fun footer(init: ComponentContainer.() -> Unit) {
        footer.init()
        appendChild(footer)
    }

}

public fun ComponentContainer.panel(style:PanelStyle = PanelStyle.DEFAULT, init:Panel.() -> Unit) {
    val panel = Panel(style = style)
    panel.init()
    appendChild(panel)
}
