/**
 * Created by jean on 20.11.2014.
 */
package net.yested

import kotlin.js.dom.html.HTMLElement
import kotlin.js.dom.html.document
import kotlin.dom.addText
import kotlin.dom.first
import jquery.jq
import jquery.JQuery
import org.w3c.dom.Element

public class Attribute {

    public fun get(thisRef: Component?, prop: PropertyMetadata): String =
            thisRef!!.element.getAttribute(prop.name)

    public fun set(thisRef: Component?, prop: PropertyMetadata, value: String): Unit =
            thisRef!!.element.setAttribute(prop.name, value)

}

public trait Component {
    val element: HTMLElement

    public var clazz: String
        get() = element.getAttribute("class")
        set(value) {
            element.setAttribute("class", value)
        }

    public var id: String
        get() = element.getAttribute("id")
        set(value) {
            element.setAttribute("id", value)
        }

    public fun setAttribute(name: String, value: String) {
        element.setAttribute(name, value)
    }

    public fun getAttribute(name: String): String =
            element.getAttribute(name)

    public fun appendChild(component: Component) {
        element.appendChild(component.element)
    }

    public fun appendContent(text: String) {
        element.innerHTML += text
    }

    public fun removeChild(childElementName: String) {
        val child = element.getElementsByTagName(childElementName).first;
        if (child != null) {
            element.removeChild(child)
        }
    }
}

public open class HTMLComponent(tagName: String) : Component {
    override val element = createDocumentHTMLElement(tagName)
}

public fun createDocumentHTMLElement(tagName: String): HTMLElement {
    return document.createElement(tagName) as HTMLElement
}

native fun JQuery.fadeOut(duration: Int, callback: () -> Unit): Unit = noImpl;
native fun JQuery.fadeIn(duration: Int, callback: () -> Unit): Unit = noImpl;

public trait ComponentContainer : Component {
    public fun String.rangeTo(value: String): Unit = element.setAttribute(this, value)

    public fun String.plus(): Unit = appendContent(this)

    public fun Component.plus() {
        this@ComponentContainer.appendChild(this)
    }

    public fun setContent(text: String) {
        element.innerHTML = text
    }

    public fun setChild(component: Component) {
        element.innerHTML = ""
        element.appendChild(component.element)
    }

    public fun fade(component: Component, callback: () -> Unit = {}) {
        jq(element).fadeOut(200) {
            element.innerHTML = ""
            element.appendChild(component.element)
            jq(element).fadeIn(200, callback)
        }
    }

    public var onclick: Function0<Unit>
        get() = element.onclick
        set(f) {
            element.onclick = f;
        }


    public fun a(clazz: String? = null, href: String? = null, onclick: Function0<Unit>? = null, init: Anchor.() -> Unit = {}) {
        val anchor = Anchor()
        if (href != null) {
            anchor.href = href
        }
        if (onclick != null) {
            anchor.onclick = onclick
        }
        if (clazz != null) {
            anchor.clazz = clazz
        }
        anchor.init()
        appendChild(anchor)
    }

    public fun div(id: String? = null, clazz: String = "", init: Div.() -> Unit): Div {
        val div = Div()
        div.init()
        div.clazz = clazz
        if (id != null) {
            div.id = id
        }
        appendChild(div)
        return div
    }

    public fun span(clazz: String? = null, init: Span.() -> Unit): Span {
        val span = Span()
        span.init()
        clazz?.let { span.clazz = clazz!! }
        appendChild(span)
        return span
    }

    public fun img(src: String, alt: String? = null) {
        +(Image() with {
            this.src = src
            this.alt = alt ?: ""
        })
    }

    public fun p(init: P.() -> Unit) {
        val p = P()
        p.init()
        appendChild(p)
    }

    public fun tag(tagName: String, init: ComponentContainer.() -> Unit): ComponentContainer {
        val tag = HTMLComponentContainer(tagName)
        tag.init()
        appendChild(tag)
        return tag
    }

    public fun table(init: Table.() -> Unit) {
        val table = Table()
        table.init()
        appendChild(table)
    }

    public fun button(label: ComponentContainer.() -> Unit, type: ButtonType = ButtonType.BUTTON, onclick: () -> Unit) {
        appendChild(Button(type = type) with {
            label()
            element.onclick = onclick
        })
    }

    public fun code(lang: String? = "javascript", content: String) {
        appendChild(tag("pre") {
            tag("code") {
                +printMarkup(content)
            }
        })
    }

    public fun ul(init: UL.() -> Unit): Unit =
            appendChild(UL() with { init() })


    public fun ol(init: OL.() -> Unit): Unit =
            appendChild(OL() with { init() })

    public fun dl(init: DL.() -> Unit): Unit =
            appendChild(DL() with { init() })

    public fun nbsp(times: Int = 1): Unit =
            (1..times).forEach {
                appendContent("&nbsp;")
            }

    public fun addTag(tagName: String, init: ComponentContainer.() -> Unit): Unit
            = appendChild(tag(tagName) { init() })

    public fun h1(init: ComponentContainer.() -> Unit): Unit = addTag("h1", init)
    public fun h2(init: ComponentContainer.() -> Unit): Unit = addTag("h2", init)
    public fun h3(init: ComponentContainer.() -> Unit): Unit = addTag("h3", init)
    public fun h4(init: ComponentContainer.() -> Unit): Unit = addTag("h4", init)
    public fun h5(init: ComponentContainer.() -> Unit): Unit = addTag("h5", init)

    public fun emph(init: ComponentContainer.() -> Unit): Unit = addTag("strong", init)
    public fun small(init: ComponentContainer.() -> Unit): Unit = addTag("small", init)
    public fun mark(init: ComponentContainer.() -> Unit): Unit = addTag("mark", init)
    public fun del(init: ComponentContainer.() -> Unit): Unit = addTag("del", init)
    public fun s(init: ComponentContainer.() -> Unit): Unit = addTag("s", init)
    public fun ins(init: ComponentContainer.() -> Unit): Unit = addTag("ins", init)
    public fun u(init: ComponentContainer.() -> Unit): Unit = addTag("u", init)
    public fun strong(init: ComponentContainer.() -> Unit): Unit = addTag("strong", init)
    public fun em(init: ComponentContainer.() -> Unit): Unit = addTag("em", init)
    public fun b(init: ComponentContainer.() -> Unit): Unit = addTag("b", init)
    public fun i(init: ComponentContainer.() -> Unit): Unit = addTag("i", init)
    public fun kbd(init: ComponentContainer.() -> Unit): Unit = addTag("kbd", init)
    public fun variable(init: ComponentContainer.() -> Unit): Unit = addTag("var", init)
    public fun samp(init: ComponentContainer.() -> Unit): Unit = addTag("samp", init)
    public fun blockquote(init: ComponentContainer.() -> Unit): Unit = addTag("blockquote", init)
    public fun form(init: ComponentContainer.() -> Unit): Unit = addTag("form", init)

    public fun textArea(rows: Int = 3, init: ComponentContainer.() -> Unit): Unit =
            appendChild(HTMLComponentContainer("textarea") with {
                setAttribute("rows", rows.toString())
                init()
            })

    public fun abbr(title: String, init: ComponentContainer.() -> Unit): Unit {
        appendChild(HTMLComponentContainer("abbr") with {
            setAttribute("title", title)
            init()
        })
    }

    public fun br(): Unit = addTag("br") { }

    public fun label(forId: String? = null, clazz: String? = null, init: ComponentContainer.() -> Unit): ComponentContainer {
        val l = HTMLComponentContainer("label") with {
            forId?.let { "for"..forId!! }
            clazz?.let { "class"..clazz!! }
            init()
        }
        appendChild(l)
        return l
    }
}

public open class HTMLComponentContainer(tagName: String) : HTMLComponent(tagName), ComponentContainer {

    public var role: String by Attribute()
    public var style: String by Attribute()
}

public class Table : HTMLComponent("table") {

    public var border: String by Attribute()

    public fun thead(init: THead.() -> Unit) {
        val thead = THead()
        thead.init()
        appendChild(thead)
    }

    public fun tbody(init: TBody.() -> Unit) {
        val tbody = TBody()
        tbody.init()
        appendChild(tbody)
    }

}

public class THead : HTMLComponent("thead") {

    public fun tr(init: TRHead.() -> Unit) {
        val tr = TRHead()
        tr.init()
        appendChild(tr)
    }

}

public class TBody : HTMLComponent("tbody") {

    public fun tr(init: TRBody.() -> Unit) {
        val tr = TRBody()
        tr.init()
        appendChild(tr)
    }

}

public class TRHead : HTMLComponent("tr") {

    public fun th(init: ComponentContainer.() -> Unit): ComponentContainer {
        val th = HTMLComponentContainer("th")
        th.init()
        appendChild(th)
        return th
    }

}

public class TRBody : HTMLComponent("tr") {

    public fun td(init: ComponentContainer.() -> Unit) {
        val td = HTMLComponentContainer("td")
        td.init()
        appendChild(td)
    }

}

public class OL : HTMLComponentContainer("ol") {

    public fun li(init: Li.() -> Unit): Li {
        val li = Li()
        li.init()
        appendChild(li)
        return li
    }

}

public class UL : HTMLComponentContainer("ul") {

    public fun li(init: Li.() -> Unit): Li {
        val li = Li()
        li.init()
        appendChild(li)
        return li
    }

}

public class DL : HTMLComponentContainer("dl") {

    public fun item(dt: ComponentContainer.() -> Unit, dd: ComponentContainer.() -> Unit) {
        appendChild(tag("dt", dt))
        appendChild(tag("dd", dd))
    }

}


native trait Context {}

native trait CanvasI {
    public fun getContext(id: String): Context = noImpl
}

open class Canvas(val width: Int, val height: Int) : HTMLComponentContainer("canvas") {
    {
        setAttribute("width", "${width}")
        setAttribute("height", "${height}")
    }
    fun getContext(id: String): Context = (element as CanvasI).getContext(id)
}

public open class Div : HTMLComponentContainer("div") {}

public open class Span : HTMLComponentContainer("span") {}

public enum class ButtonType(val code: String) {
    BUTTON : ButtonType("button")
    SUBMIT : ButtonType("submit")
    RESET : ButtonType("reset")
}

public class Button(type: ButtonType = ButtonType.BUTTON) : HTMLComponent("button") {

    {
        setAttribute("type", type.code)
    }
}

public class Image : HTMLComponent("img") {

    public var src: String by Attribute()
    public var alt: String by Attribute()

}

public class P : HTMLComponentContainer("p") {}

public open class Li : HTMLComponentContainer("li") {}

public open class Anchor() : HTMLComponentContainer("a") {

    public var href: String by Attribute()

}

public fun div(id: String? = null, clazz: String? = null, init: Div.() -> Unit): Div {
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

public fun thead(init: THead.() -> Unit): THead {
    val thead = THead()
    thead.init()
    return thead
}

public fun tbody(init: TBody.() -> Unit): TBody {
    val tbody = TBody()
    tbody.init()
    return tbody
}

public fun tag(tagName: String, init: ComponentContainer.() -> Unit): ComponentContainer =
        HTMLComponentContainer(tagName) with {
            init()
        }

public fun text(text: String): ComponentContainer.() -> Unit = {
    +text
}
