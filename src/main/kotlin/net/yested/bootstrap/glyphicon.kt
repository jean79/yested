package net.yested.bootstrap

import net.yested.Span
import net.yested.HTMLComponent
import net.yested.with
import net.yested.createElement
import net.yested.Component

public class Glyphicon(icon:String) : Component {

    override public val element = createElement("span");

    {
        element.className = "glyphicon glyphicon-${icon}"
    }

}

public fun HTMLComponent.glyphicon(icon:String): Unit {
    +Glyphicon(icon = icon)
}