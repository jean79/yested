package demo.chartjs

import net.yested.Div
import net.yested.div
import net.yested.Chart
import net.yested.BarChartData
import net.yested.BarChartSeries
import net.yested.randomColor
import net.yested.toHTMLColor

fun createChartJSBarSection(): Div {

    val chart = Chart(width = 300, height = 250)

    val temperatureCZE = arrayOf(-2.81, -1.06, 2.80, 7.49, 12.30, 15.41, 17.11, 16.90, 13.49, 8.59, 2.82, -1.06)
    val temperatureSVK = arrayOf(-2.03, 0.85, 5.44, 10.72, 15.49, 18.52, 20.11, 19.70, 16.13, 10.81, 4.89, 0.11)

    val colorCZE = randomColor(1.0)
    val colorSVK = randomColor(1.0)

    val chartData =
            BarChartData(
                    labels = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sept", "Oct", "Nov", "Dec"),
                    datasets = arrayOf(
                            BarChartSeries(
                                    label = "Czech Re",
                                    strokeColor = colorCZE.copy(alpha = 0.8).toHTMLColor(),
                                    fillColor = colorCZE.copy(alpha = 0.5).toHTMLColor(),
                                    highlightStroke = colorCZE.copy(alpha = 1.0).toHTMLColor(),
                                    highlightFill = colorCZE.copy(alpha = 0.75).toHTMLColor(),
                                    data = temperatureCZE),
                            BarChartSeries(
                                    label = "Slovakia",
                                    strokeColor = colorSVK.copy(alpha = 0.8).toHTMLColor(),
                                    fillColor = colorSVK.copy(alpha = 0.5).toHTMLColor(),
                                    highlightStroke = colorSVK.copy(alpha = 1.0).toHTMLColor(),
                                    highlightFill = colorSVK.copy(alpha = 0.75).toHTMLColor(),
                                    data = temperatureSVK)))

    val options = object {
        val responsive = true
    }

    chart.drawBarChart(chartData, options)

    return div {
        h4 { +"Bar Chart" }
        +chart
        a(href = "https://github.com/jean79/yested/blob/master/src/main/docsite/demo/chartjs/chartjs_bar.kt", target = "_blank") { +"Source code"}
    }

}