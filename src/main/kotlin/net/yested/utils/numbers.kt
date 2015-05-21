package net.yested.utils

public native("isNaN") fun isNaN(n:Number):Boolean = noImpl

public fun toZero(n:Double):Double =
    if (isNaN(n)) {
        0.0
    } else {
        n
    }
