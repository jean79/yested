package net.yested.utils

external interface Performance {
    fun now(): Int
}

external val performance: Performance

fun measure(title:String = "Execution", code:()->Unit) {
    val start = performance.now()
    try {
        code()
    } finally {
        println("${title} took ${performance.now() - start} ms.")
    }
}