package net.yested

import kotlin.js.dom.html.HTMLElement
import kotlin.js.dom.html.document
import kotlin.dom.addText
import kotlin.dom.first
import jquery.jq
import jquery.JQuery
import org.w3c.dom.Element
import net.yested.bootstrap.InputElement
import java.util.ArrayList
import kotlin.js.dom.html.HTMLInputElement
import org.w3c.dom.Node
import kotlin.js.dom.html.HTMLTextAreaElement

public class Attribute(val attributeName:String? = null, val element:HTMLElement? = null) {

    private fun getElement(thisRef: Component?) = element ?: thisRef!!.element

    public fun get(thisRef: Component?, prop: PropertyMetadata):String =
            getElement(thisRef).getAttribute(attributeName ?: prop.name)

    public fun set(thisRef: Component?, prop: PropertyMetadata, value: String):Unit =
            getElement(thisRef).setAttribute(attributeName ?: prop.name, value)

}

public class BooleanAttribute(val attributeName:String? = null, val element:HTMLElement? = null) {

    private fun getElement(thisRef: Component?) = element ?: thisRef!!.element

    public fun get(thisRef: Component?, prop: PropertyMetadata):Boolean =
            getElement(thisRef).getAttribute(attributeName ?: prop.name) == "true"

    public fun set(thisRef: Component?, prop: PropertyMetadata, value: Boolean):Unit =
            getElement(thisRef).setAttribute(attributeName ?: prop.name, if (value) "true" else "false")

}

public trait Component {
    val element : HTMLElement
}

public fun createElement(name:String): HTMLElement = document.createElement(name) as HTMLElement

public fun HTMLElement.appendComponent(component:Component):Unit {
    this.appendChild(component.element)
}

public fun HTMLElement.removeChildByName(childElementName:String) {
    val child = this.getElementsByTagName(childElementName).first;
    if (child != null) {
        this.removeChild(child)
    }
}

public trait ElementEvents {

    val element : HTMLElement

    public var onblur:Function0<Unit>
        get() = element.onblur
        set(value) { element.onblur = value}

    public var onclick:Function0<Unit>
        get() = element.onclick
        set(value) { element.onclick = value}

    public var ondblclick:Function0<Unit>
        get() = element.ondblclick
        set(value) { element.ondblclick = value}

    public var onfocus:Function0<Unit>
        get() = element.onfocus
        set(value) { element.onfocus = value}

    public var onkeydown:Function0<Unit>
        get() = element.onkeydown
        set(value) { element.onkeydown = value}

    public var onkeyup:Function0<Unit>
        get() = element.onkeyup
        set(value) { element.onkeyup = value}

    public var onmouseup:Function0<Unit>
        get() = element.onmouseup
        set(value) { element.onmouseup = value}

    public var onmousedown:Function0<Unit>
        get() = element.onmousedown
        set(value) { element.onmousedown = value}

    public var onmouseout:Function0<Unit>
        get() = element.onmouseout
        set(value) { element.onmouseout = value}

    public var onmouseover:Function0<Unit>
        get() = element.onmouseover
        set(value) { element.onmouseover = value}

    public var onmousemove:Function0<Unit>
        get() = element.onmousemove
        set(value) { element.onmousemove = value}

    public var onresize:Function0<Unit>
        get() = element.onresize
        set(value) { element.onresize = value}

}

public open class HTMLComponent(tagName:String) : Component, ElementEvents {

    override public var element = createElement(tagName)

    public var role:String by Attribute()
    public var style:String by Attribute()
    public var id:String by Attribute()
    public var clazz:String by Attribute("class")

    public fun String.rangeTo(value:String):Unit = element.setAttribute(this, value)

    public fun String.plus(): Unit {
        jq(element).append(this)
    }

    public fun Component.plus() {
        this@HTMLComponent.appendChild(this)
    }

    public fun appendChild(component: Component) {
        element.appendComponent(component)
    }

    public fun setContent(text:String) {
        jq(element).text(text)
    }

    public fun setChild(component:Component) {
        removeAllChildren()
        element.appendChild(component.element)
    }

    public fun removeAllChildren() {
        (0..(element.childNodes.length - 1)).forEach {
            element.removeChild(element.childNodes.item(it))
        }
    }

    public fun setChild(content:Component, effect:BiDirectionEffect, callback:Function0<Unit>? = null) {
        effect.applyOut(this) {
            setChild(content)
            effect.applyIn(this) {
                callback?.invoke()
            }
        }
    }

    open public fun a(clazz:String? = null, target:String? = null, href:String?=null, onclick:Function0<Unit>? = null, init:Anchor.() -> Unit = {}) {
        val anchor = Anchor()
        if (href != null) {
            anchor.href = href
        }
        if (onclick != null) {
            anchor.onclick = onclick
        }
        if (target != null) {
            anchor.target = target
        }
        if (clazz != null) { anchor.clazz = clazz }
        anchor.init()
        element.appendComponent(anchor)
    }

    public fun div(id:String? = null, clazz:String = "", init:Div.() -> Unit):Div {
        val div = Div()
        div.init()
        div.clazz = clazz
        if (id != null) { div.id = id }
        element.appendComponent(div)
        return div
    }

    public fun span(clazz:String? = null, init:Span.() -> Unit = {}):Span {
        val span = Span()
        span.init()
        clazz?.let { span.clazz = clazz!! }
        element.appendComponent(span)
        return span
    }

    public fun img(src:String, alt:String? = null) {
        +(Image() with {
            this.src = src
            this.alt = alt?:""
        })
    }

    public fun p(init:P.() -> Unit) {
        +(P() with { init() })
    }

    public fun tag(tagName:String, init: HTMLComponent.() -> Unit): Unit {
        +(HTMLComponent(tagName) with { init() } )
    }

    public fun table(init:Table.() -> Unit) {
        +(Table() with { init() })
    }

    public fun checkbox(init:CheckBox.() -> Unit) {
        +(CheckBox() with { init() })
    }

    public fun button(label: HTMLComponent.() -> Unit, type: ButtonType = ButtonType.BUTTON, onclick:() -> Unit) {
        +(Button(type = type) with {
            label()
            element.onclick = onclick
        })
    }

    public fun code(lang:String? = "javascript", content : String) {
        tag("pre") {
            tag("code") {
                +printMarkup(content)
            }
        }
    }

    public fun ul(init:UL.() -> Unit): Unit =
        +( UL() with { init() })


    public fun ol(init:OL.() -> Unit):Unit =
        +( OL() with { init() })

    public fun dl(init:DL.() -> Unit):Unit =
            +( DL() with { init() })

    public fun nbsp(times:Int = 1):Unit =
        (1..times).forEach {
            +("&nbsp;")
        }

    public fun h1(init: HTMLComponent.() -> Unit): Unit = tag("h1", init)
    public fun h2(init: HTMLComponent.() -> Unit): Unit = tag("h2", init)
    public fun h3(init: HTMLComponent.() -> Unit): Unit = tag("h3", init)
    public fun h4(init: HTMLComponent.() -> Unit): Unit = tag("h4", init)
    public fun h5(init: HTMLComponent.() -> Unit): Unit = tag("h5", init)

    public fun emph(init: HTMLComponent.() -> Unit): Unit = tag("strong", init)
    public fun small(init: HTMLComponent.() -> Unit): Unit = tag("small", init)
    public fun mark(init: HTMLComponent.() -> Unit): Unit = tag("mark", init)
    public fun del(init: HTMLComponent.() -> Unit): Unit = tag("del", init)
    public fun s(init: HTMLComponent.() -> Unit): Unit = tag("s", init)
    public fun ins(init: HTMLComponent.() -> Unit): Unit = tag("ins", init)
    public fun u(init: HTMLComponent.() -> Unit): Unit = tag("u", init)
    public fun strong(init: HTMLComponent.() -> Unit): Unit = tag("strong", init)
    public fun em(init: HTMLComponent.() -> Unit): Unit = tag("em", init)
    public fun b(init: HTMLComponent.() -> Unit): Unit = tag("b", init)
    public fun i(init: HTMLComponent.() -> Unit): Unit = tag("i", init)
    public fun kbd(init: HTMLComponent.() -> Unit): Unit = tag("kbd", init)
    public fun variable(init: HTMLComponent.() -> Unit): Unit = tag("var", init)
    public fun samp(init: HTMLComponent.() -> Unit): Unit = tag("samp", init)
    public fun blockquote(init: HTMLComponent.() -> Unit): Unit = tag("blockquote", init)
    public fun form(init: HTMLComponent.() -> Unit): Unit = tag("form", init)

    public fun textArea(rows:Int = 3, init: TextArea.() ->Unit): Unit =
        +(TextArea(rows = rows) with {
                init()
            })

    public fun abbr(title:String, init: HTMLComponent.() -> Unit): Unit {
        +(HTMLComponent("abbr") with {
            element.setAttribute("title", title)
            init()
        })
    }

    public fun br(): Unit = tag("br") { }

    public fun label(forId:String? = null, clazz:String? = null, init: HTMLComponent.() -> Unit): HTMLComponent {
        val l = HTMLComponent("label") with {
            forId?.let { "for"..forId!! }
            clazz?.let { "class"..clazz!! }
            init()
        }
        +l
        return l
    }

}

public class TextArea(rows:Int) : Component, ElementEvents {

    override val element = createElement("textarea") as HTMLTextAreaElement

    public var style:String by Attribute()
    public var id:String by Attribute()
    public var clazz:String by Attribute("class")

    public var value:String
        get() = element.textContent
        set(value) { element.textContent = value }

    public var rows:Int
        get() = parseInt(element.getAttribute("rows"))
        set(value) { element.setAttribute("rows", value.toString()) }

    {
        this.rows = rows
    }

    public fun scrollDown() {
        element.scrollTop = element.scrollHeight - element.clientHeight
    }

}

public class Table : Component {

    override public var element = createElement("table")

    public var border:String by Attribute()

    public fun thead(init:THead.() -> Unit) {
        element.appendComponent(THead() with { init() })
    }

    public fun tbody(init:TBody.() -> Unit) {
        element.appendComponent(TBody() with { init() })
    }

}

public class THead : Component {

    override public var element = createElement("thead")

    public fun tr(init:TRHead.() -> Unit) {
        element.appendComponent(TRHead() with  { init() })
    }

}

public class TBody : Component {

    override public var element = createElement("tbody")

    public fun tr(init:TRBody.() -> Unit) {
        element.appendComponent(TRBody() with  { init() })
    }

}

public class TRHead : Component {

    override public var element = createElement("tr")

    public fun th(init: HTMLComponent.() -> Unit):Unit {
        element.appendComponent(HTMLComponent("th") with  { init() })
    }

}

public class TRBody : Component {

    override public var element = createElement("tr")

    public fun td(init: HTMLComponent.() -> Unit) {
        element.appendComponent(HTMLComponent("td") with  { init() })
    }

}

public class OL : HTMLComponent("ol") {

    public fun li(init:Li.() -> Unit):Unit {
        +( Li() with  { init() })
    }

}

public class UL : HTMLComponent("ul") {

    public fun li(init:Li.() -> Unit) {
        +( Li() with  { init() })
    }

}

public class DL : HTMLComponent("dl") {

    public fun item(dt : HTMLComponent.() -> Unit, dd : HTMLComponent.() -> Unit) {
        +( HTMLComponent("dt") with { dt() } )
        +( HTMLComponent("dd") with { dd() } )
    }

}

native trait HTMLInputElementWithOnChange : HTMLInputElement {
    public native var onchange: () -> Unit
}

public abstract class InputComponent : Component {

    abstract override val element: HTMLInputElement

    public var checked:Boolean
        get() = element.checked
        set(value) { element.checked = value }

    public var disabled:Boolean
        get() = element.disabled
        set(value) { element.disabled = value }

}

public class CheckBox() : InputComponent() {

    override val element: HTMLInputElementWithOnChange =
            (createElement("input") with {
                setAttribute("type", "checkbox")
            }) as HTMLInputElementWithOnChange

    public var onchange:Function0<Unit>
        get():Function0<Unit> = element.onchange
        set(value:Function0<Unit>) {
            element.onchange = value
        }

}

native trait Context { }

native trait CanvasI {
    public fun getContext(id:String):Context = noImpl
}

open class Canvas(val width:Int, val height:Int) : HTMLComponent("canvas") {
    {
        element.setAttribute("width", "${width}")
        element.setAttribute("height", "${height}")
    }
    fun getContext(id:String):Context = (element as CanvasI).getContext(id)
}

public class Div : HTMLComponent("div") { }

public class Span : HTMLComponent("span") { }

public enum class ButtonType(val code:String) {
    BUTTON : ButtonType("button")
    SUBMIT : ButtonType("submit")
    RESET : ButtonType("reset")
}

public class Button(type:ButtonType = ButtonType.BUTTON) : HTMLComponent("button") {
    {
        element.setAttribute("type", type.code)
    }
}

public class Image : Component {

    override val element = createElement("img")
    public var src:String by Attribute()
    public var alt:String by Attribute()

}

public class P : HTMLComponent("p") { }

public class Li : HTMLComponent("li") { }

public class Anchor() : HTMLComponent("a") {

    public var href : String by Attribute()
    public var target: String by Attribute()

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

public fun text(text:String): HTMLComponent.() -> Unit = {
    +text
}
