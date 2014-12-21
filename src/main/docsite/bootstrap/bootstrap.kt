package bootstrap

import net.yested.Div
import net.yested.div

/**
 * Created by jean on 20.12.2014.
 */
fun boostrapPage():Div =
        div {
            +createButtons()
            +createForm()
            +createInputs()
            +createGrid()
            +createTabs()
        }