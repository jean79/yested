package net.yested.bootstrap

import net.yested.Component
import kotlin.js.dom.html.HTMLElement
import net.yested.createElement
import net.yested.with
import java.util.ArrayList
import net.yested.CheckBox
import net.yested.Li
import net.yested.removeAllContent

public class ListGroup<TYPE>(val renderer: Li.(item:TYPE)->Unit) : Component {

    override val element = createElement("ul") with {
        setAttribute("class", "list-group")
    }

    public fun setData(data: Collection<TYPE>) {
        element.removeAllContent()
        data.forEach {
            val li = Li() with {
                "class".."list-group-item"
                renderer(it)
            }
            element.appendChild(li.element)
        }
    }

}