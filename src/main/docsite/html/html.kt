package html

import net.yested.div
import net.yested.Div

/**
 * Created by jean on 20.12.2014.
 */
fun htmlPage(): Div {

    return div {
        +htmlSection()
    }

}