package net.yested.bootstrap

import net.yested.Div
import net.yested.HTMLComponent
import net.yested.with
import net.yested.div
import kotlin.js.dom.html.HTMLElement
import net.yested.el
import net.yested.Component
import net.yested.createElement
import net.yested.appendComponent

public class Row(): Component {

    override val element = createElement("div");

    {
        element.setAttribute("class", "row")
    }

    public fun col(vararg modifiers: ColumnModifier, init: HTMLComponent.() -> Unit) {
        element.appendComponent(
            Div() with {
                clazz = modifiers map {it.toString()} join(" ")
                init()
            })
    }

}

public class Page(val element: HTMLElement) {

    public fun topMenu(navbar: Navbar) {
        element.appendComponent(navbar)
    }

    public fun content(init: HTMLComponent.() -> Unit): Unit {
        element.appendChild(
                div {
                    "class".."container theme-showcase"
                    "role".."main"
                    init()
                }.element)
    }

    public fun footer(init: HTMLComponent.() -> Unit): Unit {
        element.appendChild(
                div {
                    div(clazz = "container") {
                        tag("hr") {}
                        init()
                    }
                }.element
        )
    }

}

public class PageHeader  : HTMLComponent("div") {
    {
        clazz = "page-header"
    }
}

public fun HTMLComponent.pageHeader(init: HTMLComponent.() -> Unit) {
    +(PageHeader() with  { init() })
}

public fun HTMLComponent.row(init:Row.()->Unit) {
    +(Row() with  { init() })
}

public fun page(placeholderElementId:String, init:Page.() -> Unit):Unit {
    Page(el(placeholderElementId) as HTMLElement) with {
        this.init()
    }
}
