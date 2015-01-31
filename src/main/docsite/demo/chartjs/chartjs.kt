package demo.chartjs

import net.yested.Div
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.Medium

fun chartJsPage(): Div {

    return div {
            row {
                col(Medium(8)) {
                    pageHeader {
                        h3 { +"ChartJS" }
                    }
                    p {
                        +"Yested provides wrappers for ChartJS library."
                        a(href = "http://www.chartjs.org/") { +"http://www.chartjs.org/" }
                    }
                    p {
                        +"First initialize Chart class, then create any of supported charts."
                        br()
                        +"Yested provides strongly-typed wrappers for ChartJS data but not for options."
                    }
                    code(lang="kotlin", content=
"""//Chart extends Component and can be added anywhere in DOM
val chart = Chart(width = 300, height = 250)

//create array of chart data
val data = array(
        PieChartSeries(
                value = 15.1,
                color = "#F7464A",
                highlight = "#F7464A",
                label = "Red"
        ),
        PieChartSeries(
                value = 5.1,
                color = "#F7464A",
                highlight = "#F7464A",
                label = "Blue"
        ))

//Yested does not provide strongly-typed API for options as this may change quickly with another version of ChartJS.
val options = object {
    val responsive = true
}

//this value is set by handler in next method
var pieChart:dynamic

chart.drawPieChart(data, options, { pieChart = it })

//you can manipulate with chart once it is created - check ChartJS page
pieChart.addData(PieChartSeries(..)
""")
                }
            }
            br()
            row {
                col(Medium(4)) {
                    +createChartJSLineSection()
                }
                col(Medium(4)) {
                    +createPieChartSection()
                }
                col(Medium(4)) {
                    +createChartJSBarSection()
                }
            }
            br()
            row {
                col(Medium(4)) {
                    +createChartJSRadarSection()
                }
                col(Medium(4)) {
                    +createPolarChartSection()
                }
                col(Medium(4)) {
                    +createDoughnutChartSection()
                }
            }
        }
}

