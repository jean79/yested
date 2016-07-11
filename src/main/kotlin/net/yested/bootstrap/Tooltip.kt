package net.yested.bootstrap

import jquery.JQuery
import org.w3c.dom.HTMLElement
import net.yested.whenAddedToDom
import jquery.jq

@native
private fun JQuery.tooltip(options: Any): Unit = noImpl
 data class TooltipDelay(val show: Int, val hide: Int)
 enum class TooltipPlacement {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT,
    AUTO
}
 enum class TooltipTrigger {
    CLICK,
    HOVER,
    FOCUS,
    MANUAL
}
 data class TooltipViewPort(val selector: String, val padding: Int)
 data class TooltipOptions(val animation: Boolean = true,
                                 val delay: TooltipDelay = TooltipDelay(0, 0),
                                 val html: Boolean = false,
                                 val placement: TooltipPlacement = TooltipPlacement.TOP,
                                 val trigger: Array<TooltipTrigger> = arrayOf(TooltipTrigger.HOVER, TooltipTrigger.FOCUS),
                                 val viewPort: TooltipViewPort = TooltipViewPort("body", 0)
)

 fun addTooltip(element: HTMLElement, options: TooltipOptions = TooltipOptions(), title: HTMLElement.()->String) {
    element.whenAddedToDom {
        jq(element).tooltip(object {
            val animation = options.animation
            val delay = object {val show = options.delay.show; val hide = options.delay.hide}
            val html = options.html
            val placement = options.placement.name.toLowerCase()
            val trigger = options.trigger.map { it.name.toLowerCase() }.joinToString(" ")
            val viewPort = object {val selector = options.viewPort.selector; val padding = options.viewPort.padding}
            val title = title
        })
    }
}

 fun removeTooltip(element: HTMLElement) {
    element.whenAddedToDom {
        jq(element).tooltip("destroy")
    }
}

 fun showTooltip(element: HTMLElement) {
    element.whenAddedToDom {
        jq(element).tooltip("show")
    }
}