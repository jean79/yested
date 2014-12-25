package ajax

import net.yested.div
import net.yested.Div

/**
 * Created by jean on 20.12.2014.
 */
fun ajaxPage(): Div {

    return div {
        +createAjaxGetSection()
    }

}