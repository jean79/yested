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
import java.util.HashMap
import java.util.ArrayList
import net.yested.div

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

    private val bar = UL() with { role = "tablist"; clazz = "nav nav-tabs"}

    private val content = Div() with { clazz = "tab-content"}

    private val anchorsLi = java.util.ArrayList<HTMLParentComponent>();

    private val tabsRendered = HashMap<Int, Div>()

    private var index = 0;

    {
        element.setAttribute("role", "tabpanel")

        add(bar)
        add(content)

    }

    private fun renderContent(tabId:Int, init:HTMLParentComponent.()->Unit):Div =
        if (tabsRendered.containsKey(tabId)) {
            tabsRendered.get(tabId)!!
        } else {
            val div = Div() with {
                "class".."fade in"
                init()
            }
            tabsRendered.put(tabId, div)
            div
        }

    private fun activateTab(li:Li, tabId:Int, onSelect:Function0<Unit>?, init:HTMLParentComponent.()->Unit) {

        li.clazz = "active"

        anchorsLi.filter { it != li} .forEach { it.clazz = "" }

        content.replace(renderContent(tabId, init));

        if (onSelect != null) {
            onSelect()
        }
    }

    public fun tab(active:Boolean = false, header:HTMLParentComponent.()->Unit, onSelect:Function0<Unit>? = null, init:HTMLParentComponent.()->Unit) {

        val tabId = index++;

        val a = Anchor() with {
            "role" .. "tab"
            "style" .. "cursor: pointer;"
            header()
        }

        val li = bar.li { +a; role = "presentation" }

        a.onclick = {
            activateTab(li, tabId, onSelect, init)
        }

        bar.add(li)
        anchorsLi.add(li)

        if (active) {
            activateTab(li, tabId, onSelect, init)
        }

    }

}

public fun HTMLParentComponent.tabs(init:Tabs.() -> Unit): Unit {
    val tabs = Tabs()
    tabs.init()
    add(tabs)
}
