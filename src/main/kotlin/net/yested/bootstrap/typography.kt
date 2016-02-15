package net.yested.bootstrap

import net.yested.HTMLComponent
import net.yested.Span
import net.yested.with
import net.yested.P

 enum class TextAlign(val code:String) {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right"),
    JUSTIFY("justify"),
    NOWRAP("nowrap")
}

 fun HTMLComponent.aligned(align:TextAlign, init:P.() -> Unit) {
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

 fun HTMLComponent.uppercase(init:Span.() -> Unit):Unit =
        addSpan(this, "text-uppercase", init)

 fun HTMLComponent.lowercase(init:Span.() -> Unit):Unit =
        addSpan(this, "text-lowercase", init)

 fun HTMLComponent.capitalize(init:Span.() -> Unit):Unit =
        addSpan(this, "text-capitalize", init)
