/**
 * Created by jean on 21.11.2014.
 */

package net.yested.bootstrap

/**
 * <form class="form-horizontal" role="form">
        <div class="form-group">
            <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <div class="checkbox">
                    <label>
                        <input type="checkbox"> Remember me
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Sign in</button>
            </div>
        </div>
</form>
 */
import net.yested.HTMLComponent
import net.yested.Span
import net.yested.with
import net.yested.ObservableInput
import net.yested.InputComponent

public interface ValidatorI {
    fun onchange(invoke:(valid:Boolean)->Unit)
    fun isValid():Boolean
    val errorText:String
}

public class Validator<T>(val inputElement: InputComponent<T>, override val errorText:String, val validator:(value:T)->Boolean) : ValidatorI {

    private val onChangeListeners: java.util.ArrayList<Function1<Boolean, Unit>> = java.util.ArrayList();

    private var listen = false

    init {
        inputElement.addOnChangeListener { listen = true; revalidate() }
        inputElement.addOnChangeLiveListener { if (listen) { revalidate() } }
    }

    override fun onchange(invoke: (Boolean) -> Unit) {
        onChangeListeners.add(invoke)
    }

    private fun revalidate():Boolean =
        validator(inputElement.data) with {
            for (listener in onChangeListeners) {
                listener(this)
            }
            if (this) {
                removeTooltip(inputElement.element);
            } else {
                addTooltip(element = inputElement.element, options = TooltipOptions(placement = TooltipPlacement.TOP), title = { errorText })
            }
        }

    override fun isValid(): Boolean = revalidate()

}

public enum class FormStyle(val code:String) {
    DEFAULT("form-default"),
    INLINE("form-inline"),
    HORIZONTAL("form-horizontal")
}

public enum class FormInputSize(val code:String) {
    DEFAULT(""),
    LARGE("form-group-lg"),
    SMALL("form-group-sm")
}

public class Form(private val formStyle: FormStyle = FormStyle.DEFAULT, private val inputSize:FormInputSize = FormInputSize.DEFAULT, private val labelDef:DeviceSize = Small(4), private val inputDef:DeviceSize = Small(8)) : HTMLComponent("form") {

    init {
        element.setAttribute("class", "${formStyle.code}")
        role = "form"
        element.setAttribute("onsubmit", "return false")
    }

    public fun item(forId:String = "", label: HTMLComponent.()->Unit, validator:ValidatorI? = null, content: HTMLComponent.()->Unit) {

        val divInput = if (formStyle == FormStyle.HORIZONTAL) {
            div(clazz = "$inputDef", init = content)
        } else span(init = content)

        val divFormGroup = div(clazz = "form-group ${inputSize.code}") {
                label(forId = forId, clazz= if (formStyle == FormStyle.HORIZONTAL) "${labelDef} control-label" else "", init = label)
                + divInput
            }
        validator?.onchange {
            isValid ->
                divFormGroup.clazz = if (isValid) "form-group" else "form-group has-error"
        }
    }

}

public fun HTMLComponent.btsForm(formStyle: FormStyle = FormStyle.DEFAULT, labelDef:DeviceSize = Small(4), inputDef:DeviceSize = Small(8), init:Form.() -> Unit):Unit {
    val form = Form(formStyle = formStyle, labelDef = labelDef, inputDef = inputDef)
    form.init()
    +form
}


