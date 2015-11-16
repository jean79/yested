package net.yested.utils

public @kotlin.js.native("isNaN") fun isNaN(n:Number):Boolean = noImpl

public fun toZero(n:Double):Double =
    if (isNaN(n)) {
        0.0
    } else {
        n
    }
