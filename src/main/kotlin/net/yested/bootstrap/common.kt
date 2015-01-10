package net.yested.bootstrap

public enum class Device(val code:String) {
    EXTRA_SMALL : Device("xs")
    SMALL : Device("sm")
    MEDIUM : Device("md")
    LARGER : Device("lg")
}

public abstract data class DeviceSize(val size: Int, val device: Device)
public class ExtraSmall(size: Int): DeviceSize(size, Device.EXTRA_SMALL)
public class Small(size: Int): DeviceSize(size, Device.SMALL)
public class Medium(size: Int): DeviceSize(size, Device.MEDIUM)
public class Larger(size: Int): DeviceSize(size, Device.LARGER)

public enum class Align(val code:String) {
    LEFT : Align("left")
    CENTER : Align("center")
    RIGHT : Align("right")
}