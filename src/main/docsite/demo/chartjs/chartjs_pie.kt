package demo.chartjs

import net.yested.Div
import net.yested.div
import net.yested.Chart
import net.yested.PieChartSeries
import net.yested.randomColor
import net.yested.toHTMLColor
import net.yested.lighten

class Data(val countryCode:String, val temperature:Double)

fun createPieChartSection(): Div {

    val chart = Chart(width = 300, height = 250)

    val temperaturesData = arrayOf(
        Data("BEL", 9.51),
        Data("BEN", 27.46),
        Data("BFA", 28.18),
        Data("BGD", 25.47),
        Data("BGR", 10.40),
        Data("BHS", 25.06),
        Data("BIH", 9.02),
        Data("BLR", 6.29),
        Data("BLZ", 25.06),
        Data("BOL", 20.98),
        Data("BRA", 24.92),
        Data("BRN", 25.93),
        Data("BTN", 8.58),
        Data("BWA", 21.48),
        Data("CAF", 24.84))

    val chartData:Array<PieChartSeries> = temperaturesData.map {
        val color = randomColor(1.0)
        PieChartSeries(
                value = it.temperature,
                color = color.toHTMLColor(),
                highlight = color.lighten(30).toHTMLColor(),
                label = it.countryCode
        )
    }.toTypedArray()

    val options = object {
        val responsive = true
    }

    chart.drawPieChart(chartData, options)

    return div {
        h4 { +"Pie Chart"}
        +chart
        a(href = "https://github.com/jean79/yested/blob/master/src/main/docsite/demo/chartjs/chartjs_pie.kt", target = "_blank") { +"Source code"}
    }

}