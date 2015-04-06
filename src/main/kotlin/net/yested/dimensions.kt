package net.yested

public trait Dimension {
    fun toHtml():String
}

public class Percent(val value:Double) : Dimension {

    override fun toHtml(): String {
        return "${value}%"
    }

}

public fun Int.pct():Percent = Percent(this.toDouble())
public fun Double.pct():Percent = Percent(this)

public class Pixels(val value:Int) : Dimension {

    override fun toHtml(): String {
        return "${value}px"
    }

}

public fun Int.px():Pixels = Pixels(this)
