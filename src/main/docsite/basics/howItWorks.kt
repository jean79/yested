package basics

import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div

fun howItWorksSection(): Div {

    return div {
        row {
            col(12) {
                pageHeader { h3 { +"Fundamentals of Framework" } }
            }
        }
        row {
            col(4) {
                h4 { +"Just a single interface"}
                div {
                    +"""All framework components are just simple wrappers around HTMLElement.<br />
                        Then they provide usefull methods for manipulation with HTML. I.e. attribute settings or DOM subtree manipulatio.<br />
                        All components have to implement trait (interface) Component."""
                }
            }
            col(8) {
                h4 { nbsp() }
                code(lang="kotlin") {
                    +"""trait Component {
    val element : HTMLElement
}"""
                }
            }
        }
        br()
        row {
            col(4) {
                h4 { +"Component creation"}
                div {
                    +"""Typicaly components extend HTMLParentComponent"""
                }
            }
            col(8) {
                h4 { nbsp() }
                code(lang="kotlin") {
                    +"""class Anchor(href:String? = null) : HTMLParentComponent("a") {

    { //this is constructor
        if (href != null) {
            href(href)
        }
    }

    fun href(url:String) {
        element.setAttribute("href", url)
    }

}"""
                }
            }
        }
        br()
        row {
            col(4) {
                h4 { +"Yested application initialization"}
                div {
                    +"""You need to have a DIV in your html page with id "page". Then Yested app will be renderred into this div using command on the right."""
                }
            }
            col(8) {
                h4 { nbsp() }
                code(lang="kotlin") {
                    +"""page("page") {
        topMenu(navbar)
        content {
            div {
                a(href="http://www.yested.net") { +"Yested homepage" }
            }
        }
    }"""
                }
            }
        }
        row {
            col(4) {
                h4 { +"DSL for layout construction"}
                div {
                    +"""To understand the DSL please take look at <a href="http://kotlinlang.org/docs/reference/type-safe-builders.html">Kotlin HTML builder</a>.
                    Have you got it? Then Yested is written in the same DSL way but each object wraps a single HTML element and manipulates with it in a runtime.
                    """
                }
            }
            col(8) {
                h4 { nbsp() }
                code(lang = "kotlin") {
+"""div {
    p {
        h5 { +"Demo list" }
        ul {
            li { a(href="http://www.yested.net") { +"Yested" } }
            li { emph { +"Bold text" }
            li { colorized(color="#778822") { +"Colorized text" } }
        }
    }
}
"""
                }
            }
        }
    }

}