package net.yested.utils

import jquery.JQuery
import kotlin.js.dom.html.HTMLElement
import org.w3c.dom.Element

public native fun JQuery.sortable(options: dynamic):Unit = noImpl;
public native fun JQuery.each(func:(index:Int, element:HTMLElement)->Unit):Unit = noImpl;
public native fun JQuery.disableSelection():Unit = noImpl;
public native fun JQuery.on(event:String, handler:(dynamic)->Unit): Unit = noImpl
public native fun JQuery.off(event:String): Unit = noImpl
public native fun JQuery.keypress(handler: (event:dynamic) -> Unit): Unit = noImpl
public native fun JQuery.css(property:String):String = noImpl
public native fun JQuery.css(property:String, value:String):String = noImpl

native trait JQStatic {
    //throttle: https://github.com/cowboy/jquery-throttle-debounce
    fun <EVENT> throttle(duration:Int, handler:(event:EVENT) -> Unit): Function1<EVENT, Unit> = noImpl
}

native("$") public var jqStatic: JQStatic = null!!

public fun <M> throttle(duration:Int, handler:(event:M) -> Unit): Function1<M, Unit> = jqStatic.throttle(duration, handler)