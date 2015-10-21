package net.yested


public @native class RegExp { }

public @native("new RegExp") fun regExp(regexp:String, parameters:String): RegExp = RegExp();

public @native fun Number.toFixed(dp:Int):String = noImpl
public @native fun String.replace(regex:String, with:String):String = noImpl
public @native fun String.replace(regex:RegExp, with:String):String = noImpl

public fun String.replaceAll(regex:String, with:String):String {
   return this.replace(regExp(regex, "g"), with)
}

//fun Number.asMoney():String = this.toFixed(2).replace("\\d(?=(\\d{3})+\\.)", "$&,");