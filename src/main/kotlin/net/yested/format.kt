package net.yested

public native fun Number.toFixed(dp:Int):String = noImpl
public native fun String.replace(regex:String, with:String):String = noImpl

//fun Number.asMoney():String = this.toFixed(2).replace("\\d(?=(\\d{3})+\\.)", "$&,");