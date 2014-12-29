package net.yested.bootstrap

import net.yested.HTMLParentComponent
import net.yested.ParentComponent
import net.yested.Li
import net.yested.with

/**
 * Created by jean on 30.11.2014.
 */
public class Breadcrumbs : ParentComponent("ol") {

    {
        setAttribute("class", "breadcrumb")
    }

    fun link(href:String? = null, onclick:Function0<Unit>? = null, init:HTMLParentComponent.() -> Unit) {
        add(Li() with {
            a(href = href, onclick = onclick, init = init)
        })
    }

    fun selected(init: HTMLParentComponent.() -> Unit) {
        add(Li() with {
            clazz = "active"
            init()
        })
    }

}

public fun HTMLParentComponent.breadcrumbs(init: Breadcrumbs.() -> Unit): Breadcrumbs {
    val breadcrumbs = Breadcrumbs()
    breadcrumbs.init()
    this.add(breadcrumbs)
    return breadcrumbs
}