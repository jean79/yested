package net.yested

public native trait ChartItemI {
    val value: Number
    val color: String
    val highlight: String
    val label: String
}

public data class ChartItem(
        override val value: Number,
        override val color: String,
        override val highlight: String,
        override val label: String) : ChartItemI {}

public native trait SingleLineDataI {
    val label: String
    val fillColor: String
    val strokeColor: String
    val pointColor: String
    val pointStrokeColor: String
    val pointHighlightFill: String
    val pointHighlightStroke: String
    val data: Array<Double>
}

public data class SingleLineData(
        override val label: String,
        override val fillColor: String,
        override val strokeColor: String,
        override val pointColor: String,
        override val pointStrokeColor: String,
        override val pointHighlightFill: String,
        override val pointHighlightStroke: String,
        override val data: Array<Double>) : SingleLineDataI {}

public native trait LineDataI {
    val labels: Array<String>
    val datasets: Array<SingleLineDataI>
}

public data class LineData(
        override val labels: Array<String>,
        override val datasets: Array<SingleLineDataI>) : LineDataI {}


public native class ChartNative() {
    public fun Pie(data: Array<ChartItem>): Any? = noImpl;
    public fun Line(data: LineData, options: Any?): Any? = noImpl;
}

public native("new Chart") fun jsChart(context: Context): ChartNative = ChartNative();

public class Chart(width: Int, height: Int) : Canvas(width, height) {

    fun Pie(data: Array<ChartItem>): Any? = jsChart(getContext("2d")).Pie(data)
    //fun Line(data: LineData, options: ChartOptions? = null): Any? = jsChart(getContext("2d")).Line(data, options)
    fun Line(data: LineData, options: Any? = null): Any? =
            jsChart(getContext("2d")).Line(data, options)

}

