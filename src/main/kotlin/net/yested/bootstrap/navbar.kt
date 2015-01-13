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

public enum class NavbarPosition(val code:String) {
    STATIC_TOP : NavbarPosition("static-top")
    FIXED_TOP : NavbarPosition("fixed-top")
    FIXED_BOTTOM : NavbarPosition("fixed-bottom")
}

public enum class NavbarLook(val code:String) {
    DEFAULT : NavbarLook("default")
    INVERSE : NavbarLook("inverse")
}

public class Navbar(id:String = "navbar", position: NavbarPosition? = null, look:NavbarLook = NavbarLook.DEFAULT) : ParentComponent("nav") {

    private val ul = UL() with { clazz = "nav navbar-nav" }
    private val collapsible = div(id = id, clazz = "navbar-collapse collapse") { +ul }

    private val menuItems = ArrayList<Li>();
    private val brandLink = Anchor();

    {

        setAttribute("class", "navbar navbar-${look.code} ${if (position != null) "navbar-${position.code}" else ""}")
        setAttribute("role", "navigation")

        add(
            div(clazz = "container") {
                div(clazz = "navbar-header") {
                    + tag("button") {
                        "type".."button"; "class".."navbar-toggle collapsed";
                        "data-toggle".."collapse"; "data-target".."#${id}"
                        "aria-expanded".."false"; "aria-controls".."navbar"
                        span(clazz = "sr-only") { + "Toogle navigation" }
                        span(clazz = "icon-bar") { }
                        span(clazz = "icon-bar") { }
                        span(clazz = "icon-bar") { }
                    }
                    +brandLink
                }
                +collapsible
            }
        )

    }

    public fun brand(href:String = "/", init: HTMLParentComponent.() -> Unit):Unit {
        brandLink.href = href
        brandLink.clazz = "navbar-brand"
        brandLink.replace( Span() with { init() })
        brandLink.onclick = {
            deselectAll()
        }
    }

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

    public fun dropdown(label: Anchor.()->Unit, init:NavBarDropdown.()->Unit):Unit {
        val dropdown = NavBarDropdown({ deselectAll() }, label) with { init() }
        ul.add(dropdown)
        menuItems.add(dropdown)
    }

    public fun deselectAll() {
        menuItems.forEach { it.clazz = "" }
    }

    public fun left(init : Div.()->Unit) {
        collapsible.add(div(clazz = "navbar-left") { init() })
    }

    public fun right(init : Div.()->Unit) {
        collapsible.add(div(clazz = "navbar-right") { init() })
    }

}

class NavBarDropdown(private val deselectFun:() -> Unit, label: Anchor.()->Unit) : Li() {

    private val ul = UL() with {
        "class".."dropdown-menu"
        "role".."menu"
    }

    {
        setAttribute("class", "dropdown")
        add(
                Anchor() with {
                    "class".."dropdown-toggle"
                    "data-toggle".."dropdown"
                    "role".."button"
                    "aria-expanded".."false"
                    href = "#"
                    label()
                    span(clazz = "caret") { }
                })
        add(ul)
    }

    fun selectThis() {
        deselectFun();
        setAttribute("class", "dropdown active");
    }

    public fun item(href:String = "#", onclick: Function0<Unit>? = null, init: Anchor.() -> Unit) {
        val li = Li() with {
            a(href = href, onclick = { selectThis(); onclick?.let { onclick() } }, init = init)
        }
        ul.add(li)
    }

    public fun divider() {
        ul.add(tag("li", { "class".."divider" }))
    }

}

public fun HTMLParentComponent.navbar(id:String = "navbar", position: NavbarPosition? = null, look:NavbarLook = NavbarLook.DEFAULT, init: Navbar.() -> Unit):Unit {
    add(Navbar(id = id, position = position, look = look) with { init() })
}
