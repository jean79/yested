import net.yested.el
import net.yested.Div
import net.yested.with

fun main(args: Array<String>) {

    //here we create some div with a header and a list
    val div = Div() with {
        h1 {
            +"Hello World" //plus add given text as textContent to HTML element (h1)
        }
        ul {
            li {
                strong {
                    +"Bolded text"
                }
            }
            li {
                a(href = "http://www.yested.net") {
                    +"Link to Framework"
                }
            }
        }
    }

    //when we have constructed a DOM, we can take a parent element (via div.element)
    //and append it as a child to "app" div in HTML page
    el("app").appendChild(div.element)

}