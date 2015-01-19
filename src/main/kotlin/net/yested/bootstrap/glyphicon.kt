package net.yested.bootstrap

import net.yested.Span
import net.yested.HTMLParentComponent
import net.yested.with

public class Glyphicon(icon:String) : Span() {
    {
        clazz = "glyphicon glyphicon-${icon}"
    }
}

public fun HTMLParentComponent.glyphicon(icon:String): Unit {
    +Glyphicon(icon = icon)
}