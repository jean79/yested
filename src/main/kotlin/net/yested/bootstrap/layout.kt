package net.yested.bootstrap

import net.yested.Div
import net.yested.ComponentContainer
import net.yested.with
import net.yested.div
import kotlin.js.dom.html.HTMLElement
import net.yested.el
import net.yested.HTMLComponent
import net.yested.HTMLComponentContainer

public class Row() : HTMLComponent("div") {

    {
        setAttribute("class", "row")
    }

    public fun col(vararg modifiers: ColumnModifier, init: ComponentContainer.() -> Unit) {
        appendChild(
            Div() with {
                clazz = modifiers map {it.toString()} join(" ")
                init()
            })
    }

}

public class Page(val element: HTMLElement) {

    public fun topMenu(navbar: Navbar) {
        element.appendChild(navbar.element)
    }

    public fun content(init: ComponentContainer.() -> Unit): Unit {
        element.appendChild(
                div {
                    "class".."container theme-showcase"
                    "role".."main"
                    init()
                }.element)
    }

    public fun footer(init: ComponentContainer.() -> Unit): Unit {
        element.appendChild(
                div {
                    div(clazz = "container") {
                        tag("hr") {}
                        init()
                    }
                }.element)
    }

}

public class PageHeader  : HTMLComponentContainer("div") {
    {
        clazz = "page-header"
    }
}

public fun ComponentContainer.pageHeader(init: ComponentContainer.() -> Unit) {
    val pageHeader = PageHeader()
    pageHeader.init()
    appendChild(pageHeader)
}

public fun ComponentContainer.row(init:Row.()->Unit): Row {
    val row = Row()
    row.init()
    appendChild(row)
    return row
}

public fun page(placeholderElementId:String, init:Page.() -> Unit):Unit {
    Page(el(placeholderElementId) as HTMLElement) with {
        this.init()
    }
}
