package complex

import net.yested.*
import net.yested.bootstrap.*
import org.w3c.dom.HTMLElement
import java.util.*

enum class Continent(val label:String) {
    EUROPE("Europe"),
    AMERICA("America"),
    ASIA("Asia"),
    AFRICA("Africa")
}

data class City(val name:String, val continent:Continent)

class DetailScreen(
        val editedCity:City?,
        val saveHandler:(City)->Unit,
        val cancelHandler:()->Unit) : Component {

    val textInput = StringInputField(placeholder = "City name")
    val validator = Validator(inputElement = textInput, errorText = "Name is mandatory", validator = { it.size > 3})
    val continents = arrayListOf(Continent.EUROPE, Continent.AFRICA, Continent.AMERICA, Continent.ASIA)
    val select = Select(options = continents, renderer = { it.label })

    fun save() {
        if (validator.isValid()) {
            saveHandler(City(textInput.data, select.selectedItems.first()))
        }
    }

    init {
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
            Grid(columns = arrayOf(
                    Column(label = { +"City name"},
                            render = { +it.name }, sortFunction = compareByValue<City, String> { it.name },
                            defaultSort = true),
                    Column(label = { +"Continent"},
                            render = { +it.continent.label},
                            sortFunction = compareByValue<City, String> { it.continent.label }),
                    Column(label = { },
                            render = { city -> btsButton(size = ButtonSize.EXTRA_SMALL, label = { +"Edit" }, onclick = { editHandler(city) })},
                            sortFunction = compareByValue<City, String> { it.name }),
                    Column(label = { },
                            render = { city -> btsButton(size = ButtonSize.EXTRA_SMALL, look = ButtonLook.DANGER, label = { +"Delete" }, onclick = { deleteCity(city) })},
                            sortFunction = compareByValue<City, String> { it.name })));

    fun deleteCity(city: City) {
        list.remove(city)
        grid.list = list
    }

    init {
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

    init {
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