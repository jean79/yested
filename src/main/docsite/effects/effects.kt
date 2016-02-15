package bootstrap

import net.yested.Div
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.Medium

import net.yested.bootstrap.enableScrollSpy
import effects.createEffectsSection
import effects.createBidirectionalEffectsSection

fun effectsPage(): Div {

    return div {

        row {
            row {
                col(Medium(8)) {
                    pageHeader {
                        h3 { +"Effects" }
                    }
                    +"Effects are applied to components. They must implement the Effect interface:"
                    code(lang = "kotlin", content =
""" trait Effect {
    fun apply(component:Component)
}""")
                    +"Effects are based on JQuery effects."
                    br()
                    +"Some effects can applied bidirectionaly - to hide and to show an element for example."
                    br()
                    +"These effects must implement BiDirectionalEffect interface:"
                    code(lang = "kotlin", content =
""" trait BiDirectionEffect {
    fun applyIn(component:Component, callback:Function0<Unit>? = null)
    fun applyOut(component:Component, callback:Function0<Unit>? = null)
}""")
                }
            }
            row {
                col(Medium(12)) {
                    +createEffectsSection()
                    +createBidirectionalEffectsSection()
                }
            }
        }
    }
}