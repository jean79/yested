package net.yested.bootstrap

external private interface JQAffix {
    fun scrollspy(parameters:Any?) : Unit
}

private val affixJQuery: JQAffix
    get() = js("$('body')")

fun enableScrollSpy(id:String) : Unit {
    affixJQuery.scrollspy(object { val target = "#${id}" })
}