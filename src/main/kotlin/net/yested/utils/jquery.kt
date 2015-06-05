package net.yested.utils

import jquery.JQuery
import org.w3c.dom.HTMLElement
import org.w3c.dom.Element
import org.w3c.dom.Window
import java.util.ArrayList

public class Position(val top:Int, val left:Int)

public class JSArray(val length:Int)

public native fun JQuery.sortable(options: dynamic):Unit = noImpl;
public native fun JQuery.each(func:(index:Int, element:HTMLElement)->Unit):Unit = noImpl;
public native fun JQuery.disableSelection():Unit = noImpl;
public native fun JQuery.on(event:String, handler:(dynamic)->Unit): Unit = noImpl
public native fun JQuery.off(event:String): Unit = noImpl
public native fun JQuery.keypress(handler: (event:dynamic) -> Unit): Unit = noImpl
public native fun JQuery.css(property:String):String = noImpl
public native fun JQuery.css(property:String, value:String):String = noImpl
public native fun JQuery.offset():Position = noImpl
public native fun JQuery.scrollLeft():Int = noImpl
public native fun JQuery.scrollTop():Int = noImpl
public native fun JQuery.closest(element: Element):JSArray = noImpl

native("$") public fun jq(window: Window): JQuery = JQuery();

native interface JQStatic {
    //throttle: https://github.com/cowboy/jquery-throttle-debounce
    fun <EVENT> throttle(duration:Int, handler:(event:EVENT) -> Unit): Function1<EVENT, Unit> = noImpl
}

native("$") public var jqStatic: JQStatic = null!!

public fun <M> throttle(duration:Int, handler:(event:M) -> Unit): Function1<M, Unit> = jqStatic.throttle(duration, handler)