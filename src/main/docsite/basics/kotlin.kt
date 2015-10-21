package basics

import net.yested.Div
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.row
import net.yested.div

fun kotlinSection(): Div {

    return div {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Kotlin to Javascript Compiler" } }
            }
        }
        row {

            col(Medium(4)) {
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
            col(Medium(8)) {
                h4 { +"Simplest Kotlin Code" }
                code(lang = "kotlin", content=
"""fun main(args: Array<String>) {
    println("This will be printed into a Javascript console.")
}""")
            }
        }
    }

}