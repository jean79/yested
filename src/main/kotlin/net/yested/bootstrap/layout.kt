package net.yested.bootstrap

import net.yested.Div
import net.yested.ParentComponent
import net.yested.HTMLParentComponent
import net.yested.with
import net.yested.div
import kotlin.js.dom.html.HTMLElement
import net.yested.el

public class Row() : ParentComponent("div") {

    {
        setAttribute("class", "row")
    }

    public fun col(vararg sizes: DeviceSize, init:HTMLParentComponent.() -> Unit) {
        add(
            Div() with {
                clazz = sizes map {"col-${it.device.code}-${it.size}"} join(" ")
                init()
            })
    }

}

public class Page(val element: HTMLElement) {

    public fun topMenu(navbar: Navbar) {
        element.appendChild(navbar.element)
    }

    public fun content(init: HTMLParentComponent.() -> Unit): Unit {
        element.appendChild(
                div {
                    "class".."container theme-showcase"
                    "role".."main"
                    init()
                }.element)
    }

    public fun footer(init:HTMLParentComponent.() -> Unit): Unit {
        element.appendChild(
                div {
                    div(clazz = "container") {
                        tag("hr") {}
                        init()
                    }
                }.element)
    }

}

public class PageHeader  : HTMLParentComponent("div") {
    {
        clazz = "page-header"
    }
}

public fun HTMLParentComponent.pageHeader(init: HTMLParentComponent.() -> Unit) {
    val pageHeader = PageHeader()
    pageHeader.init()
    add(pageHeader)
}

public fun HTMLParentComponent.row(init:Row.()->Unit): Row {
    val row = Row()
    row.init()
    add(row)
    return row
}

public fun page(placeholderElementId:String, init:Page.() -> Unit):Unit {
    Page(el(placeholderElementId) as HTMLElement) with {
        this.init()
    }
}
