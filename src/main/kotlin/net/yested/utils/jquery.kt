package net.yested.utils

import jquery.JQuery
import kotlin.js.dom.html.HTMLElement
import org.w3c.dom.Element

/**
 * Created by jean on 7.2.2015.
 */
public native fun JQuery.sortable(options: dynamic):Unit = noImpl;
public native fun JQuery.each(func:(index:Int, element:HTMLElement)->Unit):Unit = noImpl;
public native fun JQuery.disableSelection():Unit = noImpl;
public native fun JQuery.on(event:String, handler:(dynamic)->Unit): Unit = noImpl
public native fun JQuery.off(event:String): Unit = noImpl
public native fun JQuery.keypress(handler: (event:dynamic) -> Unit): Unit = noImpl