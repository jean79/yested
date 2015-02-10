package net.yested.utils

import kotlin.js.dom.html.HTMLElement
import net.yested.AjaxRequest

native
trait Performance {
    fun now(): Int = noImpl
}

native("performance") public var performance: Performance = null!!

fun measure(title:String = "Execution", code:()->Unit) {
    val start = performance.now()
    try {
        code()
    } finally {
        println("${title} took ${performance.now() - start} ms.")
    }
}