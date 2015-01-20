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
import net.yested.bootstrap.TextInput
import net.yested.bootstrap.Validator
import net.yested.bootstrap.Select
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.Medium

enum class Continent(val label:String) {
    EUROPE : Continent("Europe");
    AMERICA : Continent("America");
    ASIA : Continent("Asia");
    AFRICA : Continent("Africa")
}

data class City(val name:String, val continent:Continent)

class MasterDetail {

    val placeholder = Div()

    val list = arrayListOf(
                    City("Prague", Continent.EUROPE),
                    City("London", Continent.EUROPE),
                    City("New York", Continent.AMERICA))

    val grid = Grid(columns = array(
            Column(label = { +"City name"},
                    render = { +it.name }, sortFunction = bootstrap.compareBy<City, String> { it.name },
                    defaultSort = true),
            Column(label = { +"Continent"},
                    render = { +it.continent.label},
                    sortFunction = bootstrap.compareBy<City, String> { it.continent.label }),
            Column(label = { },
                    render = { btsButton(size = ButtonSize.EXTRA_SMALL, label = { +"Edit" }, onclick = { edit(it) })},
                    sortFunction = bootstrap.compareBy<City, String> { it.name }),
            Column(label = { },
                    render = { btsButton(size = ButtonSize.EXTRA_SMALL, look = ButtonLook.DANGER, label = { +"Delete" }, onclick = { delete(it) })},
                    sortFunction = bootstrap.compareBy<City, String> { it.name })))

    fun delete(city:City) {
        list.remove(city)
        grid.list = list
    }

    fun edit(editedCity:City? = null) {

        val textInput = TextInput(placeholder = "City name")
        val validator = Validator(inputElement = textInput, errorText = "Name is mandatory", validator = { it.size > 3})
        val select = Select<Continent>(renderer = { it.label })
        select.data = Continent.values().toList()

        fun close() {
            placeholder.setContentWithFadeEffect( createMasterView() )
        }

        fun save() {
            if (validator.isValid()) {
                if (editedCity != null) {
                    list.remove(editedCity)
                }
                list.add(City(textInput.value, select.selectedItems.first()))
                grid.list = list
                close()
            }
        }

        if (editedCity != null) {
            textInput.value = editedCity.name
            select.selectedItems = listOf(editedCity.continent)
        }

        placeholder.setContentWithFadeEffect(
            Form(labelDef = "col-sm-4", inputDef = "col-sm-8") with  {
                item(label = { +"City name"}, validator = validator) {
                    +textInput
                }
                item(label = { +"Continent" }) {
                    +select
                }
                item(label = {}) {
                    div {
                        btsButton(label = { +"Save" }, look = ButtonLook.PRIMARY, type = ButtonType.SUBMIT, onclick = ::save)
                        btsButton(label = { +"Cancel" }, onclick = ::close)
                    }
                }
            })

    }

    fun createMasterView() =
        div {
            +grid
            btsButton(label = { +"Add"}, onclick = { edit()})
        }

    fun createDiv(): Div {

        placeholder.setContentWithFadeEffect(createMasterView())
        grid.list = list

        return div {
            row {
                col(Medium(12)) {
                    pageHeader { h3 { +"Master / Detail" } }
                }
            }
            row {
                col(Medium(6)) {
                    h4 { +"Demo" }
                    +placeholder
                }
                col(Medium(6)) {
                    h4 { +"Source code"}
                    a(href = "https://github.com/jean79/yested/blob/master/src/main/docsite/complex/masterdetails.kt") {+"Source code is deployed on GitHub"}
                }
            }
        }

    }

}

fun masterDetail(): Div =
    MasterDetail().createDiv()

