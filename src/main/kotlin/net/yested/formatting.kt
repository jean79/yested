package net.yested

data class Color(
        val red: Int,
        val green: Int,
        val blue:Int,
        val alpha:Double)

fun Color.toHTMLColor() = "rgba(${this.red},${this.green},${this.blue},${this.alpha})"

class Colorized(color:Color? = null, backgroundColor: Color? = null, init: HTMLParentComponent.() -> Unit) : Span() {

    {
        style = (if (color != null) "color: ${color.toHTMLColor()};" else "") +
                (if (backgroundColor != null) "background-color: ${backgroundColor.toHTMLColor()};" else "");
        this.init()
    }

}