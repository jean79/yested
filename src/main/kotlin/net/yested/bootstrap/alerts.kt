package net.yested.bootstrap

import net.yested.HTMLParentComponent
import net.yested.Div
import net.yested.div
import net.yested.Anchor
import net.yested.with

/**
 * Created by jean on 25.12.2014.
 *
 */

public enum class AlertStyle(val code:String) {
    SUCCESS :   AlertStyle("success")
    INFO :      AlertStyle("info")
    WARNING :   AlertStyle("warning")
    DANGER :    AlertStyle("danger")
}

public class Alert(style: AlertStyle) : Div() {

    {
        clazz = "alert alert-${style.code}"
    }

    override fun a(clazz: String?, href: String?, onclick: (() -> Unit)?, init: Anchor.() -> Unit) {
        super<Div>.a(clazz?:"alert-link", href, onclick, init)
    }

}

fun HTMLParentComponent.alert(style: AlertStyle, init:Alert.() -> Unit) =
    add(
            Alert(style = style) with { init() } )
