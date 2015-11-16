package basics

import net.yested.Div
import net.yested.div

fun basicPage(): Div {

    return div {
        +aboutSection()
        +kotlinSection()
        +howItWorksSection()
    }

}