package net.yested.utils

import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import org.w3c.dom.Window

/**
 * @return true if given element is included in page DOM
 *
 * check this:
 * http://stackoverflow.com/questions/19669786/check-if-element-is-visible-in-dom
* return (node as HTMLElement).offsetParent != null // this is fast but does not work in IE9
 */

external class CSSStyleDeclaration {
    val display:String
}

@Suppress("NOTHING_TO_INLINE")
inline fun Window.getComputedStyle(node:Node):CSSStyleDeclaration = asDynamic().getComputedStyle(node)

 fun isIncludedInDOM(node:Node):Boolean {
    /*var style = window.getComputedStyle(node);
    return style.display != "none" && style.display != ""*/
    return (node as HTMLElement).offsetParent != null
}