package net.yested.bootstrap

import net.yested.Component
import net.yested.HTMLComponent
import net.yested.createElement

 class Glyphicon(icon:String) : Component {

    override  val element = createElement("span")

    init {
        element.className = "glyphicon glyphicon-${icon}"
    }

}

 fun HTMLComponent.glyphicon(icon:String): Unit {
    +Glyphicon(icon = icon)
}