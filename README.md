Yested
======
Yested is a Kotlin framework for building web applications in Javascript. 

Design goal:
* Fast and **strongly typed** development of web applications

Yested was inspired by:
* Adobe Flex
* [Kotlin HTML builder](http://kotlinlang.org/docs/reference/type-safe-builders.html)

Project status: Alpha, comments, critics and patches welcome!

# Introduction
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
It's rather boring, it creates a HTML elements via DOM manipulation and attach this subtree to div with id placeholder.

More complex example:
```
fun main() {
    val textinput = Textinput(placeholder = "Query")
    val buton = Button() { println("Content of textinput: ${textinput.value)") }
    page("placeholder") {
        content {
            +textinput
            +br()
            +button
        }
    }
}
```

Main is a Kotlin function which is executed when HTML page is fully loaded.

## What is it suitable for?
Yested is designed for a development of web applications that:
* Are dynamic (i.e. it is not a static HTML)
* Re-use components (grids, tabs)
* Needs to create it's own components
* Wants to be developed in a strongly typed way.
* Are CRUD like.

## What it does NOT allow
* Design components in HTML - all components have to be written in Yested DSL (in Kotlin language)

## Features
* Strongly typed development of a whole application.
* Simple and extensible DSL language for easy application constructon.
* Simple development of new components and attachement to DSL
* Damn easy wrapping of 3rd party Javascript libraries (thanks to Kotlin)
* Provides wrapper for CSS framework: Twitter Bootstrap 3
* Provides wrapper for Chart.js charting library 
