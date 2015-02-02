package bootstrap

import net.yested.Div
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.aligned
import net.yested.bootstrap.TextAlign
import net.yested.bootstrap.uppercase
import net.yested.bootstrap.lowercase
import net.yested.bootstrap.capitalize
import net.yested.bootstrap.Medium

/**
 * Created by jean on 20.12.2014.
 */
fun createTypographySection(id: String): Div {

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Typography" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""
Simple Typography support.
"""
                }
                br()
                h4 { +"Demo" }
                aligned(TextAlign.RIGHT) { +"Right Align"}
                aligned(TextAlign.LEFT) { +"Left Align"}
                aligned(TextAlign.CENTER) { +"Center"}
                aligned(TextAlign.JUSTIFY) { +"Justify"}
                aligned(TextAlign.NOWRAP) { +"No wrap"}
                p { uppercase { +"all is upercase" }}
                p { lowercase { +"ALL IS lowerCase" }}
                p { capitalize { +"capitalized" }}
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content=
"""aligned(TextAlign.RIGHT) { +"Right Align"}
aligned(TextAlign.LEFT) { +"Left Align"}
aligned(TextAlign.CENTER) { +"Center"}
aligned(TextAlign.JUSTIFY) { +"Justify"}
aligned(TextAlign.NOWRAP) { +"No wrap"}
p { uppercase { +"all is upercase" }}
p { lowercase { +"ALL IS lowerCase" }}
p { capitalize { +"capitalized" }}""")
            }
        }
    }

}