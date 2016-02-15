package net.yested

import jquery.jq
import jquery.JQuery
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
import kotlin.browser.document
import kotlin.properties.Delegates
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0


 class Attribute(val attributeName:String? = null, val element: HTMLElement? = null) {

    private fun getElement(thisRef: Component?):HTMLElement = element ?: thisRef!!.element

     operator fun getValue(thisRef: Component, prop: KProperty<*>):String? =
            getElement(thisRef).getAttribute(attributeName ?: prop.name)

     operator fun setValue(thisRef: Component, prop: KProperty<*>, value: String?):Unit =
            getElement(thisRef).setAttribute(attributeName ?: prop.name, value!!)

}

 class BooleanAttribute(val attributeName:String? = null, val element:HTMLElement? = null) {

    private fun getElement(thisRef: Component?) = element ?: thisRef!!.element

     fun getValue(thisRef: Component, prop: KProperty<Boolean>):Boolean =
            getElement(thisRef).getAttribute(attributeName ?: prop.name) == "true"

     fun setValue(thisRef: Component, prop: KProperty<Boolean>, value: Boolean):Unit =
            getElement(thisRef).setAttribute(attributeName ?: prop.name, if (value) "true" else "false")

}

/**
 * Each UI component must implement this interface.
 * There is only one requirement for the component: It must return an HTML element.
 */
 interface Component {

    /**
     * Each component must return an HTML element.
     * Important: One instance of component must return always the same html element!
     */
    val element : HTMLElement
}

 fun createElement(name:String): HTMLElement = document.createElement(name) as HTMLElement

 fun HTMLElement.appendComponent(component:Component):Unit {
    this.appendChild(component.element)
}

 fun HTMLElement.removeChildByName(childElementName:String) {
    val elements = this.getElementsByTagName(childElementName)
    (0..elements.length-1).forEach {
        this.removeChild(elements.get(it)!!)
    }
}

 interface ElementEvents {

    val element : HTMLElement

     var onblur: ((Event) -> dynamic)?
        get() = element.onblur
        set(value) { element.onblur = value}

     var onclick: ((Event) -> dynamic)?
        get() = element.onclick
        set(value) { element.onclick = value}

     var ondblclick: ((Event) -> dynamic)?
        get() = element.ondblclick
        set(value) { element.ondblclick = value}

     var onfocus: ((Event) -> dynamic)?
        get() = element.onfocus
        set(value) { element.onfocus = value}

     var onkeydown: ((Event) -> dynamic)?
        get() = element.onkeydown
        set(value) { element.onkeydown = value}

     var onkeyup: ((Event) -> dynamic)?
        get() = element.onkeyup
        set(value) { element.onkeyup = value}

     var onmouseup: ((Event) -> dynamic)?
        get() = element.onmouseup
        set(value) { element.onmouseup = value}

     var onmousedown: ((Event) -> dynamic)?
        get() = element.onmousedown
        set(value) { element.onmousedown = value}

     var onmouseout: ((Event) -> dynamic)?
        get() = element.onmouseout
        set(value) { element.onmouseout = value}

     var onmouseover: ((Event) -> dynamic)?
        get() = element.onmouseover
        set(value) { element.onmouseover = value}

     var onmousemove: ((Event) -> dynamic)?
        get() = element.onmousemove
        set(value) { element.onmousemove = value}

     var onresize: ((Event) -> dynamic)?
        get() = element.onresize
        set(value) { element.onresize = value}

     var onscroll: ((Event) -> dynamic)?
        get() = element.onscroll
        set(value) { element.onscroll = value}

     var ondragstart: ((Event) -> dynamic)?
        get() = element.ondragstart
        set(value) { element.ondragstart = value}

     var ondrag: ((Event) -> dynamic)?
        get() = element.ondrag
        set(value) { element.ondrag = value}

     var ondragend: ((Event) -> dynamic)?
        get() = element.ondragend
        set(value) { element.ondragend = value}

}

 fun HTMLElement.removeAllContent() {
    while (lastChild != null) {
        removeChild(lastChild!!);
    }
}

 open class HTMLComponent(tagName:String, htmlElement:HTMLElement? = null) : Component, ElementEvents {

    override  var element = htmlElement?:createElement(tagName)

     var role:String? by Attribute()
     var style:String? by Attribute()
     var id:String? by Attribute()
     var clazz:String? by Attribute("class")

    operator  fun String.rangeTo(value:String):Unit = element.setAttribute(this, value)

    operator  fun String.unaryPlus(): Unit {
        jq(element).append(this)
    }

    operator  fun Component.unaryPlus() {
        this@HTMLComponent.appendChild(this)
    }

     fun appendChild(component: Component) {
        element.appendComponent(component)
    }

     fun appendChild(childElement: HTMLElement) {
        this.element.appendChild(childElement)
    }

     fun setContent(text:String) {
        jq(element).text(text)
    }

     fun setChild(component:Component) {
        removeAllChildren()
        element.appendChild(component.element)
    }

     fun removeAllChildren() {
        element.removeAllContent()
    }

     fun setChild(content:Component, effect:BiDirectionEffect, callback:Function0<Unit>? = null) {
        effect.applyOut(this) {
            setChild(content)
            effect.applyIn(this) {
                callback?.invoke()
            }
        }
    }

    open  fun a(clazz:String? = null, target:String? = null, href:String?=null, onclick: ((Event) -> Unit)? = null, init:Anchor.() -> Unit = {}) {
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

     fun div(id:String? = null, clazz:String = "", init:Div.() -> Unit):Div {
        val div = Div()
        div.init()
        div.clazz = clazz
        if (id != null) { div.id = id }
        element.appendComponent(div)
        return div
    }

     fun span(clazz:String? = null, init:Span.() -> Unit = {}):Span {
        val span = Span()
        span.init()
        clazz?.let { span.clazz = clazz }
        element.appendComponent(span)
        return span
    }

     fun img(src:String, alt:String? = null) {
        +(Image() with {
            this.src = src
            this.alt = alt?:""
        })
    }

     fun p(init:P.() -> Unit) {
        +(P() with { init() })
    }

     fun tag(tagName:String, init: HTMLComponent.() -> Unit): Unit {
        +(HTMLComponent(tagName) with { init() } )
    }

     fun table(init:Table.() -> Unit) {
        +(Table() with { init() })
    }

     fun checkbox(init:CheckBox.() -> Unit) {
        +(CheckBox() with { init() })
    }

     fun button(label: HTMLComponent.() -> Unit, type: ButtonType = ButtonType.BUTTON, onclick:(Event) -> dynamic) {
        +(Button(type = type) with {
            label()
            element.onclick = onclick
        })
    }

    @Suppress("UNUSED_PARAMETER")
     fun code(lang:String? = "javascript", content : String) {
        tag("pre") {
            tag("code") {
                +printMarkup(content)
            }
        }
    }

     fun ul(init:UL.() -> Unit): Unit =
            +( UL() with { init() })


     fun ol(init:OL.() -> Unit):Unit =
            +( OL() with { init() })

     fun dl(init:DL.() -> Unit):Unit =
            +( DL() with { init() })

     fun nbsp(times:Int = 1):Unit =
            (1..times).forEach {
                +("&nbsp;")
            }

     fun h1(init: HTMLComponent.() -> Unit): Unit = tag("h1", init)
     fun h2(init: HTMLComponent.() -> Unit): Unit = tag("h2", init)
     fun h3(init: HTMLComponent.() -> Unit): Unit = tag("h3", init)
     fun h4(init: HTMLComponent.() -> Unit): Unit = tag("h4", init)
     fun h5(init: HTMLComponent.() -> Unit): Unit = tag("h5", init)

     fun emph(init: HTMLComponent.() -> Unit): Unit = tag("strong", init)
     fun small(init: HTMLComponent.() -> Unit): Unit = tag("small", init)
     fun mark(init: HTMLComponent.() -> Unit): Unit = tag("mark", init)
     fun del(init: HTMLComponent.() -> Unit): Unit = tag("del", init)
     fun s(init: HTMLComponent.() -> Unit): Unit = tag("s", init)
     fun ins(init: HTMLComponent.() -> Unit): Unit = tag("ins", init)
     fun u(init: HTMLComponent.() -> Unit): Unit = tag("u", init)
     fun strong(init: HTMLComponent.() -> Unit): Unit = tag("strong", init)
     fun em(init: HTMLComponent.() -> Unit): Unit = tag("em", init)
     fun b(init: HTMLComponent.() -> Unit): Unit = tag("b", init)
     fun i(init: HTMLComponent.() -> Unit): Unit = tag("i", init)
     fun kbd(init: HTMLComponent.() -> Unit): Unit = tag("kbd", init)
     fun variable(init: HTMLComponent.() -> Unit): Unit = tag("var", init)
     fun samp(init: HTMLComponent.() -> Unit): Unit = tag("samp", init)
     fun blockquote(init: HTMLComponent.() -> Unit): Unit = tag("blockquote", init)
     fun form(init: HTMLComponent.() -> Unit): Unit = tag("form", init)

     fun textArea(rows:Int = 3, init: TextArea.() ->Unit): Unit =
            +(TextArea(rows = rows) with {
                init()
            })

     fun abbr(title:String, init: HTMLComponent.() -> Unit): Unit {
        +(HTMLComponent("abbr") with {
            element.setAttribute("title", title)
            init()
        })
    }

     fun br(): Unit = tag("br") { }

     fun label(forId:String? = null, clazz:String? = null, init: HTMLComponent.() -> Unit): HTMLComponent {
        val l = HTMLComponent("label") with {
            forId?.let { "for"..forId }
            clazz?.let { "class"..clazz }
            init()
        }
        +l
        return l
    }

}

 class TextArea(rows:Int) : ObservableInput<String>(), ElementEvents {

     var textContent: String
        get() = element.value
        set(value) { element.value = value }

    override val element = createElement("textarea") as HTMLTextAreaElement

     var style:String? by Attribute()
     var id:String? by Attribute()
     var clazz:String? by Attribute("class")

     var rows:Int
        get() = parseInt(element.getAttribute("rows")?:"1")
        set(value) { element.setAttribute("rows", value.toString()) }

    init {
        this.rows = rows
    }

    override fun clear() {
        data = ""
    }

     fun scrollDown() {
        element.scrollTop = element.scrollHeight - jq(element).height().toInt()
    }

    override var data: String
        get() = textContent
        set(value) {this.textContent = value}

}

 class Table : Component {

    override  var element = createElement("table")

     var border:String? by Attribute()

     fun thead(init:THead.() -> Unit) {
        element.appendComponent(THead() with { init() })
    }

     fun tbody(init:TBody.() -> Unit) {
        element.appendComponent(TBody() with { init() })
    }

}

 class THead : Component {

    override  var element = createElement("thead")

     fun tr(init:TRHead.() -> Unit) {
        element.appendComponent(TRHead() with  { init() })
    }

}

 class TBody : Component {

    override  var element = createElement("tbody")

     fun tr(init:TRBody.() -> Unit) {
        element.appendComponent(TRBody() with  { init() })
    }

}

 class TRHead : Component {

    override  var element = createElement("tr")

     fun th(init: HTMLComponent.() -> Unit):Unit {
        element.appendComponent(HTMLComponent("th") with  { init() })
    }

}

 class TRBody : Component {

    override  var element = createElement("tr")

     fun td(init: HTMLComponent.() -> Unit) {
        element.appendComponent(HTMLComponent("td") with  { init() })
    }

}

 class OL : HTMLComponent("ol") {

     fun li(init:Li.() -> Unit):Unit {
        +( Li() with  { init() })
    }

}

 class UL : HTMLComponent("ul") {

     fun li(init:Li.() -> Unit) {
        +( Li() with  { init() })
    }

}

 class DL : HTMLComponent("dl") {

     fun item(dt : HTMLComponent.() -> Unit, dd : HTMLComponent.() -> Unit) {
        +( HTMLComponent("dt") with { dt() } )
        +( HTMLComponent("dd") with { dd() } )
    }

}

 @native var HTMLElement.onchange: (() -> Unit)?
    get() = noImpl
    set(value) = noImpl


 interface InputComponent<T> : Component {
    var data: T
    fun addOnChangeListener(invoke:()->Unit)
    fun addOnChangeLiveListener(invoke:()->Unit)
    fun decorate(valid:Boolean)
    fun clear()
}

 abstract class InputElementComponent<T>(): ObservableInput<T>() {
    abstract override val element: HTMLInputElement

    protected open var value: String
        get() = element.value
        set(value) { element.value = value }

     open var disabled: Boolean
        get() = element.disabled
        set(value) { element.disabled = value }

     open var readOnly: Boolean
        get() = element.readOnly
        set(value) { element.readOnly = value }
}

 abstract class ObservableInput<T>(): InputComponent<T> {
    protected val onChangeListeners = arrayListOf<Function0<Unit>>();
    protected val onChangeLiveListeners = arrayListOf<Function0<Unit>>();

    override fun addOnChangeListener(invoke: () -> Unit) {
        onChangeListeners.add(invoke)
        registerOnChangeListener();
    }

    override fun addOnChangeLiveListener(invoke: () -> Unit) {
        onChangeLiveListeners.add(invoke)
        registerOnChangeListener();
    }
    override fun decorate(valid: Boolean) {
    }

    /**
     * HACK: If the following code is placed into the constructor, it throws NPE because element is NULL there.
     */
    private fun registerOnChangeListener() {
        if (element.onchange == null) {
            element.onchange = {
                onChangeListeners.forEach { it() }
                onChangeLiveListeners.forEach { it() }
            }
            element.onkeyup = {
                onChangeLiveListeners.forEach { it() }
            }
        }
    }
}

 class TextInput() : InputElementComponent<String>() {

    override fun clear() {
        data = ""
    }

    override var data: String
        get() = value
        set(value) {this.value = value}

    override val element: HTMLInputElement =
            (createElement("input") with {
                setAttribute("type", "text")
            }) as HTMLInputElement
}

open  class CheckBox() : InputElementComponent<Boolean>() {

    override fun clear() {
        data = false
    }

    override val element: HTMLInputElement =
            (createElement("input") with {
                setAttribute("type", "checkbox")
            }) as HTMLInputElement

     open var checked: Boolean
        get() = element.checked
        set(value) { element.checked = value }

    override var data: Boolean
        get() = checked
        set(value) {this.checked = value}

    override  var value: String
        get() = element.value
        set(value) { element.value = value }
}

@native interface Context { }

@native interface CanvasI {
     fun getContext(id:String):Context = noImpl
}

open class Canvas(val width:Int, val height:Int) : HTMLComponent("canvas") {
    init {
        element.setAttribute("width", "${width}")
        element.setAttribute("height", "${height}")
    }
    fun getContext(id:String):Context = (element as CanvasI).getContext(id)
}

 class Div : HTMLComponent("div") { }

 class Span : HTMLComponent("span") { }

 enum class ButtonType(val code:String) {
    BUTTON("button"),
    SUBMIT("submit"),
    RESET("reset")
}

 class Button(type:ButtonType = ButtonType.BUTTON) : HTMLComponent("button") {
    init {
        element.setAttribute("type", type.code)
    }
}

 class Image : Component {

    override val element = createElement("img")
     var src:String? by Attribute()
     var alt:String? by Attribute()

}

 class P : HTMLComponent("p") { }

 class Li : HTMLComponent("li") { }

 class Anchor() : HTMLComponent("a") {

     var href : String? by Attribute()
     var target: String? by Attribute()

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

 fun text(text:String): HTMLComponent.() -> Unit = {
    +text
}
