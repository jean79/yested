package net.yested.bootstrap

import net.yested.HTMLComponent
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

public class Alert(style: AlertStyle) : HTMLComponent("div") {

    {
        clazz = "alert alert-${style.code}"
    }

    override fun a(clazz: String?, href: String?, onclick: (() -> Unit)?, init: Anchor.() -> Unit) {
        super<HTMLComponent>.a(clazz?:"alert-link", href, onclick, init)
    }

}

fun HTMLComponent.alert(style: AlertStyle, init:Alert.() -> Unit) =
    +(Alert(style = style) with { init() } )
