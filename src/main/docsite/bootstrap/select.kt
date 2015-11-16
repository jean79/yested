package bootstrap

import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.Validator
import net.yested.bootstrap.btsForm
import net.yested.bootstrap.BtsButton
import net.yested.bootstrap.Select
import net.yested.Span
import net.yested.bootstrap.Medium

data class Car(val model:String, val color:String)

fun createSelectSection(id: String): Div {

    val someData = listOf(
            Car("Ford", "Black"),
            Car("Skoda", "White"),
            Car("Renault", "Red"),
            Car("Citroen", "Purple"))

    val resultSingleSelect = Div()
    val singleSelect = Select(options = someData, renderer = { "${it.model} (${it.color})" })
    singleSelect.addOnChangeListener {
        resultSingleSelect.setContent( "Selected: ${singleSelect.selectedItems.first().model}")
    }

    val resultMultiSelect = Div()
    val multiSelect = Select(options = someData, multiple = true, size = 4, renderer = { "${it.model} (${it.color})" })
    multiSelect.addOnChangeListener {
        resultMultiSelect.setContent( "Selected: " + multiSelect.selectedItems.map { "${it.model}" }.joinToString(" and "))
    }

    val btn = BtsButton(label = { +"Select Skoda and Ford" }) {
        multiSelect.selectedItems = someData.filter { it.model == "Skoda" || it.model == "Ford"}
    }

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Select" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
+"""HTML Select demo with listener."""
                }
                br()
                h4 { +"Demo" }
                + singleSelect
                + resultSingleSelect
                br()
                br()
                + multiSelect
                + resultMultiSelect
                br()
                + btn
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content=
"""val someData = listOf(
        Car("Ford", "Black"),
        Car("Skoda", "White"),
        Car("Renault", "Red"),
        Car("Citroen", "Purple"))

val resultSingleSelect = Div()
val singleSelect = Select<Car>(data = someData, renderer = { "$\{it.model} ($\{it.color})" })
singleSelect.addOnChangeListener {
    resultSingleSelect.replace( "Selected: $\{singleSelect.selectedItems.first().model}")
}

val resultMultiSelect = Div()
val multiSelect = Select<Car>(data = someData, multiple = true, size = 4, renderer = { "$\{it.model} ($\{it.color})" })
multiSelect.addOnChangeListener {
    resultMultiSelect.replace( "Selected: " + multiSelect.selectedItems.map { "$\{it.model}" }.join(" and "))
}

val btn = BtsButton(label = { +"Select Skoda and Ford" }) {
    multiSelect.selectedItems = someData.filter { it.model == "Skoda" || it.model == "Ford"}
}

...
div {
    + singleSelect
    + resultSingleSelect
    br()
    br()
    + multiSelect
    + resultMultiSelect
    br()
    + btn
}""")
            }
        }
    }

}