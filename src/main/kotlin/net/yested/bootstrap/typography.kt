package net.yested.bootstrap

import net.yested.ComponentContainer
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

public fun ComponentContainer.aligned(align:TextAlign, init:P.() -> Unit) {
    appendChild(P() with {
        clazz = "text-${align.code}"
        init()
    })
}

private fun addSpan(parent: ComponentContainer, clazz:String, init:Span.() -> Unit) {
    parent.appendChild(Span() with {
        this.clazz = clazz
        init()
    })
}

public fun ComponentContainer.uppercase(init:Span.() -> Unit):Unit =
    addSpan(this, "text-uppercase", init)

public fun ComponentContainer.lowercase(init:Span.() -> Unit):Unit =
        addSpan(this, "text-lowercase", init)

public fun ComponentContainer.capitalize(init:Span.() -> Unit):Unit =
        addSpan(this, "text-capitalize", init)
