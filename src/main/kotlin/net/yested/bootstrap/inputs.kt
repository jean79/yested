/**
 * Created by jean on 21.11.2014.
 */
package net.yested.bootstrap

import kotlin.js.dom.html.HTMLInputElement
import java.util.ArrayList
import net.yested.ParentComponent

public native trait HTMLInputElementWithOnChange {
    public native var onchange: () -> Unit
}

trait InputElement<T> {
    fun addOnChangeListener(invoke:()->Unit)
    fun addOnChangeLiveListener(invoke:()->Unit)
    var value:T
    fun decorate(valid:Boolean)
}

class TextInput(placeholder:String? = null) : ParentComponent("input"), InputElement<String> {

    val onChangeListeners: ArrayList<Function0<Unit>> = ArrayList();
    val onChangeLiveListeners: ArrayList<Function0<Unit>> = ArrayList();

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


