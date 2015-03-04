package net.yested.utils


native("moment") private fun moment_js(): MomentJs = noImpl
native("moment") private fun moment_js(millisecondsSinceUnixEpoch: Long): MomentJs = noImpl
native("moment") private fun moment_js(input: String, format: String): MomentJs = noImpl

native("Moment")
private class MomentJs {
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
}

public class Moment(private val moment: MomentJs) {

    public fun format(format: String): String = moment.format(format)
    public fun format(format: FormatString): String = moment.format(format.toString())

    public val millisecondsSinceUnixEpoch: Long
		get() = moment.valueOf()

    public var millisecond: Int
        get() = moment.millisecond()
        set(value) {
            moment.millisecond(value)
        }
    public var second: Int
        get() = moment.second()
        set(value) {
            moment.second(value)
        }
    public var minute: Int
        get() = moment.minute()
        set(value) {
            moment.minute(value)
        }
    public var hour: Int
        get() = moment.hour()
        set(value) {
            moment.hour(value)
        }
    public var dayOfMonth: Int
        get() = moment.date()
        set(value) {
            moment.date(value)
        }
    public var dayOfYear: Int
        get() = moment.dayOfYear()
        set(value) {
            moment.dayOfYear(value)
        }
    public var month: Int
        get() = moment.month()
        set(value) {
            moment.month(value)
        }

    class object {
        public fun now(): Moment = Moment(moment_js())

        public fun parse(input: String, format: String): Moment = Moment(moment_js(input, format))

        public fun parseMillisecondsSinceUnixEpoch(millisecondsSinceUnixEpoch: Long): Moment{
			requireNotNull(millisecondsSinceUnixEpoch)
			return Moment(moment_js(millisecondsSinceUnixEpoch))
		}

        fun setLocale(localeName: String) {
            moment_js().locale(localeName)
        }
    }
}

public class FormatElement (val str: String) {

    public fun plus(b: FormatElement): FormatString {
        return FormatString(arrayListOf(this, b))
    }

    fun plus(b: String): FormatString {
        return FormatString(arrayListOf(this, FormatElement(b)))
    }
}

public class FormatString(private val elements: MutableList<FormatElement> = arrayListOf()) {

    public fun plus(b: FormatElement): FormatString {
        elements.add(b)
        return FormatString(elements)
    }

    public fun plus(b: String): FormatString {
        elements.add(FormatElement(b))
        return FormatString(elements)
    }

    override fun toString(): String = elements.map { it.str }.join(separator = "")
}

class Digit(private val oneDigitFactory: ()->FormatElement, private val twoDigitsFactory: ()->FormatElement, private val fourDigitsFactory: ()->FormatElement) {
	val oneDigit: FormatElement
		get() = oneDigitFactory()
	val twoDigits: FormatElement
		get() = twoDigitsFactory()
	val fourDigits: FormatElement
		get() = fourDigitsFactory()
}

public class FormatStringBuilder() {

	public val year: Digit = Digit({throw UnsupportedOperationException()}, {FormatElement("YY")}, {FormatElement("YYYY")})
	public val month: Digit = Digit({FormatElement("M")}, {FormatElement("MM")}, {throw UnsupportedOperationException()})
	public val dayOfMonth: Digit = Digit({FormatElement("D")}, {FormatElement("DD")}, {throw UnsupportedOperationException()})
	public val hour24: Digit = Digit({FormatElement("H")}, {FormatElement("HH")}, {throw UnsupportedOperationException()})
	public val hour12: Digit = Digit({FormatElement("h")}, {FormatElement("hh")}, {throw UnsupportedOperationException()})
	public val minutes: Digit = Digit({FormatElement("m")}, {FormatElement("mm")}, {throw UnsupportedOperationException()})
	public val seconds: Digit = Digit({FormatElement("s")}, {FormatElement("ss")}, {throw UnsupportedOperationException()})
}

public fun format(init: FormatStringBuilder.() -> FormatString): FormatString {
    return FormatStringBuilder().init()
}