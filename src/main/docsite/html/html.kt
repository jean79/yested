package html

import net.yested.div
import net.yested.Div
import net.yested.bootstrap.row
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader

/**
 * Created by jean on 20.12.2014.
 */
fun htmlPage(): Div {

    return div {
        +htmlSection()
        +elementEventsSection()
    }

}