package html

import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.Medium

fun htmlSection(): Div {

    return div {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"HTML" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""Yested provides basic DSL for construction of HTML page from HTML basic elements.<br /><br />
                    This DSL can be easily enhanced with your custom or built-in Bootstrap components via Kotlin Extension methods."""
                }
            }
        }
        row {
            col(Medium(4)) {
                h4 { +"Demo" }
                div {
                    a(href = "http://www.yested.net") { +"Yested" }
                    br()
                    div {
                        span { +"Text in span which is in div" }
                    }
                    p { +"Text in Paragraph" }
                    s { +"Strikethrough text" }
                    nbsp()
                    del { +"Deleted text" }
                    nbsp()
                    mark { +"Marked text" }
                    nbsp()
                    ins { +"Inserted text" }
                    nbsp()
                    u { +"Underlined text" }
                    nbsp()
                    small { +"Small text" }
                    nbsp()
                    strong { +"Strong text" }
                    nbsp()
                    em { +"Italicized text." }
                    br()
                    br()
                    blockquote { +"blockquote" }
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
                    img(src = "demo-site/img/sample_img.jpg", alt = "bla")
                    emph { +"bold text" }
                    h1 { +"H1" }
                    h2 { +"H2" }
                    h3 { +"H3" }
                    h4 { +"H4" }
                    h5 { +"H5" }
                    checkbox { addOnChangeListener { println("changed to: ${this.checked}")} }
                    br()
                    button(label = { +"Press me" },
                            type = ButtonType.BUTTON,
                            onclick = { println("Check console!") })
                    ul {
                        li { +"List item 1" }
                        li { +"List item 2" }
                        li { +"List item 3" }
                        li { +"List item 4" }
                    }

                    ol {
                        li { +"List item 1" }
                        li { +"List item 2" }
                        li { +"List item 3" }
                        li { +"List item 4" }
                    }

                    dl {
                        clazz = "dl-horizontal"
                        item(dt = { +"Term 1"}) { +"Definition"}
                        item(dt = { +"Term 2"}) { +"Definition"}
                    }

                    kbd { +"cd" }
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content=
"""
a(href="http://www.yested.net") { +"Yested"}
br()
div {
    span { +"Text in span which is in div"}
}
p { +"Text in Paragraph" }
s { +"Strikethrough text" }
nbsp()
del { +"Deleted text" }
nbsp()
mark { +"Marked text" }
nbsp()
ins { +"Inserted text" }
nbsp()
u { +"Underlined text" }
nbsp()
small { +"Small text" }
nbsp()
strong { +"Strong text" }
nbsp()
em { +"Italicized text." }
br()
br()
blockquote { +"blockquote" }
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
checkbox { onchange = { println("changed to: $\{this.checked}")} }
br()
button(label = { +"Press me"},
        type = ButtonType.BUTTON,
        onclick = { println("Check console!")})
ul {
    li { +"List item 1"}
    li { +"List item 2"}
    li { +"List item 3"}
    li { +"List item 4"}
}

ol {
    li { +"List item 1" }
    li { +"List item 2" }
    li { +"List item 3" }
    li { +"List item 4" }
}

dl {
    clazz = "dl-horizontal"
    item(dt = { +"Term 1"}) { +"Definition"}
    item(dt = { +"Term 2"}) { +"Definition"}
}

kbd { +"cd" }

""")
            }
        }
    }

}