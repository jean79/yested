Yested
======
Yested is a Kotlin framework for building web applications in Javascript. 

# How it works
Kotlin applications can be compiled into Javascript. Yested framework is designed for a strongly typed constructions of single page web applications. Compiled application runs whole in a browser. 

To understand the framework please look at Kotlin HTML builder.

## Short example
Let's have a html page with a placeholder div:
```
<div id="page"></div>
```

Then we can build a Yested application as:
```
fun main() {
    Page( el("page") as HTMLElement ) with {
        content {
            a(href="http://www.wikipedia.org") { +"Wikipedia" }
            br()
            br()
            span(clazz="colored") { +"Some colored text" }
        }
    }
}
```
