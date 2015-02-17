package bootstrap

import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader
import net.yested.Div
import net.yested.bootstrap.navbar
import net.yested.bootstrap.glyphicon
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.NavbarLook
import net.yested.bootstrap.StringInputField

fun createNavbarSection(id: String): Div {

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Navbar" } }
            }
        }
        row {
            col(Medium(6)) {
                div {
                    +"Complete implementation of Twitter Bootstrap Navbar. Please see: "
                    a(href = "http://getbootstrap.com/components/#navbar") { +"http://getbootstrap.com/components/#navbar" }
                    br()
                    br()
                    emph { +"Features:" }
                    ul {
                        li { +"Navbar collapses on mobile screens." }
                        li { +"Once clicked on menu item, it stays selected." }
                        li { +"You can set hrefs of menu items or capture onclick events." }
                    }
                    br()
                    emph { +"Please note!" }
                    ul {
                        li { +"Set correct Bootrsap classes to forms/text you use in header (see in the example below)" }
                        li { +"Keep the order of the elements as specified by Bootstrap" }
                        li { +"Set different IDs if you have multiple navbars in one application" }
                    }
                    br()
                }
            }
            col(Medium(6)) {
                div {
                    emph { +"Navbar Positions (parameter 'position'):" }
                    ul {
                        li { +"Empty - Navbar will render in the current element"}
                        li { +"FIXED_TOP - Navbar will be at the top and always visible"}
                        li { +"FIXED_BOTTOM - Navbar will be at the bottom and always visiblet"}
                        li { +"STATIC_TOP - Navbar will be at the top and will scroll out"}
                    }
                    br()
                    emph { +"Navbar Look (parameter 'look'):" }
                    ul {
                        li { +"DEFAULT - Default look (light)"}
                        li { +"INVERSE - Inversed colours (dark)"}
                    }
                    br()
                    emph { +"Navbar features (DSL functions):" }
                    ul {
                        li { +"brand - Page title/logo (Anchor) (optional, once)"}
                        li { +"item -  Top menu item (Anchor) (optional, many times)"}
                        li { +"dropdown -  Top menu item (Anchor) (optional, many times)"}
                        li { +"left -  Content will be position on the left (after last menu link)"}
                        li { +"right -  Content will be position on the right"}
                    }
                }
            }
        }
        row {
            col(Medium(12)) {
                h4 { +"Demo" }
                navbar(id = "navbarDemo", look = NavbarLook.INVERSE) {
                    brand(href = "#bootstrapComponents") { glyphicon(icon = "home"); nbsp(); +"Home"  }
                    item(href = "#bootstrapComponents") { +"Some Link 1" }
                    item(href = "#bootstrapComponents", onclick = { println("clicked")}) { +"Some Link 2" }
                    dropdown(label = { +"Dropdown"}) {
                        item(href = "#bootstrapComponents", onclick = { println("clicked")}) { +"Link 1" }
                        item(href = "#bootstrapComponents", onclick = { println("clicked")}) { +"Link 2" }
                        divider()
                        item(href = "#bootstrapComponents", onclick = { println("clicked")}) { +"Link 3" }
                    }
                    left {
                        form { "class".."navbar-form"
                            div(clazz = "form-group") {
                                +StringInputField(placeholder = "username")
                            }
                            btsButton(type = ButtonType.SUBMIT, label = { +"Login"}) {}
                        }
                    }
                    right {
                        span(clazz = "navbar-text") {
                            +"On the right1"
                        }
                    }
                }
            }
        }
        row {
            col(Medium(12)) {
                h4 { +"Code" }
                code(lang = "kotlin", content =
"""navbar(id = "navbarDemo", look = NavbarLook.INVERSE) {
    brand(href = "#bootstrapComponents") {glyphicon(icon = "home"); nbsp(); +" Home" }
    item(href = "#bootstrapComponents") { +"Some Link 1" }
    item(href = "#bootstrapComponents", onclick = { println("clicked")}) { +"Some Link 2" }
    dropdown(label = { +"Dropdown"}) {
        item(href = "#bootstrapComponents", onclick = { println("clicked")}) { +"Link 1" }
        item(href = "#bootstrapComponents", onclick = { println("clicked")}) { +"Link 2" }
        divider()
        item(href = "#bootstrapComponents", onclick = { println("clicked")}) { +"Link 3" }
    }
    left {
        form { "class".."navbar-form"
            div(clazz = "form-group") {
                textInput(placeholder = "username") {}
            }
            btsButton(type = ButtonType.SUBMIT, label = { +"Login"}) {}
        }
    }
    right {
        span(clazz = "navbar-text") {
            +"On the right1"
        }
    }
}"""            )
            }
        }
    }
}