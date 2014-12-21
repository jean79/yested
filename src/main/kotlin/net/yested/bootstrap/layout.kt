package net.yested.bootstrap

import net.yested.Div
import net.yested.ParentComponent
import net.yested.HTMLParentComponent
import net.yested.with
import net.yested.div
import kotlin.js.dom.html.HTMLElement
import net.yested.el

/**
 * Created by jean on 24.11.2014.
 */
class Row(val device:String = "md") : ParentComponent("div") {

    {
        setAttribute("class", "row")
    }

    fun col(dimension:Int, init:HTMLParentComponent.() -> Unit) {
        add(
            Div() with {
                clazz = "col-${device}-${dimension}"
                init()
            })
    }

}

class Page(val element: HTMLElement) {

    fun topMenu(navbar: Navbar) {
        element.appendChild(navbar.element)
    }

    fun content(init: HTMLParentComponent.() -> Unit) =
            element.appendChild(
                div { "class".."container theme-showcase"; "role".."main"
                    init()
                }.element)


}

class PageHeader  : HTMLParentComponent("div") {
    {
        clazz = "page-header"
    }
}

fun HTMLParentComponent.pageHeader(init: HTMLParentComponent.() -> Unit) {
    val pageHeader = PageHeader()
    pageHeader.init()
    add(pageHeader)
}

fun HTMLParentComponent.row(device:String = "md", init:Row.()->Unit): Row {
    val row = Row(device = device)
    row.init()
    add(row)
    return row
}

fun page(placeholderElementId:String, init:Page.() -> Unit):Unit {
    Page(el(placeholderElementId) as HTMLElement) with {
        this.init()
    }
}
