/**
 * Created by jean on 21.11.2014.
 */
package net.yested.bootstrap

import net.yested.ParentComponent
import net.yested.UL
import net.yested.Div
import net.yested.HTMLParentComponent
import net.yested.Li
import net.yested.Anchor
import net.yested.with

/**
 * <div role="tabpanel">
 *
 *  <ul class="nav nav-tabs" role="tablist" id="myTab">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Home</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Profile</a></li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane fade in active" id="home">ggg</div>
        <div role="tabpanel" class="tab-pane fade" id="profile">bla</div>
    </div>
</div>
 */
class Tabs : ParentComponent("div") {

    val bar = UL() with { role = "tablist"; clazz = "nav nav-tabs"}

    val content = Div() with { clazz = "tab-content"}

    val anchorsLi = java.util.ArrayList<HTMLParentComponent>();

    var onselect: Function1<String, Unit>? = null

    {
        element.setAttribute("role", "tabpanel")

        add(bar)
        add(content)

    }

    private fun activateTab(li:Li, code:String, init:HTMLParentComponent.()->Unit) {

        li.clazz = "active"

        anchorsLi.filter { it != li} .forEach { it.clazz = "" }

        content.removeChild("div")

        val div = Div() with {
            "class".."fade in"
            init()
        }

        content.add(div)

        if (onselect != null) {
            onselect!!(code)
        }
    }

    fun tab(active:Boolean = false, code:String, header:HTMLParentComponent.()->Unit, init:HTMLParentComponent.()->Unit) {

        val a = Anchor() with {
            "role" .. "tab"
            "style" .. "cursor: pointer;"
            header()
        }

        val li = bar.li { +a; role = "presentation" }

        a.onclick = {
            activateTab(li, code, init)
        }

        bar.add(li)
        anchorsLi.add(li)

        if (active) {
            activateTab(li, code, init)
        }
    }

}

fun tabs(init:Tabs.() -> Unit): Tabs = Tabs() with { init() }
