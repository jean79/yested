package bootstrap

import net.yested.bootstrap.Grid
import net.yested.bootstrap.Column
import net.yested.text
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.Medium
import net.yested.bootstrap.BtsButton
import net.yested.with
import net.yested.bootstrap.addTooltip
import net.yested.bootstrap.TooltipOptions
import net.yested.bootstrap.TooltipPlacement
import jquery.jq

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