package net.yested.utils

import org.w3c.dom.Node
import net.yested.with

private fun getTopNode(node:Node):Node? {
    var n:Node? = node.parentNode
    while (n != null && n!!.nodeName != "#document") {
        n = n!!.parentNode
    }
    return n
}

/**
 * @return true if given element is included in page DOM
 */
public fun isIncludedInDOM(node:Node):Boolean {
    val topNode = getTopNode(node)
    return topNode != null && topNode.nodeName == "#document"
}