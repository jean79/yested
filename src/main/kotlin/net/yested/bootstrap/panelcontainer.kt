package net.yested.bootstrap

import net.yested.Component
import kotlin.js.dom.html.HTMLElement
import net.yested.createElement
import net.yested.with
import jquery.jq
import jquery.JQuery
import org.w3c.dom.Element
import net.yested.HTMLComponent
import net.yested.Div

public native fun JQuery.sortable(options: dynamic):Unit = noImpl;
public native fun JQuery.each(func:(index:Int, element:HTMLElement)->Unit):Unit = noImpl;
public native fun JQuery.disableSelection():Unit = noImpl;

/**
 * Created by jean on 1.2.2015.
 */
public class PanelContainer(layoutChangeHandler:Function0<Unit>? = null) : Component {

    override val element:HTMLElement = createElement("ul");

    private val panels = arrayListOf<Panel>();

    {

        element.setAttribute("class", "list-unstyled")
        jq(element).disableSelection()
        jq(element).sortable(
                object {
                    val handle = ".panel-heading"
                    val update = {
                        if (layoutChangeHandler != null) {
                            layoutChangeHandler()
                        }
                    }
                })
        jq(element).disableSelection()

    }

    public fun getPanels(): Array<Panel> = panels.copyToArray()

    public fun panel(size: DeviceSize, style:PanelStyle = PanelStyle.DEFAULT, dismissible: Boolean = false, init:Panel.() -> Unit) {
        add(panel = Panel(style = style, dismissible = dismissible) with { init() }, size = size)
    }

    public fun add(panel:Panel, size: DeviceSize) {

        panel.dismissibleHandler = {
            element.removeChild(it.element.parentNode)
            panels.remove(it)
        }

        element.appendChild(
                (Div() with {
                    "class"..size.toString()
                    +panel
                }).element)

        panels.add(panel)

    }

}

public fun HTMLComponent.panelContainer(init:PanelContainer.()->Unit) {
    +(PanelContainer() with { init() })
}