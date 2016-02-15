package net.yested

import jquery.JQuery
import org.w3c.dom.HTMLElement
import jquery.jq

@native fun JQuery.fadeOut(duration:Int, callback:()->Unit) :Unit = noImpl;
@native fun JQuery.fadeIn(duration:Int, callback:()->Unit) :Unit = noImpl;
@native fun JQuery.slideUp(duration:Int, callback:()->Unit) :Unit = noImpl
@native fun JQuery.slideDown(duration:Int, callback:()->Unit) :Unit = noImpl
@native fun JQuery.show(callback:()->Unit) :Unit = noImpl;
@native fun JQuery.hide(callback:()->Unit) :Unit = noImpl;

private val DURATION = 200
private val SLIDE_DURATION = DURATION * 2

 interface Effect {
    fun apply(component:Component, callback:Function0<Unit>? = null)
}

 interface BiDirectionEffect {
    fun applyIn(component:Component, callback:Function0<Unit>? = null)
    fun applyOut(component:Component, callback:Function0<Unit>? = null)
}

private fun call(function:Function0<Unit>?) {
    function?.let { function() }
}

 class Show() : Effect {
    override fun apply(component: Component, callback: (() -> Unit)?) {
        jq(component.element).show { call(callback) }
    }
}

 class Hide() : Effect {
    override fun apply(component: Component, callback: (() -> Unit)?) {
        jq(component.element).hide { call(callback) }
    }
}

 class SlideUp() : Effect {
    override fun apply(component: Component, callback:Function0<Unit>?) {
        jq(component.element).slideUp(SLIDE_DURATION) { call(callback) }
    }
}

 class SlideDown : Effect {
    override fun apply(component: Component, callback: (() -> Unit)?) {
        jq(component.element).slideDown(SLIDE_DURATION) { call(callback) }
    }
}

 class FadeOut : Effect {
    override fun apply(component: Component, callback: (() -> Unit)?) {
        jq(component.element).fadeOut(DURATION) { call(callback) }
    }
}

 class FadeIn : Effect {
    override fun apply(component: Component, callback: (() -> Unit)?) {
        jq(component.element).fadeIn(DURATION) { call(callback) }
    }
}

 class Fade : BiDirectionEffect {
    override fun applyIn(component: Component, callback: (() -> Unit)?) {
        FadeIn().apply(component, callback)
    }

    override fun applyOut(component: Component, callback: (() -> Unit)?) {
        FadeOut().apply(component, callback)
    }
}

 class Slide : BiDirectionEffect {
    override fun applyIn(component: Component, callback: (() -> Unit)?) {
        SlideDown().apply(component, callback)
    }

    override fun applyOut(component: Component, callback: (() -> Unit)?) {
        SlideUp().apply(component, callback)
    }
}