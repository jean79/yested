package net.yested.bootstrap.smartgrid

import net.yested.HTMLComponent
import net.yested.Component
import net.yested.createElement
import kotlin.js.dom.html.HTMLElement
import net.yested.Div
import net.yested.with
import jquery.jq
import net.yested.utils.disableSelection
import net.yested.utils.sortable
import net.yested.removeChildByName

import net.yested.Span
import java.util.HashMap
import org.w3c.dom.Node
import java.util.ArrayList
import net.yested.removeAllContent
import net.yested.bootstrap.Align
import net.yested.bootstrap.glyphicon
import net.yested.bootstrap.InputField
import net.yested.utils.on

public trait CellEditorFactory<TYPE> {
    fun createEditor(width:String, item:TYPE, closeHandler: ()->Unit):HTMLElement
}

public data class GridColumn<T>(
        val id: String,
        val width: String,
        val label: String,
        val render: HTMLComponent.(T) -> Unit,
        val editor: CellEditorFactory<T>? = null,
        val align:Align = Align.LEFT,
        val sortFunction:((T, T) -> Int)? = null)

/**
 *
 */
public class SmartGrid<TYPE, KEY>(
        maxHeight:String? = null,
        val getKey:(TYPE)->KEY,
        val defaultSortColumn:String? = null,
        val defaultSortOrderAsc:Boolean = true,
        columns:Array<GridColumn<TYPE>>) : Component {

    override val element = createElement("div") with {
        setAttribute("style", "position: relative;")
    }

    private var sortColumn:GridColumn<TYPE>? = null
    private var asc:Boolean = true;
    private val arrowsPlaceholders = ArrayList<Span>();
    private var columnHeaders:List<GridColumnHeader<TYPE>>? = null

    private val tableElement = createElement("table") with {
        setAttribute("class", "table-striped table-hover table-condensed")
        setAttribute("style", "table-layout: fixed;")
    }

    private val header = createElement("tr") with { }

    private val headerTableElement = createElement("table") with {
        setAttribute("class", "table-striped table-hover table-condensed")
        setAttribute("style", "margin-bottom: 0px;")
    }

    private var visibleColumns = listOf<String>()

    private val columns = columns.toMap { it.id };

    private var rowsReferences = hashMapOf<KEY, HTMLElement>();

    private fun showDialogCustom() {
        openConfigurationDialog(columns.values(), visibleColumns, {
            visibleColumns = it;
            renderHeaderInto(header)
            displayData()
        })
    }

    {
        visibleColumns = columns.map { it.id }

        val headerCont = Div() with {
            if (maxHeight != null) {
                "style".."overflow: scroll; overflow-x:hidden; overflow-y:hidden;"
            }
            div {
                "style".."position: absolute; right: 0; top: 4px; z-index: 5"
                a(onclick = { showDialogCustom() }) {
                    "style".."cursor: pointer;"
                    glyphicon("cog")
                }
            }
        }
        headerCont.element.appendChild(headerTableElement)

        element.appendChild(headerCont.element)

        val thead = createElement("thead")
        thead.appendChild(header)

        headerTableElement.appendChild(thead)

        val cont = Div() with {
            if (maxHeight != null) {
                "style".."overflow: scroll; overflow-x:auto; overflow-y:auto; height: ${maxHeight};"
            }
        }
        cont.onscroll = {
            headerCont.element.scrollLeft = cont.element.scrollLeft
        }

        cont.element.appendChild(tableElement)
        element.appendChild(cont.element)

        renderHeaderInto(header)
        makeHeaderSortable(header)

    }

    private var dataList: ArrayList<TYPE> = arrayListOf()

    public var list: ArrayList<TYPE>?
        get() = dataList
        set(value) {
            dataList = value ?: arrayListOf()
            displayData()
        }

    private fun getVisibleColumns() =
            visibleColumns.map { columns.get(it)!! }


    private fun setSortingArrow() {
        columnHeaders!!.forEach { it.updateSorting(sortColumn, asc) }
    }

    private fun sortByColumn(column:GridColumn<TYPE>):Unit {
        if (column == sortColumn) {
            asc = !asc;
        } else {
            asc = true;
            sortColumn = column
        }
        sortData()
        redisplayTheReorderedDataSet()
        setSortingArrow()
    }

    private fun sortData() {

        if (sortColumn?.sortFunction != null) {
            dataList = dataList.sortBy(object : java.util.Comparator<TYPE> {
                override fun compare(obj1: TYPE, obj2: TYPE): Int {
                    return (sortColumn!!.sortFunction!!(obj1, obj2)) * (if (asc) 1 else -1)
                }
            }).toArrayList()
        }

    }

    private fun renderHeaderInto(headerDiv: HTMLElement) {

        columnHeaders =
                getVisibleColumns()
                .map {
                    GridColumnHeader(
                            column = it,
                            sortingSupported = it.sortFunction != null,
                            sortFunction = { sortByColumn(it) } )}

        headerDiv.removeAllContent()

        columnHeaders!!
                .map { createColumnHeader(it) }
                .forEach { headerDiv.appendChild(it.element) }
    }

    private fun createColumnHeader(columnHeader: GridColumnHeader<TYPE>) =
        HTMLComponent("th") with {
            "columnid"..columnHeader.column.id
            "style".."min-width: ${columnHeader.column.width}; max-width: ${columnHeader.column.width};"
            "class" .. "text-${columnHeader.column.align.code}";
            +columnHeader
        }

    private fun readCurrentColumnLayout() =
        (0..(header.childNodes.length-1)).map {
            (header.childNodes.item(it) as HTMLElement).getAttribute("columnid")
        }.toList()

    private fun makeHeaderSortable(headerDiv:HTMLElement) {
        jq(headerDiv).disableSelection()
        jq(headerDiv).sortable(
                object {
                    val update = { (event:dynamic, ui:dynamic) ->
                        visibleColumns = readCurrentColumnLayout()
                        redisplayTheReorderedDataSet()
                    }
                })
    }

    private fun setOnClick(td:HTMLComponent, column:GridColumn<TYPE>, item: TYPE) {
        if (column.editor != null) {
            td.onclick = {
                if (td.element.getAttribute("editing") != "true") {
                    td.element.setAttribute("editing", "true")
                    val editor = column.editor.createEditor(
                            column.width,
                            item,
                            {
                                td.element.removeAttribute("editing")
                                td.element.removeAllContent()
                                td with {
                                    column.render(item)
                                }
                            })
                    td.element.removeAllContent()
                    td.element.appendChild(editor)

                }
            }
        } else {
            //td.element.removeEventListener("onclick")
            td.onclick = {}
        }
    }

    private fun displayData() {
        val columns = getVisibleColumns()
        rowsReferences = HashMap()

        var tbody = createElement("tbody")

        var firstRow = true

        dataList.forEach { item ->
            val tr = createElement("tr")
            columns.forEachIndexed { index, column ->
                val td = HTMLComponent("td") with {
                    if (column.align != Align.LEFT) {
                        clazz = "text-${column.align.code}"
                    }
                    column.render(item)
                }
                if (firstRow) {
                    td.element.setAttribute("style", "min-width: ${column.width};  max-width: ${column.width};")
                }
                setOnClick(td, column, item)
                tr.appendChild(td.element)
            }
            rowsReferences.put(getKey(item), tr)
            tbody.appendChild(tr)
            firstRow = false
        }

        tableElement.removeChildByName("tbody")
        tableElement.appendChild(tbody)

    }

    private fun redisplayTheReorderedDataSet() {

        val columns = getVisibleColumns()
        val rows = tableElement.getElementsByTagName("tbody").item(0).childNodes

        dataList.forEachIndexed { (rowIndex, item) ->
            val tr = rows.item(rowIndex) as HTMLElement
            updateRow(columns, item, tr, rowIndex == 0)
            rowsReferences.put(getKey(item), tr)
        }

    }

    private fun updateRow(columns: List<GridColumn<TYPE>>, item: TYPE, tr: Node, updateWidths:Boolean, columnsToUpdate: Collection<String>? = null) {
        columns.forEachIndexed {(columnIndex, column) ->
            if (columnsToUpdate == null || columnsToUpdate.contains(column.id)) {
                val td = tr.childNodes.item(columnIndex) as HTMLElement
                HTMLComponent("", td) with {
                    removeAllChildren()
                    if (column.align != Align.LEFT) {
                        td.setAttribute("class", "text-${column.align.code}")
                    } else {
                        td.removeAttribute("class")
                    }
                    if (updateWidths) {
                        td.setAttribute("style", "min-width: ${column.width};  max-width: ${column.width};")
                    }
                    setOnClick(this, column, item)
                    column.render(item)
                }
            }
        }
    }

    fun findItemInTheList(key: KEY): TYPE? =
        dataList.filter { getKey(it) == key }.firstOrNull()

    public fun updateItem(item: TYPE, columnsToUpdate: Collection<String>? = null) {

        val originalItem = findItemInTheList(getKey(item))
        if (originalItem == null) {
            throw Exception("Item ${item}} not found in the list")
        }
        dataList.remove(originalItem)
        dataList.add(item)

        val tr = rowsReferences.get(getKey(item))

        updateRow(getVisibleColumns(), item, tr!!, false, columnsToUpdate)
        sortColumn = null
        setSortingArrow()

    }

}