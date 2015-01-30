package demo.chartjs

import net.yested.Div
import net.yested.div
import net.yested.Chart
import net.yested.PieChartSeries
import net.yested.randomColor
import net.yested.toHTMLColor
import net.yested.lighten

class Data3(val countryCode:String, val temperature:Double)

/**
 * Created by jean on 30.1.2015.
 */
fun createDoughnutChartSection(): Div {

    val chart = Chart(width = 300, height = 250)

    val temperaturesData = array(
            Data3("BEL", 9.51),
            Data3("BEN", 27.46),
            Data3("BFA", 28.18),
            Data3("BGD", 25.47),
            Data3("BGR", 10.40),
            Data3("BHS", 25.06),
            Data3("BIH", 9.02),
            Data3("BLR", 6.29),
            Data3("BLZ", 25.06),
            Data3("BOL", 20.98),
            Data3("BRA", 24.92),
            Data3("BRN", 25.93),
            Data3("BTN", 8.58),
            Data3("BWA", 21.48),
            Data3("CAF", 24.84))

    val chartData:Array<PieChartSeries> = temperaturesData.map {
        val color = randomColor(1.0)
        PieChartSeries(
                value = it.temperature,
                color = color.toHTMLColor(),
                highlight = color.lighten(30).toHTMLColor(),
                label = it.countryCode
        )
    }.copyToArray()

    val options = object {
        val responsive = true
    }

    chart.drawDoughnutChart(chartData, options)

    return div {
        h4 { +"Doughnut Chart"}
        +chart
    }

}