package bootstrap

import net.yested.Div
import net.yested.Span
import net.yested.bootstrap.BtsCheckBox
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.collapsible
import net.yested.bootstrap.Collapsible
import net.yested.with
import net.yested.bootstrap.btsButton
import net.yested.bootstrap.ButtonSize

/**
 * Created by jean on 21.2.2015.
 */
/**
 * Created by jean on 26.1.2015.
 */
fun createCollapsibleSection(id: String): Div {

    val collapsible = Collapsible() with {
        header { +"Click me" }
        content {
            ul {
                li { +"some content" }
                li { +"some content" }
                li { +"some content" }
                li { +"some content" }
            }
        }
    }

    return div(id = id) {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Collapsible" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"Simple collapsible container with link header."
                }
                br()
                h4 { +"Demo" }
                btsButton(size = ButtonSize.EXTRA_SMALL, label = { +"Open" }, onclick = { collapsible.open()})
                nbsp()
                btsButton(size = ButtonSize.EXTRA_SMALL, label = { +"Close" }, onclick = { collapsible.close()})
                nbsp()
                btsButton(size = ButtonSize.EXTRA_SMALL, label = { +"Toggle" }, onclick = { collapsible.toggle()})
                br(); br()
                +collapsible
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content =
"""val collapsible = Collapsible() with {
    header { +"Click me" }
    content {
        ul {
            li { +"some content" }
            li { +"some content" }
            li { +"some content" }
            li { +"some content" }
        }
    }
}
...
btsButton(size = ButtonSize.EXTRA_SMALL, label = { +"Open" }, onclick = { collapsible.open()})
nbsp()
btsButton(size = ButtonSize.EXTRA_SMALL, label = { +"Close" }, onclick = { collapsible.close()})
nbsp()
btsButton(size = ButtonSize.EXTRA_SMALL, label = { +"Toggle" }, onclick = { collapsible.toggle()})
br(); br()
+collapsible""")
            }
        }
    }

}