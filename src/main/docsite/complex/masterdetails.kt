package complex

import net.yested.bootstrap.Grid
import net.yested.bootstrap.Column
import bootstrap.Person
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.btsButton
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.Form
import net.yested.with
import net.yested.bootstrap.Validator
import net.yested.bootstrap.Select
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.Medium
import net.yested.Fade
import net.yested.bootstrap.FormStyle
import net.yested.bootstrap.Small
import net.yested.Component
import kotlin.js.dom.html.HTMLElement
import java.util.ArrayList
import net.yested.bootstrap.InputField
import net.yested.compareByValue
import net.yested.bootstrap.StringInputField

enum class Continent(val label:String) {
    EUROPE : Continent("Europe");
    AMERICA : Continent("America");
    ASIA : Continent("Asia");
    AFRICA : Continent("Africa")
}

data class City(val name:String, val continent:Continent)

class DetailScreen(
        val editedCity:City?,
        val saveHandler:(City)->Unit,
        val cancelHandler:()->Unit) : Component {

    val textInput = StringInputField(placeholder = "City name")
    val validator = Validator(inputElement = textInput, errorText = "Name is mandatory", validator = { it.size > 3})
    val select = Select(options = Continent.values().toList(), renderer = { it.label })

    fun save() {
        if (validator.isValid()) {
            saveHandler(City(textInput.data, select.selectedItems.first()))
        }
    }

    {
        if (editedCity != null) {
            textInput.data = editedCity.name
            select.selectedItems = listOf(editedCity.continent)
        }
    }

    override val element: HTMLElement
        get() = (Form(formStyle = FormStyle.HORIZONTAL, labelDef = Small(4), inputDef = Small(8)) with  {
            item(label = { +"City name"}, validator = validator) {
                +textInput
            }
            item(label = { +"Continent" }) {
                +select
            }
            item(label = {}) {
                div {
                    btsButton(label = { +"Save" }, look = ButtonLook.PRIMARY, type = ButtonType.SUBMIT, onclick = { save() })
                    nbsp()
                    btsButton(label = { +"Cancel" }, onclick = { cancelHandler() })
                }
            }
        }).element

}

class MasterScreen(val list:ArrayList<City>, val editHandler:Function1<City?, Unit>) : Component {

    val grid =
            Grid(columns = array(
                    Column(label = { +"City name"},
                            render = { +it.name }, sortFunction = compareByValue<City, String> { it.name },
                            defaultSort = true),
                    Column(label = { +"Continent"},
                            render = { +it.continent.label},
                            sortFunction = compareByValue<City, String> { it.continent.label }),
                    Column(label = { },
                            render = { btsButton(size = ButtonSize.EXTRA_SMALL, label = { +"Edit" }, onclick = { editHandler(it) })},
                            sortFunction = compareByValue<City, String> { it.name }),
                    Column(label = { },
                            render = { btsButton(size = ButtonSize.EXTRA_SMALL, look = ButtonLook.DANGER, label = { +"Delete" }, onclick = { deleteCity(it) })},
                            sortFunction = compareByValue<City, String> { it.name })));

    fun deleteCity(city: City) {
        list.remove(city)
        grid.list = list
    }

    {
        grid.list = list
    }

    override val element: HTMLElement
        get() = (Div() with {
            +grid
            btsButton(label = { +"Add" }, onclick = { editHandler(null) })
        }).element

}

class MasterDetailDemo(): Component {

    val placeholder = Div()

    val list = arrayListOf(
            City("Prague", Continent.EUROPE),
            City("London", Continent.EUROPE),
            City("New York", Continent.AMERICA))

    fun saveCity(originalCity: City?, newCity: City) {
        if (originalCity != null) {
            list.remove(originalCity)
        }
        list.add(newCity)
        displayMasterScreen()
    }

    fun editCity(city: City? = null) {
        placeholder.setChild(DetailScreen(editedCity = city, saveHandler = { saveCity(city, it) }, cancelHandler = { displayMasterScreen() }), Fade())
    }

    fun displayMasterScreen() {
        placeholder.setChild(MasterScreen(list, { editCity(it) }), Fade())
    }

    {
        displayMasterScreen()
    }

    override val element: HTMLElement
        get() = (Div() with {
            +placeholder
        }).element

}

fun masterDetail() =
    div {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Master / Detail" } }
            }
        }
        row {
            col(Medium(6)) {
                h4 { +"Demo" }
                +MasterDetailDemo()
            }
            col(Medium(6)) {
                h4 { +"Source code"}
                a(href = "https://github.com/jean79/yested/blob/master/src/main/docsite/complex/masterdetails.kt") {+"Source code is deployed on GitHub"}
            }
        }
    }