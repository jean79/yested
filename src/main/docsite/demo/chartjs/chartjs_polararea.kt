package demo.chartjs

import net.yested.Div
import net.yested.div
import net.yested.Chart
import net.yested.PieChartSeries
import net.yested.randomColor
import net.yested.toHTMLColor
import net.yested.lighten
import net.yested.PolarAreaChartSeries

class Data2(val countryCode:String, val temperature:Double)

fun createPolarChartSection(): Div {

    val chart = Chart(width = 300, height = 250)

    val temperaturesData = arrayOf(
            Data2("BEL", 9.51),
            Data2("BEN", 15.46),
            Data2("BFA", 28.18),
            Data2("BGD", 66.47))

    val chartData:Array<PolarAreaChartSeries> = temperaturesData.map {
        val color = randomColor(1.0)
        PolarAreaChartSeries(
                value = it.temperature,
                color = color.toHTMLColor(),
                highlight = color.lighten(30).toHTMLColor(),
                label = it.countryCode
        )
    }.toTypedArray()

    val options = object {
        val responsive = true
    }

    chart.drawPolarAreaChart(chartData, options)

    return div {
        h4 { +"Polar Area Chart"}
        +chart
        a(href = "https://github.com/jean79/yested/blob/master/src/main/docsite/demo/chartjs/chartjs_polararea.kt", target = "_blank") { +"Source code"}
    }

}