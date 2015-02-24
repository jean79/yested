/**
 * Created by jean on 21.11.2014.
 */
package net.yested.bootstrap

import net.yested.UL
import net.yested.Div
import net.yested.HTMLComponent
import net.yested.Li
import net.yested.Anchor
import net.yested.with
import java.util.HashMap
import net.yested.Component
import net.yested.createElement
import net.yested.appendComponent
import net.yested.Fade

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
public class Tabs : Component {

    public override val element = createElement("div")

    private val bar = UL() with { role = "tablist"; clazz = "nav nav-tabs"}

    private val content = Div() with { clazz = "tab-content"}

    private val anchorsLi = java.util.ArrayList<HTMLComponent>();

    private val tabsRendered = HashMap<Int, Div>()

    private var index = 0;

    {
        element.setAttribute("role", "tabpanel")

        element.appendComponent(bar)
        element.appendComponent(content)

    }

    private fun renderContent(tabId:Int, init: HTMLComponent.()->Unit):Div =
        if (tabsRendered.containsKey(tabId)) {
            tabsRendered.get(tabId)!!
        } else {
            val div = Div() with {
                init()
            }
            tabsRendered.put(tabId, div)
            div
        }

    private fun activateTab(li:Li, tabId:Int, onSelect:Function0<Unit>?, init: HTMLComponent.()->Unit) {

        li.clazz = "active"

        anchorsLi.filter { it != li} .forEach { it.clazz = "" }

        content.setChild(renderContent(tabId, init), Fade());

        if (onSelect != null) {
            onSelect()
        }
    }

    public fun tab(active:Boolean = false, header: HTMLComponent.()->Unit, onSelect:Function0<Unit>? = null, init: HTMLComponent.()->Unit) {

        val tabId = index++;

        val a = Anchor() with {
            "role" .. "tab"
            "style" .. "cursor: pointer;"
            header()
        }

        val li = Li() with { +a; role = "presentation" }
        bar.appendChild(li)

        a.onclick = {
            activateTab(li, tabId, onSelect, init)
        }

        //bar.add(li)
        anchorsLi.add(li)

        if (active) {
            activateTab(li, tabId, onSelect, init)
        }

    }

}

public fun HTMLComponent.tabs(init:Tabs.() -> Unit): Unit {
    +(Tabs() with  { init() })
}
