package basics

import net.yested.bootstrap.Grid
import net.yested.bootstrap.Column
import net.yested.text
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize

fun kotlinSection(): Div {

    return div {
        row {
            col(12) {
                pageHeader { h3 { +"Kotlin to Javascript Compiler" } }
            }
        }
        row {

            col(4) {
                div {

                    p {
                        a(href = "http://kotlinlang.org") { +"Kotlin" }
                        +" is a language created by JetBrains company. It compiles to JVM or to Javascript."
                    }
                    p {
                        +"""Main method (see example on the right) will be executed when HTML page is loaded.
                    """
                    }
                    p {
                        +"""Kotlin to Javascript compiler allows you to simply call Javascript functions, allowing
                        us to create a simple strongly typed wrappers.
                        """
                    }

                }
            }
            col(8) {
                h4 { +"Simplest Kotlin Code" }
                code(lang = "kotlin") {
                    +
                    """fun main(args: Array<String>) {
    println("This will be printed into a Javascript console.")
}"""
                }
            }
        }
    }

}