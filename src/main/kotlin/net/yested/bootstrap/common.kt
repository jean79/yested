package net.yested.bootstrap

 enum class Device(val code:String) {
    EXTRA_SMALL("xs"),
    SMALL("sm"),
    MEDIUM("md"),
    LARGER("lg")
}

 abstract class ColumnModifier(val size: Int, val device: Device, val modifierString: String) {
    override fun toString(): String = "col-${device.code}${modifierString}-${size}"
}

 abstract class DeviceSize(size: Int, device: Device) : ColumnModifier(size, device, "")
 abstract class Offset(size: Int, device: Device) : ColumnModifier(size, device, "-offset")

 class ExtraSmall(size: Int): DeviceSize(size, Device.EXTRA_SMALL)
 class Small(size: Int): DeviceSize(size, Device.SMALL)
 class Medium(size: Int): DeviceSize(size, Device.MEDIUM)
 class Larger(size: Int): DeviceSize(size, Device.LARGER)

 enum class Align(val code:String) {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right")
}