package net.yested.bootstrap.smartgrid

import net.yested.HTMLComponent
import net.yested.Span
import net.yested.bootstrap.Glyphicon

/**
 * Internal class of SmartGrid
 */
class GridColumnHeader<T>(
        val column:GridColumn<T>,
        sortingSupported:Boolean,
        sortFunction:((GridColumn<T>) -> Unit)?) : HTMLComponent("span") {

    var sortOrderAsc:Boolean = true
    var arrowPlaceholder = Span()

    init {
        if (sortingSupported) {
            a(href = null, onclick = { sortFunction!!(column)} ) {
                "style".."cursor: pointer;"
                +column.label
            }
            +arrowPlaceholder
        } else {
            +column.label
        }
    }

    fun updateSorting(sortByColumn:GridColumn<T>?, sortAscending:Boolean) {
        if (sortByColumn != column) {
            arrowPlaceholder.setContent("")
        } else {
            arrowPlaceholder.setChild(Glyphicon("arrow-${if (sortAscending) "up" else "down"}"))
        }
    }

}

