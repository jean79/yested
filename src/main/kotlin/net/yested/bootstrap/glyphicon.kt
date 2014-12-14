package net.yested.bootstrap

import net.yested.Span
import net.yested.HTMLParentComponent

class Glyphicon(icon:String) : Span() {
    {
        clazz = "glyphicon glyphicon-${icon}"
    }
}

fun HTMLParentComponent.glyphicon(icon:String): Glyphicon {
    val glyphicon = Glyphicon(icon = icon)
    add(glyphicon)
    return glyphicon
}