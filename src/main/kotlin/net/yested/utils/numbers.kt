package net.yested.utils

 external fun isNaN(n:Number):Boolean = definedExternally

 fun toZero(n:Double):Double =
    if (isNaN(n)) {
        0.0
    } else {
        n
    }
