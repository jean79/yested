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
import net.yested.HTMLParentComponent
import net.yested.Span
import net.yested.with

public trait ValidatorI {
    fun onchange(invoke:(valid:Boolean)->Unit)
    fun isValid():Boolean
    val errorText:String
}

public class Validator<T>(val inputElement: InputElement<T>, override val errorText:String, val validator:(value:T)->Boolean) : ValidatorI {

    private val onChangeListeners: java.util.ArrayList<Function1<Boolean, Unit>> = java.util.ArrayList();

    private var listen = false;

    {
        inputElement.addOnChangeListener { listen = true; revalidate() }
        inputElement.addOnChangeLiveListener { if (listen) { revalidate() } }
    }

    override fun onchange(invoke: (Boolean) -> Unit) {
        onChangeListeners.add(invoke)
    }

    private fun revalidate():Boolean =
        validator(inputElement.value) with {
            for (listener in onChangeListeners) {
                listener(this)
            }
        }

    override fun isValid(): Boolean = revalidate()

}

public class Form(private val labelDef:String = "col-sm-2",private val inputDef:String = "col-sm-10") : HTMLParentComponent("form") {

    {
        element.setAttribute("class", "form-horizontal")
        role = "form"
        element.setAttribute("onsubmit", "return false")
    }

    public fun item(forId:String = "", label:HTMLParentComponent.()->Unit, validator:ValidatorI? = null, content:HTMLParentComponent.()->Unit) {

        val spanErrMsg = Span() with { clazz = "help-block" }
        val divInput = div(clazz = "$inputDef", init = content) with { +spanErrMsg }

        val divFormGroup = div(clazz = "form-group") {
                label(forId = forId, clazz= "${labelDef} control-label", init = label)
                + divInput
            }
        validator?.onchange {
            isValid ->
                divFormGroup.clazz = if (isValid) "form-group" else "form-group has-error"
                spanErrMsg.replace(if (isValid) "" else validator!!.errorText)
        }
    }

}

public fun HTMLParentComponent.btsForm(labelDef:String = "col-sm-2", inputDef:String = "col-sm-10", init:Form.() -> Unit):Unit {
    val form = Form(labelDef = labelDef, inputDef = inputDef)
    form.init()
    +form
}


