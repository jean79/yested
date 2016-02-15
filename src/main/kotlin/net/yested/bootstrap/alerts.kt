package net.yested.bootstrap

import net.yested.HTMLComponent
import net.yested.Anchor
import net.yested.with
import net.yested.isTrue
import org.w3c.dom.events.Event

/**
 * Created by jean on 25.12.2014.
 *
 */

 enum class AlertStyle(val code:String) {
    SUCCESS("success"),
    INFO("info"),
    WARNING("warning"),
    DANGER("danger")
}

 class Alert(style: AlertStyle, dismissible: Boolean = false) : HTMLComponent("div") {

    init {
        clazz = "alert alert-${style.code} ${dismissible.isTrue("alert-dismissible", "")}"
        if (dismissible) {
            tag("button") {
                clazz = "close"; "type".."button"; "data-dismiss".."alert"; "aria-label".."Close"
                span {
                    "aria-hidden".."true"
                    +"&times;"
                }
            }
        }
    }

    override fun a(clazz: String?, target: String?, href: String?, onclick: ((Event) -> Unit)?, init: Anchor.() -> Unit) {
        super.a(clazz = clazz?:"alert-link", target = target, href = href, onclick = onclick, init = init)
    }

}

fun HTMLComponent.alert(style: AlertStyle, dismissible: Boolean = false, init:Alert.() -> Unit) =
    +(Alert(style = style, dismissible = dismissible) with { init() } )
