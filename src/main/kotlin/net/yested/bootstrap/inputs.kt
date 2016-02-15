package net.yested.bootstrap

import java.util.ArrayList
import net.yested.Attribute
import net.yested.HTMLComponent
import net.yested.Span
import net.yested.with
import net.yested.createElement
import net.yested.appendComponent
import net.yested.CheckBox
import net.yested.ObservableInput
import net.yested.InputElementComponent
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLOptionElement
import org.w3c.dom.HTMLSelectElement

 enum class InputSize(val code:String) {
    DEFAULT(""),
    LARGE("input-lg"),
    SMALL("input-sm")
}

 abstract class InputField<T>(val inputSize: InputSize = InputSize.DEFAULT, placeholder:String? = null, type: String) : InputElementComponent<T>() {

    override val element: HTMLInputElement = createElement("input") as HTMLInputElement

     var id: String? by Attribute()


    init {
        element.setAttribute("class", "form-control ${inputSize.code}")
    }

    override fun decorate(valid: Boolean) {
        element.setAttribute("class", if (valid) "form-control" else "form-control has-error")
    }

    init {
        element.setAttribute("type", type)
        if (placeholder != null) {
            element.setAttribute("placeholder", placeholder)
        }
    }
}

 class StringInputField(inputSize: InputSize = InputSize.DEFAULT, placeholder:String? = null) :
        InputField<String>(inputSize, placeholder, type = "text") {

    override fun clear() {
        data = ""
    }

    override var data: String
        get() = value
        set(value) {this.value = value}
}

 class IntInputField(inputSize: InputSize = InputSize.DEFAULT, placeholder:String? = null) :
        InputField<Int?>(inputSize, placeholder, type = "number") {

    override fun clear() {
        data = null
    }

    override var data: Int?
        get() = if (value.isEmpty()) null else parseInt(value)
        set(value) {this.value = if (value == null) "" else value.toString()}
}

 class FloatInputField(inputSize: InputSize = InputSize.DEFAULT, placeholder:String? = null) :
        InputField<Float?>(inputSize, placeholder, type = "number") {
    override fun clear() {
        data = null
    }

    override var data: Float?
        get() = if (value.isEmpty()) null else safeParseDouble(value)?.toFloat() ?: error("cannot convert $value to Float")
        set(value) {this.value = if (value == null) "" else value.toString()}
}

 class ColorInputField(inputSize: InputSize = InputSize.DEFAULT, placeholder:String? = null) :
        InputField<String?>(inputSize, placeholder, type = "color") {
    override fun clear() {
        data = null
    }

    override var data: String?
        get() = value
        set(value) {this.value = value ?: ""
        }
}

/* fun HTMLComponent.inputField(placeholder: String? = null, init: InputField.() -> Unit):Unit {
    +(InputField(placeholder = placeholder) with  { init() })
}*/

 class BtsCheckBox(private val label:HTMLComponent.()->Unit) : CheckBox() {

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

     override var disabled:Boolean
        get() = inputCheckbox.disabled
        set(value) { inputCheckbox.disabled = value }

    override var checked: Boolean
        get():Boolean = inputCheckbox.checked
        set(value:Boolean) {
            inputCheckbox.checked = value
        }
}

 fun HTMLComponent.btsCheckBox(label:HTMLComponent.()->Unit):Unit {
    +(BtsCheckBox(label))
}

private data class SelectOption<T>(val tag: HTMLOptionElement, val value:T)

 class Select<T>(val options: List<T>,
					   val inputSize: InputSize = InputSize.DEFAULT,
					   multiple:Boolean = false,
					   size:Int = 1,
                       val emptyOptionText: String? = null,
					   val renderer:(T)->String) : ObservableInput<T>() {

    override val element: HTMLSelectElement = createElement("select") as HTMLSelectElement

    private var optionTags:ArrayList<SelectOption<T?>> = ArrayList()

    private var callbackIsInvoked = false

     var selectedItems: List<T>
        get() = optionTags.filter { it.tag.selected && it.value != null}.map { it.value as T }
        set(newData) {
            selectThese(newData)
            changeSelected()
        }

    override fun clear() {
        selectedItems = emptyList()
    }

    init {
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
            it.tag.selected = selectedItems.contains(it.value as Any?)
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

    override  var data: T
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
 fun <T> HTMLComponent.inputAddOn(prefix:String? = null, suffix:String? = null, textInput : InputField<T>):Unit =
    +(
        div(clazz = "input-group") {
            prefix?.let {
                div(clazz = "input-group-addon") { +prefix }
            }
            +textInput
            suffix?.let {
                div(clazz = "input-group-addon") { +suffix }
            }
        })



