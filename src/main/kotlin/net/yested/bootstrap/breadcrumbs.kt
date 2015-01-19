package net.yested.bootstrap

import net.yested.HTMLParentComponent
import net.yested.ParentComponent
import net.yested.Li
import net.yested.with
import net.yested.createElement
import net.yested.Component
import net.yested.add
import net.yested.appendChild

/**
 * Created by jean on 30.11.2014.
 */
public class Breadcrumbs : Component {

    override val element = createElement("ol");

    {
        element.setAttribute("class", "breadcrumb")
    }

    fun link(href:String? = null, onclick:Function0<Unit>? = null, init:HTMLParentComponent.() -> Unit) {
        element.add(Li(), {
            a(href = href, onclick = onclick, init = init)
        })
    }

    fun selected(init: HTMLParentComponent.() -> Unit) {
        element.add(Li(), {
            clazz = "active"
            init()
        })
    }

}

public fun HTMLParentComponent.breadcrumbs(init: Breadcrumbs.() -> Unit): Breadcrumbs {
    val breadcrumbs = Breadcrumbs() with init
    +breadcrumbs
    return breadcrumbs
}