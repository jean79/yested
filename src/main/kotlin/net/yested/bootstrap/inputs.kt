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

native trait HTMLInputElementWithOnChange : HTMLInputElement {
    public native var onchange: () -> Unit
}

public trait InputElement<T> {
    fun addOnChangeListener(invoke:()->Unit)
    fun addOnChangeLiveListener(invoke:()->Unit)
    var value:T
    fun decorate(valid:Boolean)
}

public class TextInput(placeholder:String? = null) : ParentComponent("input"), InputElement<String> {

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
