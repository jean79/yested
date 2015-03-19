package net.yested.bootstrap

import net.yested.HTMLComponent
import net.yested.Li
import net.yested.with
import net.yested.createElement
import net.yested.Component
import net.yested.appendComponent

/**
 * Created by jean on 30.11.2014.
 */
public class Breadcrumbs : Component {

    override val element = createElement("ol")

    init {
        element.setAttribute("class", "breadcrumb")
    }

    fun link(href:String? = null, onclick:Function0<Unit>? = null, init: HTMLComponent.() -> Unit) {
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

public fun HTMLComponent.breadcrumbs(init: Breadcrumbs.() -> Unit): Breadcrumbs {
    val breadcrumbs = Breadcrumbs() with  { init() }
    +breadcrumbs
    return breadcrumbs
}