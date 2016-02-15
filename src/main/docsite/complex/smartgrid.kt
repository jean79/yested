package complex

import jquery.jq
import net.yested.*
import net.yested.bootstrap.*
import net.yested.bootstrap.smartgrid.CellEditorFactory
import net.yested.bootstrap.smartgrid.GridColumn
import net.yested.bootstrap.smartgrid.SmartGrid
import net.yested.bootstrap.smartgrid.TextInputFilterFactory
import net.yested.utils.isIncludedInDOM
import net.yested.utils.keypress
import net.yested.utils.on
import org.w3c.dom.HTMLElement
import kotlin.browser.window

data class MarketData(val ticker:String, val country:String, val ccy:String, var price:Double, var move:Double,
                      val min:Double, val max:Double, val avg:Double, val quantity:Double,
                      val fair:Double, val ask:Double,
                      val col1:Double, val col2:Double, val col3:Double, val col4:Double, val col5:Double,
                      val col6:Double, val col7:Double, val col8:Double, val col9:Double, val col10:Double,
                      val col11:Double, val col12:Double, val col13:Double, val col14:Double, val col15:Double)

fun HTMLComponent.coloredNumber(value:Double) {
    +(Span() with {
        "style".."color: ${if (value>0) "green" else "red"}"
        +"${value.toFixed(2)}"
    })
}

@native fun HTMLElement.focus() : Unit = noImpl

 class DoubleEditor<TYPE>(
        val getValue:(TYPE)->Double?,
        val saveValue:(TYPE, Double)->Unit) : CellEditorFactory<TYPE> {

    override fun createEditor(width: String, item: TYPE, closeHandler: () -> Unit): HTMLElement {

        var closeCalled = false

        val inputField = TextInput() with {
            element.setAttribute("style", "max-width: ${width}")
        }

        repeatWithDelayUntil(
                check = { isIncludedInDOM(inputField.element) },
                millisecondInterval = 100,
                run = { inputField.element.focus() })

        jq(inputField.element).on("focusout", {
            if (!closeCalled) {
                closeHandler() //TODO: tohle se nesmi volat dvakrat!
            }
        })

        jq(inputField.element).keypress( { event:dynamic ->
            val d = safeParseDouble(inputField.data)
            if (event.which == 13 && d != null) {
                closeCalled = true
                closeHandler()
                saveValue(item, d)
            }
        })

        inputField.data = "${getValue(item)?.toFixed(2)}"

        return inputField.element
    }

}

val currencies = arrayOf("CZK", "USD", "ZAR", "GBP", "AUD")
val countries = arrayOf("Czech Re", "USA", "Austria", "Hungary")

fun generateData() =
        (1..500)
                .mapIndexed { index, it ->
                    MarketData(
                            ticker= "A${it}",
                            ccy = currencies[index % currencies.size],
                            country = countries[index % countries.size],
                            price = Math.random()*100,
                            min = Math.random()*100,
                            max = Math.random()*100,
                            avg = Math.random()*100,
                            fair = Math.random()*100,
                            ask = Math.random()*100,
                            quantity = Math.random()*1000,
                            move = Math.random()*10-5,
                            col1 = Math.random()*100,
                            col2 = Math.random()*100,
                            col3 = Math.random()*100,
                            col4 = Math.random()*100,
                            col5 = Math.random()*100,
                            col6 = Math.random()*100,
                            col7 = Math.random()*100,
                            col8 = Math.random()*100,
                            col9 = Math.random()*100,
                            col10 = Math.random()*100,
                            col11 = Math.random()*100,
                            col12 = Math.random()*100,
                            col13 = Math.random()*100,
                            col14 = Math.random()*100,
                            col15 = Math.random()*100) }
                .toMutableList()



class CustomizableGridSection: Component {

    override val element: HTMLElement
        get() = content.element

    val grid = SmartGrid<MarketData, String>(
            getKey = {it.ticker},
            columns = arrayOf
                    (GridColumn(id = "ticker", width = "100px", label = "Ticker", render = { +it.ticker },
                            filterFactory = TextInputFilterFactory { text -> { item:MarketData -> item.ticker.contains(text)} },
                            sortFunction = compareByValue<MarketData, String> { it.ticker },
                            groupBy = { it.ticker.substring(0, 2)}),
                    GridColumn(id = "country", width = "140px", label = "Country", render = { +it.country },
                            filterFactory = TextInputFilterFactory { text -> { item:MarketData -> item.country.contains(text)} },
                            sortFunction = compareByValue<MarketData, String> { it.country },
                            groupBy = { it.country }),
                    GridColumn(id = "ccy", width = "90px", label = "Ccy", render = { +it.ccy },
                            filterFactory = TextInputFilterFactory { text -> { item:MarketData -> item.ccy.contains(text)} },
                            sortFunction = compareByValue<MarketData, String> { it.ccy },
                            groupBy = { it.ccy }),
                    GridColumn(id = "price",  width = "80px", label = "Price", render = { +"${it.price.toFixed(2)}" },
                            filterFactory = TextInputFilterFactory { text -> val value = parseInt(text); { item:MarketData -> item.price >= value} },
                            sortFunction = compareByValue<MarketData, Double> { it.price },
                            editor = DoubleEditor(getValue = {it.price}, saveValue = {item, value -> updateItem(item.copy(price = value), "price")} ) ),
                    GridColumn(id = "move", width = "80px", label = "Move", render = { coloredNumber(it.move) }, sortFunction = compareByValue<MarketData, Double> { it.move }, getNumber = { it.move }),
                    GridColumn(id = "min", width = "80px", label = "Min", render = { +"${it.min.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.min }, getNumber = { it.min }),
                    GridColumn(id = "max", width = "80px", label = "Max", render = { +"${it.max.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.max }),
                    GridColumn(id = "avg", width = "80px", label = "Avg", render = { +"${it.avg.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.avg }),
                    GridColumn(id = "fair", width = "80px", label = "Fair", render = { +"${it.fair.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.fair }),
                    GridColumn(id = "ask", width = "80px", label = "Ask", render = { +"${it.ask.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.ask }),
                    GridColumn(id = "quantity", width = "80px", label = "Quantity", render = { +"${it.quantity.toFixed(0)}" }, sortFunction = compareByValue<MarketData, Double> { it.quantity }),
                    GridColumn(id = "col1", width = "80px", label = "Col 1", render = { +"${it.col1.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col1 }),
                    GridColumn(id = "col2", width = "80px", label = "Col 2", render = { +"${it.col2.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col2 }),
                    GridColumn(id = "col3", width = "80px", label = "Col 3", render = { +"${it.col3.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col3 }),
                    GridColumn(id = "col4", width = "80px", label = "Col 4", render = { +"${it.col4.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col4 }),
                    GridColumn(id = "col5", width = "80px", label = "Col 5", render = { +"${it.col5.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col5 }),
                    GridColumn(id = "col6", width = "80px", label = "Col 6", render = { +"${it.col6.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col6 }),
                    GridColumn(id = "col7", width = "80px", label = "Col 7", render = { +"${it.col7.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col7 }),
                    GridColumn(id = "col8", width = "80px", label = "Col 8", render = { +"${it.col8.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col8 }),
                    GridColumn(id = "col9", width = "80px", label = "Col 9", render = { +"${it.col9.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col9 }),
                    GridColumn(id = "col10", width = "80px", label = "Col 10", render = { +"${it.col10.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col10 }),
                    GridColumn(id = "col11", width = "80px", label = "Col 11", render = { +"${it.col11.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col11 }),
                    GridColumn(id = "col12", width = "80px", label = "Col 12", render = { +"${it.col12.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col12 }),
                    GridColumn(id = "col13", width = "80px", label = "Col 13", render = { +"${it.col13.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col13 }),
                    GridColumn(id = "col14", width = "80px", label = "Col 14", render = { +"${it.col14.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col14 }),
                    GridColumn(id = "col15", width = "80px", label = "Col 15", render = { +"${it.col15.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col15 })
            ))

    var timerId : Int? = null

    val data = generateData()

    val tickingToggleButton = BtsButton(label = { +"Toggle ticking"}, size = ButtonSize.SMALL, onclick = { toogleTicking() })

    fun updateItem(item:MarketData, columnToUpdate:String) {
        grid.updateItem(item, listOf(columnToUpdate))
    }

    val content = div {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Smart Grid" } }
            }
        }
        row {
            col(Small(6)) {
                +"Smart Grid is a fully customizable grid with a lot of fancy features."
                br()
                br()
                emph { +"Features:" }
                ul {
                    li { +"Mobile (small screens) support." }
                    li { +"Support vertical and horizontal scrolling." }
                    li { +"Can handle 100k of rows as it implements Virtual Rendering."}
                    li { +"Supports fast updates of items (live ticking)." }
                    li { +"Supports fully customizable inline cell editors." }
                    li { +"Cell can contains any content, grid supports fully customizable cell renderers." }
                    li { +"Sorting (optional per cell) is enabled via provided sorting functions." }
                    li { +"Columns can be re-sorted via Drag&amp;Drop." }
                    li { +"Columns visibility is set in a dedicated Configuration Dialog." }
                    li { +"Support customizable filtering." }
                    li { +"Support customizable aggregation and aggregation functions.." }
                }
            }
            col(Small(6)) {
                emph { +"Please note!" }
                ul {
                    li { +"When update is delivered into a grid, it disables sorting due to performance reasons." }
                }
                br()
            }
        }
        row {
            col(Medium(12)) {
                h4 { +"Demo" }
                a(href = "https://github.com/jean79/yested/blob/master/src/main/docsite/complex/smartgrid.kt") { +"Source code is deployed on GitHub" }
                br()
                +tickingToggleButton
                br()
                +" Click the button to start ticking of Move column."
                br()
                +" Click any cell in a Price column to edit it."
                div {
                    style = "width: 100%; height: 300px; position: relative"
                    +grid
                }
            }
        }
    }

    init {
        grid.list = data
    }

    fun updateRandomValues() {
        (0..Math.min(100, (data.size/5))).forEach {
            val item = data[(Math.random()*data.size).toInt()]
            item.move = Math.random()*10-5
            grid.updateItem(item, listOf("move"))
        }
    }

    fun toogleTicking() {
        if (timerId == null) {
            startTicking()
            tickingToggleButton.active = true
        } else {
            stopTicking()
            tickingToggleButton.active = false
        }
    }

    fun startTicking() {
        timerId = window.setInterval( { updateRandomValues() }, 1000)
    }

    fun stopTicking() {
        if (timerId != null) {
            window.clearInterval(timerId!!)
            timerId = null
        }
    }

}

