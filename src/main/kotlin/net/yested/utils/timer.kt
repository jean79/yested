package net.yested.utils

import org.w3c.dom.HTMLElement
import net.yested.AjaxRequest

native
interface Performance {
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