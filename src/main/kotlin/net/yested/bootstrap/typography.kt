package net.yested.bootstrap

import net.yested.HTMLParentComponent
import net.yested.Span
import net.yested.with
import net.yested.P

public enum class TextAlign(val code:String) {
    LEFT : TextAlign("left")
    CENTER : TextAlign("center")
    RIGHT : TextAlign("right")
    JUSTIFY : TextAlign("justify")
    NOWRAP : TextAlign("nowrap")
}

public fun HTMLParentComponent.aligned(align:TextAlign, init:P.() -> Unit) {
    add(P() with {
        clazz = "text-${align.code}"
        init()
    })
}

private fun addSpan(parent:HTMLParentComponent, clazz:String, init:Span.() -> Unit) {
    parent.add(Span() with {
        this.clazz = clazz
        init()
    })
}

public fun HTMLParentComponent.uppercase(init:Span.() -> Unit):Unit =
    addSpan(this, "text-uppercase", init)

public fun HTMLParentComponent.lowercase(init:Span.() -> Unit):Unit =
        addSpan(this, "text-lowercase", init)

public fun HTMLParentComponent.capitalize(init:Span.() -> Unit):Unit =
        addSpan(this, "text-capitalize", init)
