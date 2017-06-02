package net.yested


external class RegExp(regexp:String, parameters:String)

 @Suppress("NOTHING_TO_INLINE")
 inline fun Number.toFixed(dp:Int):String = asDynamic().toFixed(dp)
 @Suppress("NOTHING_TO_INLINE")
 inline fun String.replace(regex:String, with:String):String = asDynamic().replace(regex, with)
 @Suppress("NOTHING_TO_INLINE")
 inline fun String.replace(regex:RegExp, with:String):String = asDynamic().replace(regex, with)

 fun String.replaceAll(regex:String, with:String):String {
   return this.replace(RegExp(regex, "g"), with)
}

//fun Number.asMoney():String = this.toFixed(2).replace("\\d(?=(\\d{3})+\\.)", "$&,");