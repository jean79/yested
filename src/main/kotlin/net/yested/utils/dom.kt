package net.yested.utils

import org.w3c.dom.Node
import net.yested.with
import kotlin.js.dom.html.HTMLElement
import kotlin.js.dom.html.Window
import kotlin.js.dom.html.document
import kotlin.js.dom.html.window

/**
 * @return true if given element is included in page DOM
 *
 * check this:
 * http://stackoverflow.com/questions/19669786/check-if-element-is-visible-in-dom
* return (node as HTMLElement).offsetParent != null // this is fast but does not work in IE9
 */

native class CSSStyleDeclaration(
        val display:String)

native fun Window.getComputedStyle(node:Node):CSSStyleDeclaration = noImpl

public fun isIncludedInDOM(node:Node):Boolean {
    /*var style = window.getComputedStyle(node);
    return style.display != "none" && style.display != ""*/
    return (node as HTMLElement).offsetParent != null
}