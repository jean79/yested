package net.yested

import kotlin.js.dom.html.window

native
private trait JQRouter {
    fun on(eventName:String, listener:() -> Unit):Unit = noImpl
}

native("$(window)") private var routerJQuery: JQRouter = null!!

private fun getHashSplitted(): Array<String> {
    return window.location.hash.split("/")
}

fun registerHashChangeListener(runNow:Boolean = true, listener:(Array<String>) -> Unit) {
    routerJQuery.on("hashchange") {
        listener(getHashSplitted())
    }
    if (runNow) {
        listener(getHashSplitted())
    }
}
