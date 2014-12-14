Yested
======
Yested is a Kotlin framework for building web applications in Javascript. 

# How it works
Kotlin applications can be compiled into Javascript. Yested framework is designed for a strongly typed constructions of single page web applications. Compiled application runs whole in a browser. 

To understand the framework please look at [Kotlin HTML builder](http://kotlinlang.org/docs/reference/type-safe-builders.html).
Have you got it?
Then Yested is written in the same DSL way but each class wraps a single HTML element. 

## Short example
Let's have a html page with a placeholder div:
```
<div id="placeholder"></div>
```

Then we can build a Yested application as:
```
fun main() {
    page("placeholder") {
        content {
            a(href="http://www.wikipedia.org") { +"Wikipedia" }
            br()
            br()
            span(clazz="colored") { +"Some colored text" }
        }
    }
}
```
What id does: It creates a HTML elements via DOM manipulation and attach this subtree to div with id placeholder.

Main is a Kotlin function which is executed when application is fully loaded.
