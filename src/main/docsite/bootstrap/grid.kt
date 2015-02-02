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

/**
 * Created by jean on 17.12.2014.
 */

data class Person(val name: String, val age: Int)

fun createGrid(id: String): Div {

    val data = listOf(Person("Jan", 15), Person("Peter", 30), Person("Martin", 31))

    val grid = Grid(columns = array(
            Column(label = text("Name"), render = { +it.name }, sortFunction = {(l, r) -> compareValues(l.name, r.name) }),
            Column(label = text("Age "), render = { +"${it.age}" }, sortFunction = compareByValue<Person, Int> { it.age },
                    defaultSort = true, defaultSortOrderAsc = true)
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
                    +"""
Grid is simply a renderred HTML Table element. It is not suitable for too many rows.
"""
                }
                br()
                h4 { +"Demo" }
                +grid
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content="""data class Person(val name:String, val age:Int)
val data = listOf(Person("Jan", 15), Person("Peter", 30), Person("Martin", 31))

val grid = Grid(columns = array(
    Column(
            label = text("Name"),
            render = { +it.name },
            sortFunction = {(l,r) -> compareValues(l.name, r.name)}),
    Column(
            label = text("Age "),
            render = { +"\$\{it.age}" },
            sortFunction = compareBy<Person,Int> { it.age },
            defaultSort = true,
            defaultSortOrderAsc = true)
))

grid.list = data;
""")
            }
        }
    }

}