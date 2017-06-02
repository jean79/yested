package net.yested

data class AjaxRequest<RESULT>(val url:String, val type:String = "POST", val data:String,
                       val contentType:String = "application/json; charset=utf-8",
                       val dataType:String = "json", val success: ((RESULT) -> Unit))

external interface JQAjax {
    fun <T> get(url:String, loaded:(response: T) -> Unit) : Unit
    //fun post(url:String, data:Any?, handler:()->Unit, type:String = "json") : Unit = definedExternally
    //fun ajax(url:String, type:String, contentType:String, dataType:String, data:Any, success:()->Unit) : Unit = definedExternally
    fun <RESULT> ajax(request:AjaxRequest<RESULT>) : Unit
}

@JsName("$")
external var ajaxJQuery: JQAjax

fun <T> ajaxGet(url:String, loaded:(response:T) -> Unit) : Unit {
    ajaxJQuery.get(url = url, loaded = loaded)
}

fun <RESULT> ajaxPost(ajaxRequest: AjaxRequest<RESULT>) : Unit {
    ajaxJQuery.ajax(ajaxRequest)
}