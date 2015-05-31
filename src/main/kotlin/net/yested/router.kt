package net.yested

import kotlin.browser.window

native
private interface JQRouter {
    fun on(eventName:String, listener:() -> Unit):Unit = noImpl
}

native("$(window)") private var routerJQuery: JQRouter = null!!

private fun getHashSplitted(): Array<String> {
    return window.location.hash.split("_")
}

public fun registerHashChangeListener(runNow:Boolean = true, listener:(Array<String>) -> Unit) {
    routerJQuery.on("hashchange") {
        listener(getHashSplitted())
    }
    if (runNow) {
        listener(getHashSplitted())
    }
}
