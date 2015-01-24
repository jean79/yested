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
import net.yested.Fade
import net.yested.bootstrap.buttonGroup
import net.yested.Slide
import net.yested.Effect
import net.yested.BiDirectionEffect
import net.yested.bootstrap.aligned
import net.yested.bootstrap.TextAlign
import net.yested.bootstrap.ButtonLook

fun createPanel(n:Int) =
        Panel() with {
            heading { +"Sample component $n" }
            content {
                +"Sample Text of component $n"
            }
        }

fun createBidirectionalEffectsSection(): Div {

    var index = 0
    val panels = array(createPanel(0), createPanel(1))
    val container = Div()
    var effect: BiDirectionEffect = Fade()

    fun selectEffect(effectCode:String) {
        effect =
            when(effectCode) {
                "fade" -> Fade()
                "slide" -> Slide()
                else -> throw Exception("Unknown effect.")
            }
    }

    fun toogleContent() =
        container.setChild(panels.get(index++ % panels.size()), effect)

    toogleContent()

    return div {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"BiDirectional Effects" } }
            }
        }
        row {
            col(Medium(6)) {
+"""BiDirectonalEffects can be used to swap content of parent component like Div or Span"""
                code(lang="kotlin", content =
"""divOrSpan.setChild(anotherComponent, Fade())
""")
                h4 { +"Demo" }
                div {
                    buttonGroup(onSelect = ::selectEffect) {
                        button(value = "fade") { +"Fade Effect" }
                        button(value = "slide") { +"Slide Effect" }
                        select("fade")
                    }
                    br()
                    br()
                    btsButton(look = ButtonLook.PRIMARY, label = { +"Toogle it" }, onclick = ::toogleContent)
                }
                br()
                br()
                +container
            }
            col(Medium(6)) {
                h4 { +"Source code"}
                code(lang = "kotlin", content =
"""var index = 0
val panels = array(createPanel(0), createPanel(1))
val container = Div()
var effect: BiDirectionEffect = Fade()

fun selectEffect(effectCode:String) {
    effect =
        when(effectCode) {
            "fade" -> Fade()
            "slide" -> Slide()
            else -> throw Exception("Unknown effect.")
        }
}

fun toogleContent() =
    container.setChild(panels.get(index++ % panels.size()), effect)

toogleContent()

...

div {
    buttonGroup(onSelect = ::selectEffect) {
        button(value = "fade") { +"Fade Effect" }
        button(value = "slide") { +"Slide Effect" }
        select("fade")
    }
    br()
    br()
    btsButton(look = ButtonLook.PRIMARY, label = { +"Toogle it" }, onclick = ::toogleContent)
}
br()
br()
+container""")
            }
        }
    }

}