package net.yested.bootstrap

import net.yested.ComponentContainer
import net.yested.Li
import net.yested.with
import net.yested.HTMLComponent

public class Breadcrumbs : HTMLComponent("ol") {

    {
        setAttribute("class", "breadcrumb")
    }

    fun link(href:String? = null, onclick:Function0<Unit>? = null, init: ComponentContainer.() -> Unit) {
        appendChild(Li() with {
            a(href = href, onclick = onclick, init = init)
        })
    }

    fun selected(init: ComponentContainer.() -> Unit) {
        appendChild(Li() with {
            clazz = "active"
            init()
        })
    }

}

public fun ComponentContainer.breadcrumbs(init: Breadcrumbs.() -> Unit): Breadcrumbs {
    val breadcrumbs = Breadcrumbs()
    breadcrumbs.init()
    this.appendChild(breadcrumbs)
    return breadcrumbs
}