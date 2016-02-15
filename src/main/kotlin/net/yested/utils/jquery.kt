package net.yested.utils

import jquery.JQuery
import org.w3c.dom.HTMLElement
import org.w3c.dom.Element
import org.w3c.dom.Window
import java.util.ArrayList

 class Position(val top:Int, val left:Int)

 class JSArray(val length:Int)

 @native fun JQuery.sortable(options: dynamic):Unit = noImpl;
 @native fun JQuery.each(func:(index:Int, element:HTMLElement)->Unit):Unit = noImpl;
 @native fun JQuery.disableSelection():Unit = noImpl;
 @native fun JQuery.on(event:String, handler:(dynamic)->Unit): Unit = noImpl
 @native fun JQuery.off(event:String): Unit = noImpl
 @native fun JQuery.keypress(handler: (event:dynamic) -> Unit): Unit = noImpl
 @native fun JQuery.css(property:String):String = noImpl
 @native fun JQuery.css(property:String, value:String):String = noImpl
 @native fun JQuery.offset():Position = noImpl
 @native fun JQuery.scrollLeft():Int = noImpl
 @native fun JQuery.scrollTop():Int = noImpl
 @native fun JQuery.closest(element: Element):JSArray = noImpl

@native("$")  fun jq(window: Window): JQuery = JQuery();

@native interface JQStatic {
    //throttle: https://github.com/cowboy/jquery-throttle-debounce
    fun <EVENT> throttle(duration:Int, handler:(event:EVENT) -> Unit): Function1<EVENT, Unit> = noImpl
}

@native("$")  var jqStatic: JQStatic = null!!

 fun <M> throttle(duration:Int, handler:(event:M) -> Unit): Function1<M, Unit> = jqStatic.throttle(duration, handler)