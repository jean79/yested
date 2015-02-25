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
import net.yested.InputComponent
import net.yested.CheckBox
import net.yested.ObservableInput
import net.yested.onchange
import net.yested.InputElementComponent

public enum class InputSize(val code:String) {
    DEFAULT: InputSize("")
    LARGE: InputSize("input-lg")
    SMALL: InputSize("input-sm")
}

public abstract class InputField<T>(val inputSize: InputSize = InputSize.DEFAULT, placeholder:String? = null, type: String) : InputElementComponent<T>() {

    override val element: HTMLInputElement = createElement("input") as HTMLInputElement

    public var id: String by Attribute();


    {
        element.setAttribute("class", "form-control ${inputSize.code}")
    }

    override fun decorate(valid: Boolean) {
        element.setAttribute("class", if (valid) "form-control" else "form-control has-error")
    }

    {
        element.setAttribute("type", type)
        if (placeholder != null) {
            element.setAttribute("placeholder", placeholder)
        }
    }
}

public class StringInputField(inputSize: InputSize = InputSize.DEFAULT, placeholder:String? = null) :
        InputField<String>(inputSize, placeholder, type = "text") {

    override fun clear() {
        data = ""
    }

    override var data: String
        get() = value
        set(value) {this.value = value}
}

public class IntInputField(inputSize: InputSize = InputSize.DEFAULT, placeholder:String? = null) :
        InputField<Int?>(inputSize, placeholder, type = "number") {

    override fun clear() {
        data = null
    }

    override var data: Int?
        get() = if (value.isEmpty()) null else parseInt(value)
        set(value) {this.value = if (value == null) "" else value.toString()}
}

public class FloatInputField(inputSize: InputSize = InputSize.DEFAULT, placeholder:String? = null) :
        InputField<Float?>(inputSize, placeholder, type = "number") {
    override fun clear() {
        data = null
    }

    override var data: Float?
        get() = if (value.isEmpty()) null else safeParseDouble(value)?.toFloat() ?: error("cannot convert $value to Float")
        set(value) {this.value = if (value == null) "" else value.toString()}
}

public class ColorInputField(inputSize: InputSize = InputSize.DEFAULT, placeholder:String? = null) :
        InputField<String?>(inputSize, placeholder, type = "color") {
    override fun clear() {
        data = null
    }

    override var data: String?
        get() = value
        set(value) {this.value = value ?: ""
        }
}

/*public fun HTMLComponent.inputField(placeholder: String? = null, init: InputField.() -> Unit):Unit {
    +(InputField(placeholder = placeholder) with  { init() })
}*/

public class BtsCheckBox(private val label:HTMLComponent.()->Unit) : CheckBox() {

    private val inputCheckbox = (createElement("input") with {
                                                                    setAttribute("type", "checkbox")
                                                                }) as HTMLInputElement

    override val element =
            (createElement("div") with {
                setAttribute("class", "checkbox")
                appendChild(createElement("label") with {
                    appendChild(inputCheckbox)
                    appendChild((Span() with label).element)
                })
            }) as HTMLInputElement

    public override var disabled:Boolean
        get() = inputCheckbox.disabled
        set(value) { inputCheckbox.disabled = value }

    override var checked: Boolean
        get():Boolean = inputCheckbox.checked
        set(value:Boolean) {
            inputCheckbox.checked = value
        }
}

public fun HTMLComponent.btsCheckBox(label:HTMLComponent.()->Unit):Unit {
    +(BtsCheckBox(label))
}

private data class SelectOption<T>(val tag:HTMLOptionElement, val value:T)

public class Select<T>(val options: List<T>,
					   val inputSize: InputSize = InputSize.DEFAULT,
					   multiple:Boolean = false,
					   size:Int = 1,
                       val emptyOptionText: String? = null,
					   val renderer:(T)->String) : ObservableInput<T>() {

    override val element: HTMLSelectElement = createElement("select") as HTMLSelectElement

    private var optionTags:ArrayList<SelectOption<T?>> = ArrayList()

    private var callbackIsInvoked = false

    public var selectedItems: List<T>
        get() = optionTags.filter { it.tag.selected && it.value != null}.map { it.value as T }
        set(newData) {
            selectThese(newData)
            changeSelected()
        }

    override fun clear() {
        selectedItems = emptyList()
    }

    {
        element.setAttribute("class", "form-control ${inputSize.code}")
        element.setAttribute("size", size.toString())
        generateOptions()
        if (multiple) {
            element.setAttribute("multiple", "multiple")
        }
        (element).onchange = { changeSelected() }
    }

    private fun changeSelected() {
        if (!callbackIsInvoked) {
            callbackIsInvoked = true
            onChangeListeners.forEach { it() }
            callbackIsInvoked = false
        }
    }

    private fun selectThese(selectedItems:List<T>) {
        optionTags.forEach {
            it.tag.selected = selectedItems.contains(it.value)
        }
    }

    private fun generateOptions() {
        optionTags =  ArrayList()
		if (emptyOptionText != null) {
			val optionTag = HTMLComponent("option") with { +emptyOptionText }
			val selectOption = SelectOption<T?>(tag = optionTag.element as HTMLOptionElement, value = null)
			optionTags.add(selectOption)
			element.appendComponent(optionTag)
		}
        options.forEach {
            val optionTag = HTMLComponent("option") with { +renderer(it) }
            val value:T = it
            val selectOption = SelectOption<T?>(tag = optionTag.element as HTMLOptionElement, value = value)
            optionTags.add(selectOption)
            element.appendComponent(optionTag)
        }
    }

    override public var data: T
        get() = selectedItems.first()
        set(value) {
            selectedItems = listOf(value)
        }

    override fun decorate(valid: Boolean) {
        element.setAttribute("class", if (valid) "form-control" else "form-control has-error")
    }
}

/**
 * <div class="input-group">
<div class="input-group-addon">$</div>
<input type="text" class="form-control" id="exampleInputAmount" placeholder="Amount">
<div class="input-group-addon">.00</div>
</div>
 */
public fun HTMLComponent.inputAddOn<T>(prefix:String? = null, suffix:String? = null, textInput : InputField<T>):Unit =
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



