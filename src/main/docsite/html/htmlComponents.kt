package html

import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize

fun htmlSection(): Div {

    return div {
        row {
            col(12) {
                pageHeader { h3 { +"HTML" } }
            }
        }
        row {
            col(4) {
                div {
                    +"""Yested provides basic DSL for construction of HTML page from HTML basic elements.<br /><br />
                    This DSL can be easily enhanced with your custom or built-in Bootstrap components via Kotlin Extension methods."""
                }
            }
        }
        row {
            col(4) {
                h4 { +"Demo" }
                div {
                    a(href = "http://www.yested.net") { +"Yested" }
                    br()
                    div {
                        span { +"Text in span which is in div" }
                    }
                    p { +"Text in Paragraph" }
                    table {
                        border = "1"
                        thead {
                            tr {
                                th { +"First column" }
                                th { +"Second column" }
                            }
                        }
                        tbody {
                            tr {
                                td { +"Cell 1" }
                                td { +"Cell 2" }
                            }
                            tr {
                                td { +"Cell 1" }
                                td { +"Cell 2" }
                            }
                        }
                    }
                    img(src = "demo-site/img/sample_img.jpg", alt = "bla") {}
                    emph { +"bold text" }
                    h1 { +"H1" }
                    h2 { +"H2" }
                    h3 { +"H3" }
                    h4 { +"H4" }
                    h5 { +"H5" }
                    button(label = { +"Press me" },
                            type = ButtonType.BUTTON,
                            onclick = { println("Check console!") })
                    ul {
                        li { +"List item 1" }
                        li { +"List item 2" }
                        li { +"List item 3" }
                        li { +"List item 4" }
                    }

                }
            }
            col(8) {
                h4 { +"Code" }
                code(lang = "kotlin") {
                    +
                    """div {
    a(href="http://www.yested.net") { +"Yested"}
    br()
    div {
        span { +"Text in span which is in div"}
    }
    p { +"Text in Paragraph" }
    table { border = "1"
        thead {
            tr {
                th { +"First column" }
                th { +"Second column"}
            }
        }
        tbody {
            tr {
                td { +"Cell 1"}
                td { +"Cell 2"}
            }
            tr {
                td { +"Cell 1"}
                td { +"Cell 2"}
            }
        }
    }
    img(src = "demo-site/img/sample_img.jpg", alt = "bla") {}
    emph { +"bold text" }
    h1 { +"H1" }
    h2 { +"H2" }
    h3 { +"H3" }
    h4 { +"H4" }
    h5 { +"H5" }
    button(label = { +"Press me"},
            type = ButtonType.BUTTON,
            onclick = { println("Check console!")})
    ul {
        li { +"List item 1"}
        li { +"List item 2"}
        li { +"List item 3"}
        li { +"List item 4"}
    }

}"""
                }
            }
        }
    }

}