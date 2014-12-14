/**
 * Created by jean on 20.11.2014.
 */
package net.yested.bootstrap

import net.yested.HTMLParentComponent
import net.yested.ParentComponent
import net.yested.thead
import net.yested.tbody

data class Column<T>(
        val label:HTMLParentComponent.() -> Unit,
        val render:HTMLParentComponent.(T) -> Unit,
        val sortFunction:(T, T) -> Int,
        val align:Align = Align.LEFT,
        val defaultSort:Boolean = false,
        val defaultSortOrderAsc:Boolean = true)

class Grid<T>(val columns:Array<Column<T>>) : ParentComponent("table") {

    var sortColumn:Column<T>? = null
    var asc:Boolean = true;

    {
        element.className = "table table-striped table-hover table-condensed"
        renderHeader()
        //sortColumn = columns.first()
        sortColumn = (columns.filter { it.defaultSort } : Iterable<Column<T>>).firstOrNull()
        asc = sortColumn?.defaultSortOrderAsc ?: true
    }

    var _list: List<T>? = null

    var list: List<T>?
        get() = _list
        set(value) {
            _list = value
            displayData()
        }

    fun sortByColumn(column:Column<T>) {
        if (column == sortColumn) {
            asc = !asc;
        } else {
            asc = true;
            sortColumn = column
        }
        displayData()
    }

    fun renderHeader() {
        add(
            thead {
                tr {
                    columns.forEach { column ->
                        th {
                            "class" .. "text-${column.align.code}";
                            column.label()
                            style = "cursor: pointer;"
                            onclick = { sortByColumn(column) }
                        }
                    }
                }
            }
        )
    }

    fun sortData(toSort:List<T>):List<T> {
        return toSort.sortBy(object: java.util.Comparator<T> {
            override fun compare(obj1: T, obj2: T): Int {
                return (sortColumn!!.sortFunction(obj1, obj2)) * (if (asc) 1 else -1)
            }
        })
    }

    fun displayData() {
        removeChild("tbody")
        _list?.let {

            val values = if (sortColumn != null) sortData(_list!!) else _list!!

            add(
                tbody {
                    values.forEach { item ->
                        tr {
                            columns.forEach { column ->
                                td {
                                    "class" .. "text-${column.align.code}";
                                    column.render(item) } }
                        }
                    }
                }

            )

        }
    }

}