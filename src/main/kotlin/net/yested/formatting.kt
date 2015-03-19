package net.yested

public data class Color(
        val red: Int,
        val green: Int,
        val blue: Int,
        val alpha: Double)

public fun Color.toHTMLColor(): String = "rgba(${this.red},${this.green},${this.blue},${this.alpha})"

public enum class Colors(val color:Color) {
    WHITE: Colors(Color(256, 256, 256, 1.0))
    RED: Colors(Color(256, 0, 0, 1.0))
    GREEN: Colors(Color(0, 256, 0, 1.0))
    BLUE: Colors(Color(0, 0, 256, 1.0))
}

private fun randomColorPart(): Int = (Math.random() * 256).toInt()

public fun randomColor(alpha: Double): Color =
        Color(
                red = randomColorPart(),
                green = randomColorPart(),
                blue = randomColorPart(),
                alpha = alpha)

private fun lighten(color: Int, percent: Int) =
        (color.toDouble() * (1.0 + percent.toDouble() / 100.0)).toInt()

public fun Color.lighten(percent: Int = 20): Color =
        Color(
                red = lighten(this.red, percent),
                green = lighten(this.green, percent),
                blue = lighten(this.blue, percent),
                alpha = this.alpha
        )

public class Colorized(color: Color? = null, backgroundColor: Color? = null) : HTMLComponent("span") {

    init {
        style = (if (color != null) "color: ${color.toHTMLColor()};" else "") +
                (if (backgroundColor != null) "background-color: ${backgroundColor.toHTMLColor()};" else "");
    }

}

public fun HTMLComponent.colorized(color: Color? = null, backgroundColor: Color? = null, init: HTMLComponent.() -> Unit) {
    +(Colorized(color = color, backgroundColor = backgroundColor) with { init() })
}