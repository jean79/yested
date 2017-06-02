package net.yested.utils

@JsName("moment")
external private fun moment_js(): MomentJs = definedExternally
@JsName("moment")
external private fun moment_js(millisecondsSinceUnixEpoch: Long): MomentJs = definedExternally
@JsName("moment")
external private fun moment_js(input: String, format: String): MomentJs = definedExternally

@JsName("Moment")
external class MomentJs {
    fun format(formatString: String? = definedExternally): String = definedExternally
    fun valueOf(): Long = definedExternally
    fun millisecond(value: Int? = definedExternally): Int = definedExternally
    fun second(value: Int? = definedExternally): Int = definedExternally
    fun minute(value: Int? = definedExternally): Int = definedExternally
    fun hour(value: Int? = definedExternally): Int = definedExternally
    fun date(value: Int? = definedExternally): Int = definedExternally
    fun day(value: Int? = definedExternally): Int = definedExternally
    fun weekday(value: Int? = definedExternally): Int = definedExternally
    fun isoWeekday(value: Int? = definedExternally): Int = definedExternally
    fun dayOfYear(value: Int? = definedExternally): Int = definedExternally
    fun week(value: Int? = definedExternally): Int = definedExternally
    fun isoWeek(value: Int? = definedExternally): Int = definedExternally
    fun month(value: Int? = definedExternally): Int = definedExternally
    fun quarter(value: Int? = definedExternally): Int = definedExternally
    fun year(value: Int? = definedExternally): Int = definedExternally
    fun weekYear(value: Int? = definedExternally): Int = definedExternally
    fun isoWeekYear(value: Int? = definedExternally): Int = definedExternally
    fun weeksInYear(): Int = definedExternally
    fun locale(localeName: String): Unit = definedExternally
    fun unix():Int = definedExternally
    fun unix(t:Int): Unit = definedExternally
}

 class Moment(private val moment: MomentJs) {

     fun format(format: String): String = moment.format(format)
     fun format(format: FormatString): String = moment.format(format.toString())

     val millisecondsSinceUnixEpoch: Long
		get() = moment.valueOf()

     var unix: Int
        get() = moment.unix()
        set(value) {
            moment.unix(value)
        }

     var millisecond: Int
        get() = moment.millisecond()
        set(value) {
            moment.millisecond(value)
        }
     var second: Int
        get() = moment.second()
        set(value) {
            moment.second(value)
        }
     var minute: Int
        get() = moment.minute()
        set(value) {
            moment.minute(value)
        }
     var hour: Int
        get() = moment.hour()
        set(value) {
            moment.hour(value)
        }
     var dayOfMonth: Int
        get() = moment.date()
        set(value) {
            moment.date(value)
        }
     var dayOfYear: Int
        get() = moment.dayOfYear()
        set(value) {
            moment.dayOfYear(value)
        }
     var month: Int
        get() = moment.month()
        set(value) {
            moment.month(value)
        }
     var year: Int
        get() = moment.year()
        set(value) {
            moment.year(value)
        }

    companion object {
         fun now(): Moment = Moment(moment_js())

         fun parse(input: String, format: String): Moment = Moment(moment_js(input, format))

         fun parseMillisecondsSinceUnixEpoch(millisecondsSinceUnixEpoch: Long): Moment{
			requireNotNull(millisecondsSinceUnixEpoch)
			return Moment(moment_js(millisecondsSinceUnixEpoch))
		}

        fun setLocale(localeName: String) {
            moment_js().locale(localeName)
        }
    }
}

 class FormatElement (val str: String) {

     fun plus(b: FormatElement): FormatString {
        return FormatString(arrayListOf(this, b))
    }

    operator fun plus(b: String): FormatString {
        return FormatString(arrayListOf(this, FormatElement(b)))
    }
}

 class FormatString(private val elements: MutableList<FormatElement> = arrayListOf()) {

     operator fun plus(b: FormatElement): FormatString {
        elements.add(b)
        return FormatString(elements)
    }

     operator fun plus(b: String): FormatString {
        elements.add(FormatElement(b))
        return FormatString(elements)
    }

    override fun toString(): String = elements.map { it.str }.joinToString(separator = "")
}

class Digit(private val oneDigitFactory: ()->FormatElement, private val twoDigitsFactory: ()->FormatElement, private val fourDigitsFactory: ()->FormatElement) {
	val oneDigit: FormatElement
		get() = oneDigitFactory()
	val twoDigits: FormatElement
		get() = twoDigitsFactory()
	val fourDigits: FormatElement
		get() = fourDigitsFactory()
}

 class FormatStringBuilder() {

	 val year: Digit = Digit({throw UnsupportedOperationException("bla")}, {FormatElement("YY")}, {FormatElement("YYYY")})
	 val month: Digit = Digit({FormatElement("M")}, {FormatElement("MM")}, {throw UnsupportedOperationException()})
	 val dayOfMonth: Digit = Digit({FormatElement("D")}, {FormatElement("DD")}, {throw UnsupportedOperationException()})
	 val hour24: Digit = Digit({FormatElement("H")}, {FormatElement("HH")}, {throw UnsupportedOperationException()})
	 val hour12: Digit = Digit({FormatElement("h")}, {FormatElement("hh")}, {throw UnsupportedOperationException()})
	 val minutes: Digit = Digit({FormatElement("m")}, {FormatElement("mm")}, {throw UnsupportedOperationException()})
	 val seconds: Digit = Digit({FormatElement("s")}, {FormatElement("ss")}, {throw UnsupportedOperationException()})
}

 fun format(init: FormatStringBuilder.() -> FormatString): FormatString {
    return FormatStringBuilder().init()
}