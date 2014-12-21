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

fun main(args: Array<String>) {

    val navbar = navbar {
        logo { a(clazz = "navbar-brand", href="#") {+"Yested"} }
        item(href = "#/basicComponents") { +"Basic components" }
        item(href = "#/bootstrapComponents") { +"Bootstrap" }
    }

    val divContainer = div {}

    registerHashChangeListener {
        hash ->
        println("hash: ${hash} ${hash.get(1)}")
        when {
            hash.get(0) == "" -> divContainer.replace(div() { +"uvodni stranka"})
            hash.get(1) == "basicComponents" -> divContainer.replace(basicPage())
            hash.get(1) == "bootstrapComponents" -> divContainer.replace(boostrapPage())
        }
    }

    page("page") {
        topMenu(navbar)
        content {
            div {
                br(); br(); br(); br();
                +divContainer
            }
        }
    }

}