package ajax

import net.yested.bootstrap.TextInput
import net.yested.bootstrap.Validator
import net.yested.ajaxGet
import net.yested.Span
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.form
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.Panel
import net.yested.with
import net.yested.bootstrap.PanelStyle
import net.yested.bootstrap.ButtonGroup
import net.yested.bootstrap.Alert
import net.yested.bootstrap.AlertStyle

/**
 * Created by jean on 24.12.2014.
 */
native trait Coordinates {
    val lon : Double
    val lat : Double
}

native trait Sys {
    val type : Int
    val id : Int
    val message: Double
    val country : String
    val sunrise: Int
    val sunset: Int
}

native trait Weather {
    val id: Int
    val main: String
    val description : String
    val icon: String
}

native trait Main {
    val temp : Double
    val pressure : Int
    val humidity: Int
    val temp_min : Double
    val temp_max : Double
}

native trait Wind {
    val speed : Double
    val deg : Int
}

native trait Clouds {
    val all : Int
}

native trait WeatherData {
    val coord: Coordinates?
    val sys: Sys?
    val weather: Array<Weather>?
    val base: String?
    val main : Main?
    val wind : Wind?
    val clouds : Clouds?
    val dt: Long?
    val id: Int?
    val name: String?
    val cod : Int?
}

fun createAjaxGetSection(): Div {

    val textInput = TextInput(placeholder = "Type city name and press Enter")
    val validator = Validator(inputElement = textInput, errorText = "Enter at least 3 characters", validator = { it.length() > 2})
    val temperatureSpan = Div()

    val btnGroup = ButtonGroup() with {
        button("metric", label = { + "Celcius"})
        button("imperial", label = { + "Fahrenheit"})
    }
    btnGroup.select("metric")

    fun fetchWeather() {
        if (validator.isValid()) {
            ajaxGet<WeatherData>("http://api.openweathermap.org/data/2.5/weather?q=${textInput.value}&units=${btnGroup.value}") {
                weatherData ->
                if (weatherData != null && weatherData.main != null) {
                    temperatureSpan.replace(
                            Panel(panelStyle = PanelStyle.SUCCESS) with {
                                heading { +"Temperature in ${weatherData.name}" }
                                content { emph { +"${weatherData.main!!.temp}"} }
                            })
                } else {
                    temperatureSpan.replace( Alert(style = AlertStyle.DANGER) with { +"Location not found" })
                }
            }
        }
    }

    return div {
        row {
            col(12) {
                pageHeader { h3 { +"Ajax Get"} }
            }
        }
        row {
            col(6) {
                +"Yested provides JQuery Ajax wrappers:"
                code(lang = "kotlin") {
+"""ajaxGet&lt;ResponseType&gt;(url) {
    response -> do something with response
}"""
                }
                br()
                +"""ResponseType is a native trait. It is a special Kotlin interface.
                    Kotlin data classes cannot be used here as JQuery returns simple JS object parsed from JSON response."""
                code(lang = "kotlin") {
+"""native trait Coordinates {
    val lon : Double
    val lat : Double
}
"""
                }
            }

        }
        row {
            col(12) {
                pageHeader { h4 { +"Demo" }}
            }
        }
        row {
            col(6) {
                form(labelDef = "col-sm-4", inputDef = "col-sm-8") {
                    item(label = { +"Location"}, validator = validator) {
                        +textInput
                    }
                    item(label = { +"Units"}) {
                        +btnGroup
                    }
                    item(label = { }) {
                        btsButton(type = ButtonType.SUBMIT, label = { +"Get Weather"}, look = ButtonLook.PRIMARY) {
                            fetchWeather()
                        }
                    }
                }
            }
            col(6) {
                p {
                    +temperatureSpan
                }
            }
        }
        row {
            col(12) {
                pageHeader { h4 { +"Source for demo" }}
                code(lang = "kotlin") {
+"""//definition of response, just fragment
native trait Main {
    val temp : Double
    val pressure : Int
    val humidity: Int
    val temp_min : Double
    val temp_max : Double
}

native trait WeatherData {
    ...
    val base: String?
    val main : Main?
    val wind : Wind?
    ...
}

...
val textInput = TextInput(placeholder = "Type city name and press Enter")
val validator = Validator(inputElement = textInput, errorText = "Enter at least 3 characters", validator = { it.length() > 2})
val temperatureSpan = Div()

val btnGroup = ButtonGroup() with {
    button("metric", label = { + "Celcius"})
    button("imperial", label = { + "Fahrenheit"})
}
btnGroup.select("metric")

fun fetchWeather() {
    if (validator.isValid()) {
        ajaxGet&lt;WeatherData&gt;("http://api.openweathermap.org/data/2.5/weather?q=$\{textInput.value}&units=$\{btnGroup.value}") {
            weatherData ->
            if (weatherData != null && weatherData.main != null) {
                temperatureSpan.replace(
                        Panel(panelStyle = PanelStyle.SUCCESS) with {
                            heading { +"Temperature in $\{weatherData.name}" }
                            content { emph { +"$\{weatherData.main!!.temp}"} }
                        })
            } else {
                temperatureSpan.replace("Location not found")
            }
        }
    }
}
...
div {
    form(labelDef = "col-sm-4", inputDef = "col-sm-8") {
        item(label = { +"Location"}, validator = validator) {
            +textInput
        }
        item(label = { +"Units"}) {
            +btnGroup
        }
        item(label = { }) {
            btsButton(type = ButtonType.SUBMIT, label = { +"Get Weather"}, look = ButtonLook.PRIMARY) {
                fetchWeather()
            }
        }
    }
}
"""
                }
            }
        }
    }

}