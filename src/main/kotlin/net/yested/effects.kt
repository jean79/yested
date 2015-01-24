package net.yested

import jquery.JQuery
import kotlin.js.dom.html.HTMLElement
import jquery.jq

/**
 * Created by jean on 21/01/15.
 */
native fun JQuery.fadeOut(duration:Int, callback:()->Unit) :Unit = noImpl;
native fun JQuery.fadeIn(duration:Int, callback:()->Unit) :Unit = noImpl;
native fun JQuery.slideUp(duration:Int, callback:()->Unit) :Unit = noImpl
native fun JQuery.slideDown(duration:Int, callback:()->Unit) :Unit = noImpl

private val DURATION = 200
private val SLIDE_DURATION = DURATION * 2

public trait Effect {
    fun apply(component:Component, callback:Function0<Unit>? = null)
}

public trait BiDirectionEffect {
    fun applyIn(component:Component, callback:Function0<Unit>? = null)
    fun applyOut(component:Component, callback:Function0<Unit>? = null)
}

private fun call(function:Function0<Unit>?) {
    function?.let { function!!() }
}

public class SlideUp() : Effect {
    override fun apply(component: Component, callback:Function0<Unit>?) {
        jq(component.element).slideUp(SLIDE_DURATION) { call(callback) }
    }
}

public class SlideDown : Effect {
    override fun apply(component: Component, callback: (() -> Unit)?) {
        jq(component.element).slideDown(SLIDE_DURATION) { call(callback) }
    }
}

public class FadeOut : Effect {
    override fun apply(component: Component, callback: (() -> Unit)?) {
        jq(component.element).fadeOut(DURATION) { call(callback) }
    }
}

public class FadeIn : Effect {
    override fun apply(component: Component, callback: (() -> Unit)?) {
        jq(component.element).fadeIn(DURATION) { call(callback) }
    }
}

public class Fade : BiDirectionEffect {
    override fun applyIn(component: Component, callback: (() -> Unit)?) {
        FadeIn().apply(component, callback)
    }

    override fun applyOut(component: Component, callback: (() -> Unit)?) {
        FadeOut().apply(component, callback)
    }
}

public class Slide : BiDirectionEffect {
    override fun applyIn(component: Component, callback: (() -> Unit)?) {
        SlideDown().apply(component, callback)
    }

    override fun applyOut(component: Component, callback: (() -> Unit)?) {
        SlideUp().apply(component, callback)
    }
}