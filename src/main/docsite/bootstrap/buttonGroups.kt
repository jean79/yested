package bootstrap

import net.yested.bootstrap.ButtonGroup
import net.yested.bootstrap.ButtonSize
import net.yested.Span
import net.yested.with
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.page
import net.yested.bootstrap.pageHeader
import net.yested.Div
import net.yested.bootstrap.Medium

/**
 * Created by jean on 25.12.2014.
 */
fun buttonGroupsSection(id: String): Div {

    val span = Span()
    val btnGroup = ButtonGroup(
                                size = ButtonSize.DEFAULT,
                                onSelect = { value -> span.setContent("Selected: ${value}")}
                            ) with {
                                button(value = "1", label = { + "Option 1"})
                                button(value = "2", label = { + "Option 2"})
                            }

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Button Group" }}
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""Refer to Bootstrap buttons groups. Yested version
                    in addition offers a way to get selected value (via btnGroup.value)"""
                }
                br()
                h4 { +"Demo" }
                div {
                    +btnGroup
                    br()
                    +span
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content="""val span = Span()
val btnGroup =
    ButtonGroup(
        size = ButtonSize.DEFAULT,
        onSelect = { value -> span.replace("Selected: $\{value}")}
    ) with {
        button(value = "1", label = { + "Option 1"})
        button(value = "2", label = { + "Option 2"})
    }""")
            }
        }
    }

}