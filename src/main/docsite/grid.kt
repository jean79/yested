import net.yested.bootstrap.Grid
import net.yested.bootstrap.Column
import net.yested.text

/**
 * Created by jean on 17.12.2014.
 */

data class Person(val name:String, val age:Int)

fun <T, V : Comparable<V>> createComparator(get:(T)->V?) : (T, T) -> Int {
    return {(l,r) -> compareValues(get(l), get(r))}
}

fun createGrid(): Grid<Person> {

    val data = listOf(Person("Jan", 15), Person("Peter", 30), Person("Martin", 31))

    val grid = Grid(columns = array(
            Column(label = text("Name"), render = { +it.name }, sortFunction = {(l,r) -> compareValues(l.name, r.name)}),
            Column(label = text("Age "), render = { +"${it.age}" }, sortFunction = createComparator<Person,Int> { it.age },
                    defaultSort = true, defaultSortOrderAsc = true)
    ))

    grid.list = data;

    return grid

}