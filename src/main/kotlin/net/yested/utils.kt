package net.yested

import org.w3c.dom.Element
import org.w3c.dom.Node
import net.yested.utils.isIncludedInDOM
import org.w3c.dom.HTMLElement
import kotlin.browser.document
import kotlin.browser.window

public fun <T> T.with(init:T.()->Unit):T {
    this.init()
    return this
}

/**
 * Lookup element on a html page
 */
fun el(elementId:String):Element? {
    return document.getElementById(elementId);
}

fun printMarkup(content:String):String =
    content.replaceAll("<", "&lt;").replaceAll(">", "&gt;")

fun <T> Boolean.isTrue(value:T, default:T):T =
        if (this) value else default

public fun HTMLElement.whenAddedToDom(run: () -> Unit) {
    repeatWithDelayUntil (
            check = { isIncludedInDOM(this) },
            millisecondInterval = 100,
            run = run
    )
}

public fun repeatWithDelayUntil(check:()->Boolean, millisecondInterval:Int, run:()->Unit) {
    if (check()) {
        run()
    } else {
        window.setTimeout({repeatWithDelayUntil(check, millisecondInterval, run)}, millisecondInterval)
    }
}

public fun <T, V : Comparable<V>> compareByValue(get: (T) -> V?): (T, T) -> Int {
    return { l, r -> compareValues(get(l), get(r)) }
}

public native fun Node.isSameNode(node:Node):Boolean = noImpl

public fun Node.getIndexOfChildNode(childElement:Node):Int {
    var index = 0
    while (index < this.childNodes.length) {
        if (this.childNodes.item(index)!!.isSameNode(childElement)) {
            return index
        }
        index++;
    }
    return -1
}
