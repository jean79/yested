package html

import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.Medium
import net.yested.TextArea
import net.yested.with

fun elementEventsSection(): Div {

    var index = 0
    val capturedEvents = TextArea(rows = 8) with { clazz = "form-control" }

    fun addMessage(eventName:String) {
        capturedEvents.data += "${index++} - ${eventName}\n"
        capturedEvents.scrollDown()
    }

    return div {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"HTML Events" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""Yested components derived from HTMLComponents support all DOM Element events.
                    Please note that onmousemove is ignored in this demo."""
                }
            }
        }
        row {
            col(Medium(4)) {
                h4 { +"Demo" }
                div {
                    textArea(rows = 3) {
                        clazz = "form-control"
                        data = "Play with this TextArea to see all the events dispatched."
                        onmouseover = { addMessage("onmouseover") }
                        onmouseout = { addMessage("onmouseout") }
                        onclick = { addMessage("onclick") }
                        onblur = { addMessage("onblur") }
                        ondblclick = { addMessage("ondblclick") }
                        onfocus = { addMessage("onfocus") }
                        onkeydown = { addMessage("onkeydown") }
                        onkeyup = { addMessage("onkeyup") }
                        onmousedown = { addMessage("onmousedown") }
                        onmouseup = { addMessage("onmouseup") }
                        onresize = { addMessage("onresize") }
                    }
                    br()
                    +capturedEvents
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content=
"""var index = 0
val capturedEvents = TextArea(rows = 8) with { clazz = "form-control" }

fun addMessage(eventName:String) {
    capturedEvents.value += "$\{index++} - $\{eventName}\\n"
    capturedEvents.scrollDown()
}
...
div {
    textArea(rows = 3) {
        clazz = "form-control"
        value = "Play with this TextArea to see all the events dispatched."
        onmouseover = { addMessage("onmouseover") }
        onmouseout = { addMessage("onmouseout") }
        onclick = { addMessage("onclick") }
        onblur = { addMessage("onblur") }
        ondblclick = { addMessage("ondblclick") }
        onfocus = { addMessage("onfocus") }
        onkeydown = { addMessage("onkeydown") }
        onkeyup = { addMessage("onkeyup") }
        onmousedown = { addMessage("onmousedown") }
        onmouseup = { addMessage("onmouseup") }
        onresize = { addMessage("onresize") }
    }
    br()
    +capturedEvents
}
""")
            }
        }
    }

}