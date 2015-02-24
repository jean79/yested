package net.yested

import org.w3c.dom.Element
import kotlin.js.dom.html.document
import org.w3c.dom.Node
import kotlin.js.dom.html.window

public fun <T> T.with(init:T.()->Unit):T {
    this.init()
    return this
}

/**
 * Lookup element on a html page
 */
fun el(elementId:String):Element {
    return document.getElementById(elementId);
}

fun printMarkup(content:String):String =
    content.replaceAll("<", "&lt;").replaceAll(">", "&gt;")

fun <T> Boolean.isTrue(value:T, default:T):T =
        if (this) value else default

public fun repeatWithDelayUntil(check:()->Boolean, millisecondInterval:Int, run:()->Unit) {
    if (check()) {
        run()
    } else {
        window.setTimeout({repeatWithDelayUntil(check, millisecondInterval, run)}, millisecondInterval)
    }
}