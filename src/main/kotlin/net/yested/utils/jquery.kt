package net.yested.utils

import jquery.JQuery
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.Window

 class Position(val top:Int, val left:Int)

 class JSArray(val length:Int)

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.sortable(options: dynamic):Unit = asDynamic().sortable(options)

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.each(noinline func:(index:Int, element:HTMLElement)->Unit):Unit = asDynamic().each(func)

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.disableSelection():Unit = asDynamic().disableSelection()

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.on(event:String, noinline handler:(dynamic)->Unit): Unit = asDynamic().on(event, handler)

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.off(event:String): Unit = asDynamic().off(event)

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.keypress(noinline handler: (event:dynamic) -> Unit): Unit = asDynamic().keypress(handler)

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.css(property:String):String = asDynamic().css(property)

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.css(property:String, value:String):String = asDynamic().css(property, value)

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.offset():Position = asDynamic().offset()

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.scrollLeft():Int = asDynamic().scrollLeft()

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.scrollTop():Int = asDynamic().scrollTop()

@Suppress("NOTHING_TO_INLINE")
inline fun JQuery.closest(element: Element):JSArray = asDynamic().closest(element)

@JsName("$")
external fun jq(window: Window): JQuery = definedExternally

external interface JQStatic {
    //throttle: https://github.com/cowboy/jquery-throttle-debounce
    fun <EVENT> throttle(duration:Int, handler:(event:EVENT) -> Unit): Function1<EVENT, Unit>
}

@JsName("$")
external var jqStatic: JQStatic

 fun <M> throttle(duration:Int, handler:(event:M) -> Unit): Function1<M, Unit> = jqStatic.throttle(duration, handler)