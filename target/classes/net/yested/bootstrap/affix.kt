package net.yested.bootstrap

import net.yested.AjaxRequest
import net.yested.ajaxJQuery

/**
 * Created by jean on 14.1.2015.
 */
native private trait JQAffix {
    fun scrollspy(parameters:Any?) : Unit = noImpl
}

native("$('body')") private var affixJQuery: JQAffix = null!!

public fun enableScrollSpy(id:String) : Unit {
    affixJQuery.scrollspy(object { val target = "#${id}" })
}