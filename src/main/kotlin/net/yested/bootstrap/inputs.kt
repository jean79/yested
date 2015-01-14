/**
 * Created by jean on 21.11.2014.
 */
package net.yested.bootstrap

import kotlin.js.dom.html.HTMLInputElement
import java.util.ArrayList
import net.yested.ParentComponent
import net.yested.Attribute
import net.yested.HTMLParentComponent
import net.yested.tag
import kotlin.dom.addElement
import kotlin.dom.addText
import net.yested.Div
import net.yested.Span
import net.yested.with
import kotlin.js.dom.html.HTMLSelectElement
import kotlin.js.dom.html.HTMLOptionElement
import java.util.HashMap

native trait HTMLInputElementWithOnChange : HTMLInputElement {
    public native var onchange: () -> Unit
}

public trait InputElement<T> {
    fun addOnChangeListener(invoke:()->Unit)
    fun addOnChangeLiveListener(invoke:()->Unit)
    var value:T
    fun decorate(valid:Boolean)
}

public class TextInput(placeholder:String? = null) : HTMLParentComponent("input"), InputElement<String> {

    private val onChangeListeners: ArrayList<Function0<Unit>> = ArrayList();
    private val onChangeLiveListeners: ArrayList<Function0<Unit>> = ArrayList();

    {
        element.setAttribute("class", "form-control")
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

public fun HTMLParentComponent.textInput(placeholder: String?, init: TextInput.() -> Unit):Unit {
    val textInput = TextInput(placeholder = placeholder)
    textInput.init()
    add(textInput)
}

public class CheckBox() : ParentComponent("input"), InputElement<Boolean> {

    private val onChangeListeners: ArrayList<Function0<Unit>> = ArrayList();
    private val onChangeLiveListeners: ArrayList<Function0<Unit>> = ArrayList();

    private fun getElement(): HTMLInputElementWithOnChange = element as HTMLInputElementWithOnChange

    {
        setAttribute("type", "checkbox")
        getElement().onchange = {
            onChangeListeners.forEach { it() }
            onChangeLiveListeners.forEach { it() }
        }
    }

    override var value:Boolean
        get():Boolean = getElement().checked
        set(value:Boolean) {
            getElement().checked = value
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

private data class SelectOption<TT>(val tag:HTMLOptionElement, val value:TT)

public class Select<T>(multiple:Boolean = false, size:Int = 1, val renderer:(T)->String) : ParentComponent("select") {

    private val onChangeListeners: ArrayList<Function0<Unit>> = ArrayList();

    private var selectedItemsInt:List<T> = listOf()

    private var dataInt:List<T>? = null

    private var optionTags:ArrayList<SelectOption<T>> = ArrayList()

    public var data:List<T>?
        get() = dataInt
        set(newData) {
            dataInt = newData
            regenerate()
            changeSelected()
        }

    public var selectedItems:List<T>
        get() = selectedItemsInt
        set(newData) {
            selectThese(newData)
            changeSelected()
        }

    {
        element.setAttribute("class", "form-control")
        element.setAttribute("size", size.toString())
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

    private fun regenerate() {
        element.innerHTML = ""
        optionTags =  ArrayList()
        selectedItemsInt = listOf()
        if (dataInt != null) {
            dataInt?.forEach {
                val optionTag = tag("option", init = { +renderer(it) })
                val value:T = it
                val selectOption = SelectOption(tag = optionTag.element as HTMLOptionElement, value = value)
                optionTags.add(selectOption)
                add(optionTag)
            }
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
public fun HTMLParentComponent.inputAddOn(prefix:String? = null, suffix:String? = null, textInput : TextInput):Unit =

    add(
        div(clazz = "input-group") {
            prefix?.let {
                div(clazz = "input-group-addon") { +prefix!! }
            }
            +textInput
            suffix?.let {
                div(clazz = "input-group-addon") { +suffix!! }
            }
        })



