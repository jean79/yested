package bootstrap

import net.yested.Div
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.Medium

/**
 * Created by jean on 20.12.2014.
 */
fun boostrapPage():Div =
        div {
            row {
                col(Medium(12)) {
                    pageHeader {
                        h3 { +"Twitter Bootstrap wrappers"}
                    }
                    +"Yested Framework provides simple wrappers for some Twitter Boootstrap components."
                }
            }
            +createButtons()
            +createTypographySection()
            +buttonGroupsSection()
            +createForm()
            +createSelectSection()
            +createInputs()
            +createGrid()
            +createTabs()
            +createPanelSection()
            +createDialogs()
        }