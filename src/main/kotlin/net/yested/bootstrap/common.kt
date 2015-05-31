package net.yested.bootstrap

public enum class Device(val code:String) {
    EXTRA_SMALL("xs"),
    SMALL("sm"),
    MEDIUM("md"),
    LARGER("lg")
}

public abstract class ColumnModifier(val size: Int, val device: Device, val modifierString: String) {
    override fun toString(): String = "col-${device.code}${modifierString}-${size}"
}

public abstract data class DeviceSize(size: Int, device: Device) : ColumnModifier(size, device, "")
public data class Offset(size: Int, device: Device) : ColumnModifier(size, device, "-offset")

public class ExtraSmall(size: Int): DeviceSize(size, Device.EXTRA_SMALL)
public class Small(size: Int): DeviceSize(size, Device.SMALL)
public class Medium(size: Int): DeviceSize(size, Device.MEDIUM)
public class Larger(size: Int): DeviceSize(size, Device.LARGER)

public enum class Align(val code:String) {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right")
}