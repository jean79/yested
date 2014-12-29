package net.yested.bootstrap

import net.yested.ParentComponent
import net.yested.div
import net.yested.HTMLParentComponent
import net.yested.tag
import net.yested.UL
import net.yested.Li
import net.yested.Anchor
import java.util.ArrayList
import net.yested.with
import net.yested.Div
import net.yested.Span

/**
 * Created by jean on 24.11.2014.
 *
 *  <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Bootstrap theme</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Home</a></li>
                    <li><a href="#about">About</a></li>
                    <li><a href="#contact">Contact</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Dropdown <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#">Action</a></li>
                            <li><a href="#">Another action</a></li>
                            <li><a href="#">Something else here</a></li>
                            <li class="divider"></li>
                            <li class="dropdown-header">Nav header</li>
                            <li><a href="#">Separated link</a></li>
                            <li><a href="#">One more separated link</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
  </nav>
 *
 */
public class Navbar() : ParentComponent("nav") {

    private val ul = UL() with { clazz = "nav navbar-nav" }
    private val menuItems = ArrayList<Li>();
    private val brandLink = Anchor();

    {

        setAttribute("class", "navbar navbar-inverse navbar-fixed-top")
        setAttribute("role", "navigation")

        add(
            div(clazz = "container") {
                div(clazz = "navbar-header") {
                    + tag("button") {
                        "type".."button"; "class".."navbar-toggle collapsed";
                        "data-toggle".."collapse"; "data-target".."#navbar"
                        "aria-expanded".."false"; "aria-controls".."navbar"
                        span(clazz = "sr-only") { + "Toogle navigation" }
                        span(clazz = "icon-bar") { }
                        span(clazz = "icon-bar") { }
                        span(clazz = "icon-bar") { }
                    }
                    +brandLink
                }
                div(id = "navbar", clazz = "navbar-collapse collapse") {
                    +ul
                }
            }
        )

    }

    public fun brand(href:String = "/", init: HTMLParentComponent.() -> Unit):Unit {
        brandLink.href(href)
        brandLink.clazz = "navbar-brand"
        brandLink.replace( Span() with { init() })
        brandLink.onclick = {
            deselectAll()
        }
    }

    //public fun logo(init: HTMLParentComponent.() -> Unit):Unit = brandLink.init()

    /**
     * Top menu item
     * <li class="active"><a href="#">Home</a></li>
     */
    public fun item(href:String = "#", onclick: Function0<Unit>? = null, init: Anchor.() -> Unit) {

        val li = Li()

        fun linkClicked() {
            deselectAll()
            li.clazz = "active"
            onclick?.let { onclick!!() }
        }

        li with {
            a(href = href, onclick = ::linkClicked, init = init)
        }
        ul.add(li)
        menuItems.add(li)
    }

    public fun dropdown(label: Anchor.()->Unit, init:NavBarDropdown.()->Unit):Unit =
        ul.add(
            NavBarDropdown({ deselectAll() }, label) with { init() } )

    public fun deselectAll() {
        menuItems.forEach { it.clazz = "" }
    }

}

class NavBarDropdown(val deselectFun:() -> Unit, label: Anchor.()->Unit) : ParentComponent("li") {

    private val ul = UL() with {
        "class".."dropdown-menu"
        "role".."menu"
    }

    {
        setAttribute("class", "dropdown")
        add(
                Anchor(href = "#") with {
                    "class".."dropdown-toggle"
                    "data-toggle".."dropdown"
                    "role".."button"
                    "aria-expanded".."false"
                    label()
                    span(clazz = "caret") { }
                })
        add(ul)
    }

    public fun item(href:String = "#", onclick: Function0<Unit>? = null, init: Anchor.() -> Unit) {
        val li = Li() with {
            a(href = href, onclick = { deselectFun(); onclick?.let { onclick() } }, init = init)
        }
        ul.add(li)
    }

    public fun divider() {
        ul.add(tag("li", { "class".."divider" }))
    }

}

fun navbar(init: Navbar.() -> Unit): Navbar = Navbar() with { init() }
