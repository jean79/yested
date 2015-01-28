/**
 * Created by jean on 21.11.2014.
 */
package net.yested.bootstrap

import kotlin.js.dom.html.HTMLInputElement
import java.util.ArrayList
import net.yested.Attribute
import net.yested.HTMLComponent
import kotlin.dom.addElement
import kotlin.dom.addText
import net.yested.Div
import net.yested.Span
import net.yested.with
import kotlin.js.dom.html.HTMLSelectElement
import kotlin.js.dom.html.HTMLOptionElement
import java.util.HashMap
import net.yested.Component
import kotlin.js.dom.html.HTMLElement
import net.yested.createElement
import net.yested.appendComponent
import net.yested.BooleanAttribute

native trait HTMLInputElementWithOnChange : HTMLInputElement {
    public native var onchange: () -> Unit
}

public enum class InputSize(val code:String) {
    DEFAULT: InputSize("")
    LARGE: InputSize("input-lg")
    SMALL: InputSize("input-sm")
}

public trait InputElement<T> {
    fun addOnChangeListener(invoke:()->Unit)
    fun addOnChangeLiveListener(invoke:()->Unit)
    var value:T
    var disabled:Boolean
    var readonly:Boolean
    fun decorate(valid:Boolean)
}

public class TextInput(val inputSize: InputSize = InputSize.DEFAULT, placeholder:String? = null) : Component, InputElement<String> {

    override val element: HTMLElement = createElement("input")

    public var id: String by Attribute()

    override var disabled:Boolean by BooleanAttribute()
    override var readonly:Boolean by BooleanAttribute()

    private val onChangeListeners: ArrayList<Function0<Unit>> = ArrayList();
    private val onChangeLiveListeners: ArrayList<Function0<Unit>> = ArrayList();

    {
        element.setAttribute("class", "form-control ${inputSize.code}")
        (element as HTMLInputElementWithOnChange).onchange = {
            onChangeListeners.forEach { it() }
            onChangeLiveListeners.forEach { it() }
        }
        element.onkeyup = {
            onChangeLiveListeners.forEach { it() }
        }
    }

    override var value:String
        get():String = (element as HTMLInputElement).value
        set(value:String) {
            (element as HTMLInputElement).value = value
        }

    override fun decorate(valid: Boolean) {
        element.setAttribute("class", if (valid) "form-control" else "form-control has-error")
    }

    {
        element.setAttribute("type", "text")
        if (placeholder != null) {
            element.setAttribute("placeholder", placeholder)
        }
    }

    override fun addOnChangeListener(invoke: () -> Unit) {
        onChangeListeners.add(invoke)
    }

    override fun addOnChangeLiveListener(invoke: () -> Unit) {
        onChangeLiveListeners.add(invoke)
    }

}

public fun HTMLComponent.textInput(placeholder: String?, init: TextInput.() -> Unit):Unit {
    +(TextInput(placeholder = placeholder) with  { init() })
}

public class BtsCheckBox(private val label:HTMLComponent.()->Unit) : Component, InputElement<Boolean> {

    private val inputCheckbox : HTMLInputElementWithOnChange = (createElement("input") with {
                                                                    setAttribute("type", "checkbox")
                                                                }) as HTMLInputElementWithOnChange

    override val element: HTMLElement =
            createElement("div") with {
                setAttribute("class", "checkbox")
                appendChild(createElement("label") with {
                    appendChild(inputCheckbox)
                    appendChild((Span() with label).element)
                })
            }

    override var disabled:Boolean by BooleanAttribute(element = inputCheckbox)
    override var readonly:Boolean by BooleanAttribute(element = inputCheckbox)

    private val onChangeListeners: ArrayList<Function0<Unit>> = ArrayList();
    private val onChangeLiveListeners: ArrayList<Function0<Unit>> = ArrayList();

    {
        inputCheckbox.onchange = {
            onChangeListeners.forEach { it() }
            onChangeLiveListeners.forEach { it() }
        }
    }

    override var value:Boolean
        get():Boolean = inputCheckbox.checked
        set(value:Boolean) {
            inputCheckbox.checked = value
        }

    override fun decorate(valid: Boolean) {
        //element.setAttribute("class", if (valid) "form-control" else "form-control has-error")
    }

    override fun addOnChangeListener(invoke: () -> Unit) {
        onChangeListeners.add(invoke)
    }

    override fun addOnChangeLiveListener(invoke: () -> Unit) {
        onChangeLiveListeners.add(invoke)
    }

}

public fun HTMLComponent.btsCheckBox(label:HTMLComponent.()->Unit):Unit {
    +(BtsCheckBox(label))
}

private data class SelectOption<TT>(val tag:HTMLOptionElement, val value:TT)

public class Select<T>(val data:List<T>, val inputSize: InputSize = InputSize.DEFAULT, multiple:Boolean = false, size:Int = 1, val renderer:(T)->String) : Component {

    override val element: HTMLElement = createElement("select")

    public var disabled:Boolean by BooleanAttribute()
    public var readonly:Boolean by BooleanAttribute()

    private val onChangeListeners: ArrayList<Function0<Unit>> = ArrayList();

    private var selectedItemsInt:List<T> = listOf()

    private var optionTags:ArrayList<SelectOption<T>> = ArrayList()

    public var selectedItems:List<T>
        get() = optionTags.filter { it.tag.selected }.map { it.value }
        set(newData) {
            selectThese(newData)
            changeSelected()
        }

    {
        element.setAttribute("class", "form-control ${inputSize.code}")
        element.setAttribute("size", size.toString())
        generateOptions()
        if (multiple) {
            element.setAttribute("multiple", "multiple")
        }
        (element as HTMLInputElementWithOnChange).onchange = { changeSelected() }
    }

    private fun changeSelected() {
        selectedItemsInt = optionTags.filter { it.tag.selected }.map { it.value }
        onChangeListeners.forEach { it() }
    }

    private fun selectThese(selectedItems:List<T>) {
        optionTags.forEach {
            it.tag.selected = selectedItems.contains(it.value)
        }
    }

    private fun generateOptions() {
        optionTags =  ArrayList()
        selectedItemsInt = listOf()
        data.forEach {
            val optionTag = HTMLComponent("option") with { +renderer(it) }
            val value:T = it
            val selectOption = SelectOption(tag = optionTag.element as HTMLOptionElement, value = value)
            optionTags.add(selectOption)
            element.appendComponent(optionTag)
        }
    }

    public fun addOnChangeListener(invoke: () -> Unit) {
        onChangeListeners.add(invoke)
    }

}

/**
 * <div class="input-group">
<div class="input-group-addon">$</div>
<input type="text" class="form-control" id="exampleInputAmount" placeholder="Amount">
<div class="input-group-addon">.00</div>
</div>
 */
public fun HTMLComponent.inputAddOn(prefix:String? = null, suffix:String? = null, textInput : TextInput):Unit =
    +(
        div(clazz = "input-group") {
            prefix?.let {
                div(clazz = "input-group-addon") { +prefix!! }
            }
            +textInput
            suffix?.let {
                div(clazz = "input-group-addon") { +suffix!! }
            }
        })



