package net.yested.utils

import jquery.jq
import net.yested.Component
import kotlin.js.dom.html.HTMLElement
import kotlin.js.dom.html.window

private class ResizeHandler(
        val element:HTMLElement,
        val handler:(width:Number, height:Number) -> Unit) {

    private var latestWidth = jq(element).width()
    private var latestHeight = jq(element).height()

    public fun check() {
        val newWidth = jq(element).width()
        val newHeight = jq(element).height()
        if (newWidth != latestWidth || newHeight != latestHeight) {
            latestWidth = newWidth
            latestHeight = newHeight
            handler(latestWidth, latestHeight)
        }
    }

}

private var timerId:Long = -1

private var resizeHandlers = arrayListOf<ResizeHandler>()

private fun checkAllHandlers() {
    resizeHandlers.forEach { it.check() }
}

public fun registerResizeHandler(element: HTMLElement, handler:(width:Number, height:Number) -> Unit) {

    resizeHandlers.add(ResizeHandler(element, handler))

    if (resizeHandlers.size() == 1) {
        timerId = window.setInterval( { checkAllHandlers() }, 200)
    }

}