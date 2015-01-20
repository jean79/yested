package net.yested

public data class Color(
        val red: Int,
        val green: Int,
        val blue:Int,
        val alpha:Double)

public fun Color.toHTMLColor(): String = "rgba(${this.red},${this.green},${this.blue},${this.alpha})"

public class Colorized(color:Color? = null, backgroundColor: Color? = null, init: HTMLComponent.() -> Unit) : HTMLComponent("span") {

    {
        style = (if (color != null) "color: ${color.toHTMLColor()};" else "") +
                (if (backgroundColor != null) "background-color: ${backgroundColor.toHTMLColor()};" else "");
        this.init()
    }

}