package net.yested.bootstrap

import net.yested.AjaxRequest
import net.yested.ajaxJQuery

native private interface JQAffix {
    fun scrollspy(parameters:Any?) : Unit = noImpl
}

native("$('body')") private var affixJQuery: JQAffix = null!!

public fun enableScrollSpy(id:String) : Unit {
    affixJQuery.scrollspy(object { val target = "#${id}" })
}