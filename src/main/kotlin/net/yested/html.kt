/**
 * Created by jean on 20.11.2014.
 */
package net.yested

import kotlin.js.dom.html.HTMLElement
import kotlin.js.dom.html.document
import kotlin.dom.addText
import kotlin.dom.first

class Attribute {

    fun get(thisRef: Component?, prop: PropertyMetadata):String =
            thisRef!!.element.getAttribute(prop.name)

    fun set(thisRef: Component?, prop: PropertyMetadata, value: String):Unit =
            thisRef!!.element.setAttribute(prop.name, value)

}

trait Component {
    val element : HTMLElement
}

open class ParentComponent(tagName:String) : Component {

    override val element = document.createElement(tagName) as HTMLElement

    fun setAttribute(name:String, value:String) {
        element.setAttribute(name, value)
    }

    fun getAttribute(name:String) =
        element.getAttribute(name)

    fun add(component:Component) {
        element.appendChild(component.element)
    }

    fun add(text:String) {
        element.innerHTML += text
    }

    fun String.plus() = add(this)

    fun Component.plus() {
        add(this)
    }

    fun replace(text:String) {
        element.innerHTML = text
    }

    fun replace(component:Component) {
        element.innerHTML = ""
        element.appendChild(component.element)
    }

    fun removeChild(childElementName:String) {
        val child = element.getElementsByTagName(childElementName).first;
        if (child != null) {
            element.removeChild(child)
        }
    }

}

open class HTMLParentComponent(tagName:String) : ParentComponent(tagName) {

    var id:String by Attribute()
    var role:String by Attribute()
    var style:String by Attribute()

    var clazz:String
        get() = element.getAttribute("class")
        set(value) {
            element.setAttribute("class", value)
        }

    fun String.rangeTo(value:String) = element.setAttribute(this, value)

    var onclick: Function0<Unit>
        get() = element.onclick
        set(f) {
            element.onclick = f;
        }

    open fun a(clazz:String? = null, href:String?=null, onclick:Function0<Unit>? = null, init:Anchor.() -> Unit) {
        val anchor = Anchor(href)
        if (onclick != null) {
            anchor.onclick = onclick
        }
        if (clazz != null) { anchor.clazz = clazz }
        anchor.init()
        add(anchor)
    }

    fun div(id:String? = null, clazz:String = "", init:Div.() -> Unit):Div {
        val div = Div()
        div.init()
        div.clazz = clazz
        if (id != null) { div.id = id }
        add(div)
        return div
    }

    fun span(clazz:String? = null, init:Span.() -> Unit):Span {
        val span = Span()
        span.init()
        clazz?.let { span.clazz = clazz!! }
        add(span)
        return span
    }

    fun img(src:String, alt:String? = null, init: Image.() -> Unit) {
        +(Image() with {
            this.src = src
            this.alt = alt?:""
            this.init()
        })
    }

    fun p(init:P.() -> Unit) {
        val p = P()
        p.init()
        add(p)
    }

    fun tag(tagName:String, init: HTMLParentComponent.() -> Unit): HTMLParentComponent {
        val tag = HTMLParentComponent(tagName)
        tag.init()
        add(tag)
        return tag
    }

    fun table(init:Table.() -> Unit) {
        val table = Table()
        table.init()
        add(table)
    }

    fun button(label:ParentComponent.() -> Unit, type: ButtonType = ButtonType.BUTTON, onclick:() -> Unit) {
        add(Button(type = type) with {
            label()
            element.onclick = onclick
        })
    }

    fun code(lang:String? = "javascript", init: ParentComponent.() -> Unit) {
        add(tag("pre") {
            tag("code") {
                init()
            }
        })
    }
    fun ul(init:UL.() -> Unit) {
        val ul = UL()
        ul.init()
        add(ul)
    }

    fun nbsp(times:Int = 1):Unit =
        (1..times).forEach {
            add("&nbsp;")
        }

    private fun addTag(tagName:String, init: HTMLParentComponent.() -> Unit)
            = add( tag(tagName) { init() })

    fun h1(init: HTMLParentComponent.() -> Unit) = addTag("h1", init)
    fun h2(init: HTMLParentComponent.() -> Unit) = addTag("h2", init)
    fun h3(init: HTMLParentComponent.() -> Unit) = addTag("h3", init)
    fun h4(init: HTMLParentComponent.() -> Unit) = addTag("h4", init)
    fun h5(init: HTMLParentComponent.() -> Unit) = addTag("h5", init)

    fun emph(init:HTMLParentComponent.() -> Unit) = addTag("strong", init)

    fun br() = addTag("br") { }

    fun label(forId:String? = null, clazz:String? = null, init:HTMLParentComponent.() -> Unit):HTMLParentComponent {
        val l = HTMLParentComponent("label") with {
            forId?.let { "for"..forId!! }
            clazz?.let { "class"..clazz!! }
            init()
        }
        add(l)
        return l
    }

}

class Table : ParentComponent("table") {

    var border:String by Attribute()

    fun thead(init:THead.() -> Unit) {
        val thead = THead()
        thead.init()
        add(thead)
    }

    fun tbody(init:TBody.() -> Unit) {
        val tbody = TBody()
        tbody.init()
        add(tbody)
    }

}

class THead : ParentComponent("thead") {

    fun tr(init:TRHead.() -> Unit) {
        val tr = TRHead()
        tr.init()
        add(tr)
    }

}

class TBody : ParentComponent("tbody") {

    fun tr(init:TRBody.() -> Unit) {
        val tr = TRBody()
        tr.init()
        add(tr)
    }

}

class TRHead : ParentComponent("tr") {

    fun th(init:HTMLParentComponent.() -> Unit):HTMLParentComponent {
        val th = HTMLParentComponent("th")
        th.init()
        add(th)
        return th
    }

}

class TRBody : ParentComponent("tr") {

    fun td(init:HTMLParentComponent.() -> Unit) {
        val td = HTMLParentComponent("td")
        td.init()
        add(td)
    }

}

class UL : HTMLParentComponent("ul") {

    fun li(init:Li.() -> Unit):Li {
        val li = Li()
        li.init()
        add(li)
        return li
    }

}

native trait Context { }

native trait CanvasI {
    public fun getContext(id:String):Context = noImpl
}

open class Canvas(val width:Int, val height:Int) : HTMLParentComponent("canvas") {
    {
        setAttribute("width", "${width}")
        setAttribute("height", "${height}")
    }
    fun getContext(id:String):Context = (element as CanvasI).getContext(id)
}

open class Div : HTMLParentComponent("div") { }

open class Span : HTMLParentComponent("span") { }

enum class ButtonType(val code:String) {
    BUTTON : ButtonType("button")
    SUBMIT : ButtonType("submit")
    RESET : ButtonType("reset")
}

class Button(type:ButtonType = ButtonType.BUTTON) : ParentComponent("button") {

    var onclick: Function0<Unit>
        get() = element.onclick
        set(f) {
            element.onclick = f;
        }

    {
        setAttribute("type", type.code)
    }
}

class Image : ParentComponent("img") {

    var src:String by Attribute()
    var alt:String by Attribute()

}

class P : HTMLParentComponent("p") { }

class Li : HTMLParentComponent("li") { }

open class Anchor(href:String? = null) : HTMLParentComponent("a") {

    {
        if (href != null) {
            href(href)
        }
    }

    fun href(url:String) {
        element.setAttribute("href", url)
    }

}

fun div(id:String? = null, clazz:String? = null, init:Div.() -> Unit):Div {
    val div = Div()
    div.init()
    if (clazz != null) {
        div.clazz = clazz
    }
    if (id != null) {
        div.id = id;
    }
    return div
}

fun thead(init:THead.() -> Unit):THead {
    val thead = THead()
    thead.init()
    return thead
}

fun tbody(init:TBody.() -> Unit):TBody {
    val tbody = TBody()
    tbody.init()
    return tbody
}

fun tag(tagName:String, init: HTMLParentComponent.() -> Unit): HTMLParentComponent =
    HTMLParentComponent(tagName) with {
        init()
    }

fun text(text:String): ParentComponent.() -> Unit = {
    +text
}