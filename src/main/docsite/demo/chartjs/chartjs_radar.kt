package demo.chartjs

import net.yested.Div
import net.yested.div
import net.yested.Chart
import net.yested.BarChartData
import net.yested.BarChartSeries
import net.yested.randomColor
import net.yested.toHTMLColor
import net.yested.RadarChartSeries
import net.yested.RadarChartData
import net.yested.Colors

/**
 * Created by jean on 30.1.2015.
 */
fun createChartJSRadarSection(): Div {

    val chart = Chart(width = 300, height = 250)

    val dataSet1 = arrayOf(65.0, 59.0, 90.0, 81.0, 56.0, 55.0, 40.0)
    val dataSet2 = arrayOf(28.0, 48.0, 40.0, 19.0, 96.0, 27.0, 100.0)

    val colorDataSet1 = randomColor(1.0)
    val colorDataSet2 = randomColor(1.0)

    val chartData =
            RadarChartData(
                    labels = arrayOf("Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"),
                    datasets = arrayOf(
                            RadarChartSeries(
                                    label = "Data Set 1",
                                    fillColor = colorDataSet1.copy(alpha = 0.2).toHTMLColor(),
                                    strokeColor = colorDataSet1.toHTMLColor(),
                                    pointColor = colorDataSet1.toHTMLColor(),
                                    pointStrokeColor = Colors.WHITE.color.toHTMLColor(),
                                    pointHighlightFill = Colors.WHITE.color.toHTMLColor(),
                                    pointHighlightStroke = colorDataSet1.toHTMLColor(),
                                    data = dataSet1),
                            RadarChartSeries(
                                    label = "Data Set 2",
                                    fillColor = colorDataSet2.copy(alpha = 0.2).toHTMLColor(),
                                    strokeColor = colorDataSet2.toHTMLColor(),
                                    pointColor = colorDataSet2.toHTMLColor(),
                                    pointStrokeColor = Colors.WHITE.color.toHTMLColor(),
                                    pointHighlightFill = Colors.WHITE.color.toHTMLColor(),
                                    pointHighlightStroke = colorDataSet2.toHTMLColor(),
                                    data = dataSet2)))

    val options = object {
        val responsive = true
    }

    chart.drawRadarChart(chartData, options)

    return div {
        h4 { +"Radar Chart" }
        +chart
        a(href = "https://github.com/jean79/yested/blob/master/src/main/docsite/demo/chartjs/chartjs_radar.kt", target = "_blank") { +"Source code"}
    }

}