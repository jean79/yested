package net.yested.bootstrap

import net.yested.Span
import net.yested.ComponentContainer

public class Glyphicon(icon:String) : Span() {
    {
        clazz = "glyphicon glyphicon-${icon}"
    }
}

public fun ComponentContainer.glyphicon(icon:String): Glyphicon {
    val glyphicon = Glyphicon(icon = icon)
    appendChild(glyphicon)
    return glyphicon
}