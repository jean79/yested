package layout

import net.yested.Component
import net.yested.Div
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.row
import net.yested.with
import org.w3c.dom.HTMLElement

class LayoutPage : Component {

    override val element: HTMLElement = (Div() with {
        row {
            col(Medium(12)) {
                pageHeader {
                    h3 { +"Layout" }
                }
                +"Useful Layout utilities"
            }
        }
        row {
            col(Medium(12)) {
                +createScrollBarSection()
                +createHorizontalLayoutSection()
            }
        }

    }).element
}
