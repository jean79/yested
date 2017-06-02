package net.yested

import kotlin.browser.window

external
private interface JQRouter {
    fun on(eventName:String, listener:() -> Unit):Unit
}

inline private val routerJQuery: JQRouter
    get() = js("$(window)")

private fun getHashSplitted(): Array<String> {
    return window.location.hash.split("_").toTypedArray()
}

 fun registerHashChangeListener(runNow:Boolean = true, listener:(Array<String>) -> Unit) {
    routerJQuery.on("hashchange") {
        listener(getHashSplitted())
    }
    if (runNow) {
        listener(getHashSplitted())
    }
}
