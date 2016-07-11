package bootstrap

import net.yested.bootstrap.Grid
import net.yested.bootstrap.Column
import net.yested.text
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.Medium
import net.yested.compareByValue
import net.yested.colorized
import net.yested.Color
import net.yested.Colors
import kotlin.comparisons.compareValues

data class Person(val name: String, val age: Int, val hair: Color)

fun createGrid(id: String): Div {

    val data = listOf(
            Person("Jan", 15, Colors.GREEN.color),
            Person("Peter", 30, Colors.RED.color),
            Person("Martin", 31, Colors.BLUE.color))

    val grid = Grid(columns = arrayOf(
            Column(label = { +"Name" }, render = { +it.name },
                    sortFunction = { l, r -> compareValues(l.name, r.name) }),
            Column(label = text("Age"), render = { +"${it.age}" },
                    sortFunction = compareByValue<Person, Int> { it.age },
                    defaultSort = true, defaultSortOrderAsc = true),
            Column(label = text("Hair"), render = { colorized(backgroundColor = it.hair) { nbsp(4) } })
    ))

    grid.list = data;

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Grid" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"Grid is simply a rendered HTML Table element. It is not suitable for too many rows."
                    br()
                    +"Columns can optionally support sorting vi provided sort function."
                    br()
                    +"Each cell is rendered vi provided DSL function."
                    br()
                    +"Check our "
                    a(href="#grid") { +"SmartGrid" }
                    +" for much powerful grid component."
                }
                br()
                h4 { +"Demo" }
                +grid
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content="""data class Person(val name: String, val age: Int, val hair: Color)
val data = listOf(
            Person("Jan", 15, Colors.GREEN.color),
            Person("Peter", 30, Colors.RED.color),
            Person("Martin", 31, Colors.BLUE.color))

val grid = Grid(columns = array(
        Column(label = text("Name"), render = { +it.name },
                sortFunction = {(l, r) -> compareValues(l.name, r.name) }),
        Column(label = text("Age"), render = { +"$\{it.age}" },
                sortFunction = compareByValue<Person, Int> { it.age },
                defaultSort = true, defaultSortOrderAsc = true),
        Column(label = text("Hair"), render = { colorized(backgroundColor = it.hair) { nbsp(4) } })
))

grid.list = data;
""")
            }
        }
    }

}