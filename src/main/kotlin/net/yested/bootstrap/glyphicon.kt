package net.yested.bootstrap

import net.yested.Component
import net.yested.HTMLComponent
import net.yested.createElement

public class Glyphicon(icon:String) : Component {

    override public val element = createElement("span")

    init {
        element.className = "glyphicon glyphicon-${icon}"
    }

}

public fun HTMLComponent.glyphicon(icon:String): Unit {
    +Glyphicon(icon = icon)
}