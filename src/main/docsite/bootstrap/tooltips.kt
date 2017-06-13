package bootstrap

import net.yested.Div
import net.yested.bootstrap.*
import net.yested.div
import net.yested.with

fun createTooltips(id: String): Div {

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Tooltips" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""Refer to Bootstrap tooltips."""
                }
                br()
                h4 { +"Demo" }
                div {
                    +(BtsButton(label = { +"Tooltip on left" }) with {
                        addTooltip(element, TooltipOptions(placement = TooltipPlacement.LEFT), title = {element.innerHTML})
                    })
                    +(BtsButton(label = { +"Tooltip on top" }) with {
                        addTooltip(element, TooltipOptions(placement = TooltipPlacement.TOP), title = {element.innerHTML})
                    })
                    +(BtsButton(label = { +"Tooltip on bottom" }) with {
                        addTooltip(element, TooltipOptions(placement = TooltipPlacement.BOTTOM), title = {element.innerHTML})
                    })
                    +(BtsButton(label = { +"Tooltip on right" }) with {
                        addTooltip(element, TooltipOptions(placement = TooltipPlacement.RIGHT), title = {element.innerHTML})
                    })
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content="""
div {
    +(BtsButton(label = { +"Tooltip on left" }) with {
        addTooltip(element, TooltipOptions(placement = TooltipPlacement.LEFT), title = {this.innerHTML})
    })
    +(BtsButton(label = { +"Tooltip on top" }) with {
        addTooltip(element, TooltipOptions(placement = TooltipPlacement.TOP), title = {this.innerHTML})
    })
    +(BtsButton(label = { +"Tooltip on bottom" }) with {
        addTooltip(element, TooltipOptions(placement = TooltipPlacement.BOTTOM), title = {this.innerHTML})
    })
    +(BtsButton(label = { +"Tooltip on right" }) with {
        addTooltip(element, TooltipOptions(placement = TooltipPlacement.RIGHT), title = {this.innerHTML})
    })
}""")
            }
        }
    }

}