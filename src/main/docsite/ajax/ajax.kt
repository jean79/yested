package ajax

import net.yested.div
import net.yested.Div

fun ajaxPage(): Div {

    return div {
        +createAjaxGetSection()
    }

}