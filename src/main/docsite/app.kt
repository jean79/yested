import net.yested.bootstrap.page
import net.yested.bootstrap.CheckBox
import net.yested.bootstrap.btsButton
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize
import net.yested.text
import net.yested.bootstrap.row
import net.yested.bootstrap.Navbar
import net.yested.bootstrap.navbar
import net.yested.registerHashChangeListener
import net.yested.div
import bootstrap.boostrapPage
import basics.basicPage
import html.htmlPage
import ajax.ajaxPage

fun main(args: Array<String>) {

    val navbar = navbar {
        brand(href = "#") { +"Yested" }
        item(href = "#/html") { +"Basic HTML" }
        item(href = "#/bootstrapComponents") { +"Bootstrap" }
        item(href = "#/ajax") { +"Ajax" }
    }

    val divContainer = div {}

    registerHashChangeListener {
        hash ->
        when {
            hash.size() == 1  -> divContainer.replace(basicPage())
            hash.get(1) == "html" -> divContainer.replace(htmlPage())
            hash.get(1) == "bootstrapComponents" -> divContainer.replace(boostrapPage())
            hash.get(1) == "ajax" -> divContainer.replace(ajaxPage())
        }
    }

    page("page") {
        topMenu(navbar)
        content {
            div {
                br(); br();
                +divContainer
            }
        }
        footer {
            small {
                emph { +"Contact: " }
                a(href = "mailto:jan.kovar79@gmail.com") { +"jan.kovar79@gmail.com" }
            }
            br(); br()
        }
    }

}