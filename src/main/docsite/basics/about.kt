package basics

import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.btsButton
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.btsAnchor
import net.yested.bootstrap.ButtonSize

fun aboutSection(): Div {

    return div {
        row {
            col(12) {
                pageHeader { h3 { +"What is Yested" } }
            }
        }
        row {
            col(6) {
                div {
                    p {
                        +"Yested is a Kotlin framework for building single-page web applications in Javascript."
                        h5 { +"Main features" }
                        ul {
                            li { +"Strongly typed development of Web applications" }
                            li { +"Minimalistic code" }
                            li { +"DSL for layout construction"}
                            li { +"Debugging within browser"}
                            li { +"Component style of development" }
                            li { +"Simple re-use of 3rd party Javascript libraries" }
                            li { +"Simple creation and re-use of custom components" }
                            li { +"Built-in support for Twitter Bootstrap for a quick start" }
                        }
                    }
                }
            }
            col(6) {
                p {
                    btsAnchor(size = ButtonSize.LARGE, look = ButtonLook.PRIMARY, href = "https://github.com/jean79/yested") { +"Get on GitHub"}
                }
                p {
                    +"Contact: "
                    a(href="mailto:jan.kovar79@gmail.com") { +"jan.kovar79@gmail.com" }
                }
                p {
                    +"This page is developed in Yested framework"
                    br()
                    a(href = "https://github.com/jean79/yested/tree/master/src/main/docsite") {
                        +"See source code for this site" }
                }
            }
        }
    }

}