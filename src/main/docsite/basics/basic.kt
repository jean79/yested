package basics

import net.yested.Div
import net.yested.div

/**
 * Created by jean on 20.12.2014.
 */
fun basicPage(): Div {

    return div {
        +aboutSection()
        +kotlinSection()
        +howItWorksSection()
    }

}