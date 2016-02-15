package net.yested

 interface Dimension {
    fun toHtml():String
}

 class Percent(val value:Double) : Dimension {

    override fun toHtml(): String {
        return "${value}%"
    }

}

 fun Int.pct():Percent = Percent(this.toDouble())
 fun Double.pct():Percent = Percent(this)

 class Pixels(val value:Int) : Dimension {

    override fun toHtml(): String {
        return "${value}px"
    }

}

 fun Int.px():Pixels = Pixels(this)
