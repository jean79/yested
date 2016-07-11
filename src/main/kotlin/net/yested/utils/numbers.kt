package net.yested.utils

 @kotlin.js.native("isNaN") fun isNaN(n:Number):Boolean = noImpl

 fun toZero(n:Double):Double =
    if (isNaN(n)) {
        0.0
    } else {
        n
    }
