package net.yested

import jquery.JQuery
import jquery.jq

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.fadeOut(duration:Int, noinline callback:()->Unit) :Unit = asDynamic().fadeOut(duration, callback)
@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.fadeIn(duration:Int, noinline callback:()->Unit) :Unit = asDynamic().fadeIn(duration, callback)
@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.slideUp(duration:Int, noinline callback:()->Unit) :Unit = asDynamic().slideUp(duration, callback)
@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.slideDown(duration:Int, noinline callback:()->Unit) :Unit = asDynamic().slideDown(duration, callback)
@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.show(noinline callback:()->Unit) :Unit = asDynamic().show(callback)
@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.hide(noinline callback:()->Unit) :Unit = asDynamic().hide(callback)

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