package net.yested.utils


@native("moment") private fun moment_js(): MomentJs = noImpl
@native("moment") private fun moment_js(millisecondsSinceUnixEpoch: Long): MomentJs = noImpl
@native("moment") private fun moment_js(input: String, format: String): MomentJs = noImpl

@native("Moment")
class MomentJs {
    fun format(formatString: String? = null): String = noImpl
    fun valueOf(): Long = noImpl
    fun millisecond(value: Int? = null): Int = noImpl
    fun second(value: Int? = null): Int = noImpl
    fun minute(value: Int? = null): Int = noImpl
    fun hour(value: Int? = null): Int = noImpl
    fun date(value: Int? = null): Int = noImpl
    fun day(value: Int? = null): Int = noImpl
    fun weekday(value: Int? = null): Int = noImpl
    fun isoWeekday(value: Int? = null): Int = noImpl
    fun dayOfYear(value: Int? = null): Int = noImpl
    fun week(value: Int? = null): Int = noImpl
    fun isoWeek(value: Int? = null): Int = noImpl
    fun month(value: Int? = null): Int = noImpl
    fun quarter(value: Int? = null): Int = noImpl
    fun year(value: Int? = null): Int = noImpl
    fun weekYear(value: Int? = null): Int = noImpl
    fun isoWeekYear(value: Int? = null): Int = noImpl
    fun weeksInYear(): Int = noImpl
    fun locale(localeName: String): Unit = noImpl
    fun unix():Int = noImpl
    fun unix(t:Int): Unit = noImpl
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