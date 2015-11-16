package effects

import net.yested.*
import net.yested.bootstrap.*

fun createPanel(n:Int) =
        Panel() with {
            heading { +"Sample component $n" }
            content {
                +"Sample Text of component $n"
            }
        }

fun createBidirectionalEffectsSection(): Div {

    var index = 0
    val panels = arrayOf(createPanel(0), createPanel(1))
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

    fun toggleContent() =
        container.setChild(panels[index++ % panels.size], effect)

    toggleContent()

    return div {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"BiDirectional Effects" } }
            }
        }
        row {
            col(Medium(6)) {
                +"BiDirectonalEffects can be used to swap content of parent component like Div or Span"
                code(lang="kotlin", content ="divOrSpan.setChild(anotherComponent, Fade())")
                h4 { +"Demo" }
                row {
                    col(ExtraSmall(4)) {
                        btsButton(look = ButtonLook.PRIMARY, label = { +"Toggle it" }, onclick = { toggleContent() })
                    }
                    col(ExtraSmall(8)) {
                        aligned(align = TextAlign.RIGHT) {
                            buttonGroup(onSelect = ::selectEffect) {
                                button(value = "fade") { +"Fade Effect" }
                                button(value = "slide") { +"Slide Effect" }
                                select("fade")
                            }
                        }
                    }
                }
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

row {
    col(ExtraSmall(4)) {
        btsButton(look = ButtonLook.PRIMARY, label = { +"Toogle it" }, onclick = ::toogleContent)
    }
    col(ExtraSmall(8)) {
        aligned(align = TextAlign.RIGHT) {
            buttonGroup(onSelect = ::selectEffect) {
                button(value = "fade") { +"Fade Effect" }
                button(value = "slide") { +"Slide Effect" }
                select("fade")
            }
        }
    }
}
+container""")
            }
        }
    }

}