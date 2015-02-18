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
}

public class Moment(private val moment: MomentJs) {

    public fun format(format: String): String = moment.format(format)
    public fun format(format: FormatString): String = moment.format(format.toString())

    public val millisecondsSinceUnixEpoch: Long = moment.valueOf()

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

        public fun parseMillisecondsSinceUnixEpoch(millisecondsSinceUnixEpoch: Long): Moment = Moment(moment_js(millisecondsSinceUnixEpoch))
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

public class FormatStringBuilder() {

    class object {
        // TODO full list: http://momentjs.com/docs/#/parsing/
        private val fourDigitYear: FormatElement = FormatElement("YYYY")
        private val monthNumber: FormatElement = FormatElement("M")
        private val dayOfMonth: FormatElement = FormatElement("D")
        private val hour24: FormatElement = FormatElement("H")
        private val hour12: FormatElement = FormatElement("h")
        private val minutes: FormatElement = FormatElement("m")
        private val seconds: FormatElement = FormatElement("s")
    }
    public val fourDigitYear: FormatElement = FormatStringBuilder.fourDigitYear
    public val monthNumber: FormatElement = FormatStringBuilder.monthNumber
    public val dayOfMonth: FormatElement = FormatStringBuilder.dayOfMonth
    public val hour24: FormatElement = FormatStringBuilder.hour24
    public val hour12: FormatElement = FormatStringBuilder.hour12
    public val minutes: FormatElement = FormatStringBuilder.minutes
    public val seconds: FormatElement = FormatStringBuilder.seconds
}

public fun format(init: FormatStringBuilder.() -> FormatString): FormatString {
    return FormatStringBuilder().init()
}