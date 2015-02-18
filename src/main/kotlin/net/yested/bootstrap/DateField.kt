package net.yested.bootstrap

import net.yested.InputElementComponent
import kotlin.js.dom.html.HTMLInputElement
import net.yested.Div
import net.yested.createElement
import net.yested.with
import net.yested.Span
import jquery.jq
import jquery.JQuery
import net.yested.utils.FormatString
import net.yested.utils.Moment
import net.yested.utils.FormatStringBuilder


// TODO: support Locales: http://momentjs.com/docs/#/i18n/
public class DateField(formatter: FormatStringBuilder.()->FormatString) : InputElementComponent<Moment?>() {

    val formatString = FormatStringBuilder().formatter().toString()

    private val inputElement = (createElement("input") with {
        setAttribute("type", "text")
        className = "form-control"
    }) as HTMLInputElement

    override val element =
            (Div() with {
                clazz = "input-group date"
                appendChild(inputElement)
                appendChild((Span() with {
                    clazz = "input-group-addon"
                    appendChild((Span() with { clazz = "glyphicon glyphicon-calendar" }))
                }))
            }).element as HTMLInputElement

    override fun clear() {
        data = null
    }

    override protected var value: String
        get():String = inputElement.value
        set(value: String) {
            inputElement.value = value
        }

    override var data: Moment?
        get() = if (value.isEmpty()) null else Moment.parse(value, formatString)
        set(value) {
            this.value = if (value == null) "" else value.format(formatString)
        }

    public fun init() {
        val param = object {
            //val locale = "hu"
            val format = formatString
        }
        // Hack: datetimepicker cannot handle unknown parameters, and Kotlin add a $metadata$ property to every object
        js("delete param.\$metadata$")
        jq(element).datetimepicker(param)
    }
}

private native fun JQuery.datetimepicker(param: Any? ): Unit = noImpl;