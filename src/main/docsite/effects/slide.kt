package effects

import net.yested.div
import net.yested.bootstrap.btsButton
import net.yested.Div
import net.yested.bootstrap.panel
import net.yested.bootstrap.Panel
import net.yested.with
import net.yested.SlideUp
import net.yested.SlideDown
import net.yested.bootstrap.row
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader

/**
 * Created by jean79 on 21/01/15.
 */
fun createEffectsSection(): Div {

    var visible: Boolean = true

    val target = Panel() with {
        heading { +"Sample component" }
        content {
            +"Sample Text"
            br()
            emph { +"Some bolded text" }
            br()
            a() { +"Some link" }
        }
    }

    return div {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Slide Up/Down" } }
            }
        }
        row {
            col(Medium(6)) {
                +"Effects are applied directly on components:"
                code(lang="kotlin", content = "SlideUp().apply(component)")
                h4 { +"Demo" }
                btsButton(label = { +"Toogle it" }) {
                    if (visible) {
                        SlideUp().apply(target)
                    } else {
                        SlideDown().apply(target)
                    }
                    visible = !visible
                }
                //+" Press button to slide up/down panel. "
                br()
                br()
                +target
            }
            col(Medium(6)) {
                h4 { +"Source code"}
                code(lang = "kotlin", content =
"""var visible: Boolean = true

val target = Panel() with {
    heading { +"Sample component" }
    content {
        +"Sample Text"
        br()
        emph { +"Some bolded text" }
        br()
        br()
        a() { +"Some text" }
    }
}

...

div {
    btsButton(label = { +"Toogle it" }) {
        if (visible) {
            SlideUp().apply(target)
        } else {
            SlideDown().apply(target)
        }
        visible = !visible
    }
    br()
    +target
}""")
            }
        }
    }

}