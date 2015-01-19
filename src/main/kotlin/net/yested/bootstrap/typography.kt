package net.yested.bootstrap

import net.yested.HTMLComponent
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

public fun HTMLComponent.aligned(align:TextAlign, init:P.() -> Unit) {
    +(P() with {
        clazz = "text-${align.code}"
        init()
    })
}

private fun addSpan(parent: HTMLComponent, clazz:String, init:Span.() -> Unit) {
    parent.appendChild(Span() with {
        this.clazz = clazz
        init()
    })
}

public fun HTMLComponent.uppercase(init:Span.() -> Unit):Unit =
        addSpan(this, "text-uppercase", init)

public fun HTMLComponent.lowercase(init:Span.() -> Unit):Unit =
        addSpan(this, "text-lowercase", init)

public fun HTMLComponent.capitalize(init:Span.() -> Unit):Unit =
        addSpan(this, "text-capitalize", init)
