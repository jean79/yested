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

    public fun get(thisRef: Component?, prop: PropertyMetadata):String =
            thisRef!!.element.getAttribute(prop.name)

    public fun set(thisRef: Component?, prop: PropertyMetadata, value: String):Unit =
            thisRef!!.element.setAttribute(prop.name, value)

}

public trait Component {
    val element : HTMLElement
}

public open class ParentComponent(tagName:String) : Component {

    override val element = document.createElement(tagName) as HTMLElement

    protected fun setAttribute(name:String, value:String) {
        element.setAttribute(name, value)
    }

    protected fun getAttribute(name:String):String =
        element.getAttribute(name)

    open protected fun add(component:Component) {
        element.appendChild(component.element)
    }

    open protected fun add(text:String) {
        element.innerHTML += text
    }

    protected fun removeChild(childElementName:String) {
        val child = element.getElementsByTagName(childElementName).first;
        if (child != null) {
            element.removeChild(child)
        }
    }

}

native fun JQuery.fadeOut(duration:Int, callback:()->Unit) :Unit = noImpl;
native fun JQuery.fadeIn(duration:Int, callback:()->Unit) :Unit = noImpl;

public open class HTMLParentComponent(tagName:String) : ParentComponent(tagName) {

    public var id:String by Attribute()
    public var role:String by Attribute()
    public var style:String by Attribute()

    public var clazz:String
        get() = element.getAttribute("class")
        set(value) {
            element.setAttribute("class", value)
        }

    public fun String.rangeTo(value:String):Unit = element.setAttribute(this, value)

    public fun String.plus(): Unit = add(this)

    public fun Component.plus() {
        add(this)
    }

    override public fun add(component: Component) {
        super.add(component)
    }

    override public fun add(text: String) {
        super.add(text)
    }

    public fun replace(text:String) {
        element.innerHTML = text
    }

    public fun replace(component:Component) {
        element.innerHTML = ""
        element.appendChild(component.element)
    }

    public fun fade(component:Component) {
        jq(element).fadeOut(200) {
            element.innerHTML = ""
            element.appendChild(component.element)
            jq(element).fadeIn(200, {})
        }
    }

    public var onclick: Function0<Unit>
        get() = element.onclick
        set(f) {
            element.onclick = f;
        }


    open public fun a(clazz:String? = null, href:String?=null, onclick:Function0<Unit>? = null, init:Anchor.() -> Unit) {
        val anchor = Anchor()
        if (href != null) {
            anchor.href = href
        }
        if (onclick != null) {
            anchor.onclick = onclick
        }
        if (clazz != null) { anchor.clazz = clazz }
        anchor.init()
        add(anchor)
    }

    public fun div(id:String? = null, clazz:String = "", init:Div.() -> Unit):Div {
        val div = Div()
        div.init()
        div.clazz = clazz
        if (id != null) { div.id = id }
        add(div)
        return div
    }

    public fun span(clazz:String? = null, init:Span.() -> Unit):Span {
        val span = Span()
        span.init()
        clazz?.let { span.clazz = clazz!! }
        add(span)
        return span
    }

    public fun img(src:String, alt:String? = null) {
        +(Image() with {
            this.src = src
            this.alt = alt?:""
        })
    }

    public fun p(init:P.() -> Unit) {
        val p = P()
        p.init()
        add(p)
    }

    public fun tag(tagName:String, init: HTMLParentComponent.() -> Unit): HTMLParentComponent {
        val tag = HTMLParentComponent(tagName)
        tag.init()
        add(tag)
        return tag
    }

    public fun table(init:Table.() -> Unit) {
        val table = Table()
        table.init()
        add(table)
    }

    public fun button(label:HTMLParentComponent.() -> Unit, type: ButtonType = ButtonType.BUTTON, onclick:() -> Unit) {
        add(Button(type = type) with {
            label()
            element.onclick = onclick
        })
    }

    public fun code(lang:String? = "javascript", content : String) {
        add(tag("pre") {
            tag("code") {
                +printMarkup(content)
            }
        })
    }

    public fun ul(init:UL.() -> Unit): Unit =
        add( UL() with { init() })


    public fun ol(init:OL.() -> Unit):Unit =
        add( OL() with { init() })

    public fun dl(init:DL.() -> Unit):Unit =
            add( DL() with { init() })

    public fun nbsp(times:Int = 1):Unit =
        (1..times).forEach {
            add("&nbsp;")
        }

    public fun addTag(tagName:String, init: HTMLParentComponent.() -> Unit): Unit
            = add( tag(tagName) { init() })

    public fun h1(init: HTMLParentComponent.() -> Unit): Unit = addTag("h1", init)
    public fun h2(init: HTMLParentComponent.() -> Unit): Unit = addTag("h2", init)
    public fun h3(init: HTMLParentComponent.() -> Unit): Unit = addTag("h3", init)
    public fun h4(init: HTMLParentComponent.() -> Unit): Unit = addTag("h4", init)
    public fun h5(init: HTMLParentComponent.() -> Unit): Unit = addTag("h5", init)

    public fun emph(init:HTMLParentComponent.() -> Unit): Unit = addTag("strong", init)
    public fun small(init: HTMLParentComponent.() -> Unit): Unit = addTag("small", init)
    public fun mark(init: HTMLParentComponent.() -> Unit): Unit = addTag("mark", init)
    public fun del(init: HTMLParentComponent.() -> Unit): Unit = addTag("del", init)
    public fun s(init: HTMLParentComponent.() -> Unit): Unit = addTag("s", init)
    public fun ins(init: HTMLParentComponent.() -> Unit): Unit = addTag("ins", init)
    public fun u(init: HTMLParentComponent.() -> Unit): Unit = addTag("u", init)
    public fun strong(init: HTMLParentComponent.() -> Unit): Unit = addTag("strong", init)
    public fun em(init: HTMLParentComponent.() -> Unit): Unit = addTag("em", init)
    public fun b(init: HTMLParentComponent.() -> Unit): Unit = addTag("b", init)
    public fun i(init: HTMLParentComponent.() -> Unit): Unit = addTag("i", init)
    public fun kbd(init: HTMLParentComponent.() -> Unit): Unit = addTag("kbd", init)
    public fun variable(init: HTMLParentComponent.() -> Unit): Unit = addTag("var", init)
    public fun samp(init: HTMLParentComponent.() -> Unit): Unit = addTag("samp", init)
    public fun blockquote(init: HTMLParentComponent.() -> Unit): Unit = addTag("blockquote", init)

    public fun textArea(rows:Int = 3, init:HTMLParentComponent.() ->Unit): Unit =
        add(HTMLParentComponent("textarea") with {
                setAttribute("rows", rows.toString())
                init()
            })

    public fun abbr(title:String, init: HTMLParentComponent.() -> Unit): Unit {
        add(HTMLParentComponent("abbr") with {
            setAttribute("title", title)
            init()
        })
    }

    public fun br(): Unit = addTag("br") { }

    public fun label(forId:String? = null, clazz:String? = null, init:HTMLParentComponent.() -> Unit):HTMLParentComponent {
        val l = HTMLParentComponent("label") with {
            forId?.let { "for"..forId!! }
            clazz?.let { "class"..clazz!! }
            init()
        }
        add(l)
        return l
    }

}

public class Table : ParentComponent("table") {

    public var border:String by Attribute()

    public fun thead(init:THead.() -> Unit) {
        val thead = THead()
        thead.init()
        add(thead)
    }

    public fun tbody(init:TBody.() -> Unit) {
        val tbody = TBody()
        tbody.init()
        add(tbody)
    }

}

public class THead : ParentComponent("thead") {

    public fun tr(init:TRHead.() -> Unit) {
        val tr = TRHead()
        tr.init()
        add(tr)
    }

}

public class TBody : ParentComponent("tbody") {

    public fun tr(init:TRBody.() -> Unit) {
        val tr = TRBody()
        tr.init()
        add(tr)
    }

}

public class TRHead : ParentComponent("tr") {

    public fun th(init:HTMLParentComponent.() -> Unit):HTMLParentComponent {
        val th = HTMLParentComponent("th")
        th.init()
        add(th)
        return th
    }

}

public class TRBody : ParentComponent("tr") {

    public fun td(init:HTMLParentComponent.() -> Unit) {
        val td = HTMLParentComponent("td")
        td.init()
        add(td)
    }

}

public class OL : HTMLParentComponent("ol") {

    public fun li(init:Li.() -> Unit):Li {
        val li = Li()
        li.init()
        add(li)
        return li
    }

}

public class UL : HTMLParentComponent("ul") {

    public fun li(init:Li.() -> Unit):Li {
        val li = Li()
        li.init()
        add(li)
        return li
    }

}

public class DL : HTMLParentComponent("dl") {

    public fun item(dt : HTMLParentComponent.() -> Unit, dd : HTMLParentComponent.() -> Unit) {
        add(tag("dt", dt))
        add(tag("dd", dd))
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

public open class Div : HTMLParentComponent("div") { }

public open class Span : HTMLParentComponent("span") { }

public enum class ButtonType(val code:String) {
    BUTTON : ButtonType("button")
    SUBMIT : ButtonType("submit")
    RESET : ButtonType("reset")
}

public class Button(type:ButtonType = ButtonType.BUTTON) : HTMLParentComponent("button") {

    {
        setAttribute("type", type.code)
    }
}

public class Image : ParentComponent("img") {

    public var src:String by Attribute()
    public var alt:String by Attribute()

}

public class P : HTMLParentComponent("p") { }

public open class Li : HTMLParentComponent("li") { }

public open class Anchor() : HTMLParentComponent("a") {

    public var href : String by Attribute()

}

public fun div(id:String? = null, clazz:String? = null, init:Div.() -> Unit):Div {
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

public fun thead(init:THead.() -> Unit):THead {
    val thead = THead()
    thead.init()
    return thead
}

public fun tbody(init:TBody.() -> Unit):TBody {
    val tbody = TBody()
    tbody.init()
    return tbody
}

public fun tag(tagName:String, init: HTMLParentComponent.() -> Unit): HTMLParentComponent =
    HTMLParentComponent(tagName) with {
        init()
    }

public fun text(text:String): HTMLParentComponent.() -> Unit = {
    +text
}
