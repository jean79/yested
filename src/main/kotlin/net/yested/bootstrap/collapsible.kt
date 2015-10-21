package net.yested.bootstrap

import net.yested.Component
import net.yested.HTMLComponent
import net.yested.Anchor
import net.yested.createElement
import kotlin.dom.clear
import net.yested.Span
import net.yested.with
import net.yested.Div
import net.yested.FadeOut
import net.yested.FadeIn
import net.yested.BiDirectionEffect
import net.yested.Fade
import net.yested.Slide

public class Collapsible(opened:Boolean = false, private val effect:BiDirectionEffect = Slide()) : Component {

    private val arrowPlaceholder = Span();

    private val contentPlaceholder = Div()

    private val linkPlaceholder = Span() with {
        style = "padding-left: 5px"
    }

    override public val element = (Div() with {
        a(onclick = { toggle() }) {
            "style".."cursor: pointer;"
            +arrowPlaceholder
            +linkPlaceholder
        }
        +contentPlaceholder
    }).element

    private var opened:Boolean

    public val isOpen:Boolean
        get() = opened

    init {
        this.opened = opened
        replaceArrow()
        if (!opened) {
            contentPlaceholder.style = "display: none;"
        }
    }

    public fun open() {
        opened = true
        update()
    }

    public fun close() {
        opened = false
        update()
    }

    public fun toggle() {
        opened = !opened
        update()
    }

    private fun update() {
        replaceArrow()
        if (opened) {
            effect.applyIn(contentPlaceholder)
        } else {
            effect.applyOut(contentPlaceholder)
        }
    }

    private fun replaceArrow() {
        arrowPlaceholder.removeAllChildren()
        arrowPlaceholder with {
            glyphicon(if (opened) "chevron-down" else "chevron-right")
        }
    }

    public fun header(init:HTMLComponent.()->Unit) {
        linkPlaceholder.removeAllChildren()
        linkPlaceholder with {
            init()
        }
    }

    public fun content(init:HTMLComponent.()->Unit) {
        contentPlaceholder.removeAllChildren()
        contentPlaceholder with {
            init()
        }
    }

}

public fun HTMLComponent.collapsible(opened:Boolean = false, effect:BiDirectionEffect = Slide(), init:Collapsible.() -> Unit) {
    + (Collapsible(opened = opened, effect = effect) with  { init() })
}