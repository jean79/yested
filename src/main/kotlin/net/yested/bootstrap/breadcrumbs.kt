package net.yested.bootstrap

import net.yested.HTMLComponent
import net.yested.Li
import net.yested.with
import net.yested.createElement
import net.yested.Component
import net.yested.appendComponent
import org.w3c.dom.events.Event

 class Breadcrumbs : Component {

    override val element = createElement("ol")

    init {
        element.setAttribute("class", "breadcrumb")
    }

    fun link(href:String? = null, onclick:Function1<Event, Unit>? = null, init: HTMLComponent.() -> Unit) {
        element.appendComponent(Li() with {
            a(href = href, onclick = onclick, init = init)
        })
    }

    fun selected(init: HTMLComponent.() -> Unit) {
        element.appendComponent(Li() with {
            clazz = "active"
            init()
        })
    }

}

 fun HTMLComponent.breadcrumbs(init: Breadcrumbs.() -> Unit): Breadcrumbs {
    val breadcrumbs = Breadcrumbs() with  { init() }
    +breadcrumbs
    return breadcrumbs
}