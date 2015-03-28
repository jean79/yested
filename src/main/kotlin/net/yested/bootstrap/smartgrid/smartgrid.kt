package net.yested.bootstrap.smartgrid

import jquery.jq
import net.yested.*
import net.yested.bootstrap.Align
import net.yested.bootstrap.glyphicon
import net.yested.layout.ScrollBar
import net.yested.layout.ScrollBarOrientation
import net.yested.utils.disableSelection
import net.yested.utils.on
import net.yested.utils.sortable
import net.yested.utils.throttle
import org.w3c.dom.Node
import java.util.ArrayList
import java.util.HashMap
import kotlin.js.dom.html.HTMLElement

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
 * .on('mousemove', $.throttle(interval, function(e)
 */
public class SmartGrid<TYPE, KEY>(
        val rowHeight:Int = 30,
        val getKey:(TYPE)->KEY,
        val defaultSortColumn:String? = null,
        val defaultSortOrderAsc:Boolean = true,
        columns:Array<GridColumn<TYPE>>) : Component {

    private val dataTable = createElement("table") with {
        setAttribute("class", "table-striped table-hover table-condensed")
        setAttribute("style", "table-layout: fixed; height: 100%;")
    }

    private val header = createElement("tr") with { }

    private var cont = Div() with {
        "style".."overflow-x:hidden; overflow-y:auto; height: 100%"
        element.appendChild(dataTable)
    }

    private val scrollBarVertical = ScrollBar(
                                            orientation = ScrollBarOrientation.VERTICAL,
                                            size = "100%",
                                            numberOfItems = 1,
                                            visibleItems = 1,
                                            positionHandler = throttle(35, { verticalScrollBarMoved(it) }))

    private val scrollBarHorizontal = ScrollBar(
                                            orientation = ScrollBarOrientation.HORIZONTAL,
                                            size = "100%",
                                            numberOfItems = 1,
                                            visibleItems = 1,
                                            positionHandler = throttle(35, { horizontalScrollBarMoved(it) }))

    private val columnHeaderContainer = Div() with {  "style".."overflow: scroll; overflow-x:hidden; overflow-y:hidden;"
        table {
            element.setAttribute("class", "table-striped table-hover table-condensed")
            element.setAttribute("style", "margin-bottom: 0px;")
            thead {
                element.appendChild(header)
            }
        }
    }

    override val element =
            (Table() with { element.setAttribute("style", "width: 100%; height:100%; table-layout:fixed;")
                tbody {
                    tr {
                        td { "style".."width: 100%; height: ${rowHeight}px; vertical-align: middle;"
                            +columnHeaderContainer
                        }
                        td {
                            a(onclick = { showDialogCustom() }) {
                                "style".."cursor: pointer;"
                                glyphicon("cog")
                            }
                        }
                    }
                    tr { element.setAttribute("style", "height: 100%")
                        td { "style".."width: 100%; vertical-align: top;"
                            +cont
                        }
                        td { "style".."height: 100%;"
                            +scrollBarVertical
                        }
                    }
                    tr {
                        td { "style".."width: 100%; vertical-align: top;"
                            +scrollBarHorizontal
                        }
                        td { }
                    }
                }
            }).element

    private var sortColumn:GridColumn<TYPE>? = null
    private var asc:Boolean = true;
    private var columnHeaders:List<GridColumnHeader<TYPE>>? = null

    private var visibleColumns = listOf<String>()

    private val columns = columns.toMap { it.id };

    private var rowsReferences = hashMapOf<KEY, HTMLElement>();

    private var visibleRows:Int = 1

    private var currentRow:Int = 0

    private var gridIsCreated:Boolean = false

    private fun showDialogCustom() {
        openConfigurationDialog(columns.values(), visibleColumns, {
            visibleColumns = it;
            renderHeaderInto(header)
            createRowsWithColumns()
            redisplayTheReorderedDataSet()
        })
    }

    init {

        visibleColumns = columns.map { it.id }

        cont.onscroll = {
            columnHeaderContainer.element.scrollLeft = cont.element.scrollLeft
        }

        //http://stackoverflow.com/questions/2264072/detect-a-finger-swipe-through-javascript-on-the-iphone-and-android
        var touchStartY:Int = 0
        var touchStartX:Int = 0
        var touchStartRow:Int = 0
        var horizontalScrollStart:Int = 0

        jq(dataTable).on("touchstart", { event->
            touchStartY = event.originalEvent.touches[0].clientY;
            touchStartX = event.originalEvent.touches[0].clientX;
            event.preventDefault()
            touchStartRow = currentRow
            horizontalScrollStart = scrollBarHorizontal.position
        })

        jq(dataTable).on("touchmove", { event->
            event.preventDefault()
            val yUp = event.originalEvent.touches[0].clientY;
            val xUp = event.originalEvent.touches[0].clientX;
            val diffY:Int = yUp - touchStartY
            val diffX:Int = xUp - touchStartX

            val newRow = touchStartRow - (diffY.toDouble()/rowHeight).toInt()
            val limitedNewRow = Math.max(0, Math.min(newRow, dataList.size() - visibleRows))
            val newHorizontalScrollPosition = Math.max(0, Math.min(horizontalScrollStart - diffX, scrollBarHorizontal.numberOfItems))

            if (gridIsCreated) {
                if (limitedNewRow != currentRow) {
                    val previousRow = currentRow
                    currentRow = limitedNewRow
                    redisplayTheReorderedDataSet(previousRow)
                }
                scrollBarVertical.position = currentRow
                scrollBarHorizontal.position = newHorizontalScrollPosition
                cont.element.scrollLeft = newHorizontalScrollPosition.toDouble()
            }

        })

        cont.element.whenAddedToDom {
            renderHeaderInto(header)
            makeHeaderSortable(header)
            val viewPortHeight = jq(cont.element).height().toInt()
            visibleRows = Math.floor(viewPortHeight / rowHeight)
            createGrid()
        }

    }

    private var dataList: ArrayList<TYPE> = arrayListOf()
    private var dataListAsKeyMap: MutableMap<KEY, TYPE> = hashMapOf()

    public var list: ArrayList<TYPE>?
        get() = dataList
        set(value) {
            dataList = value ?: arrayListOf()
            dataListAsKeyMap = dataList.toMap { getKey(it) } as MutableMap
            currentRow = 0
            repeatWithDelayUntil( { gridIsCreated }, 100) {
                createRowsWithColumns()
                redisplayTheReorderedDataSet()
                scrollBarVertical.setup(numberOfItems = dataList.size() - visibleRows, visibleItems = visibleRows)
                if (dataList.size() <= visibleRows) {
                    scrollBarVertical.setTrackerVisible(false)
                } else {
                    scrollBarVertical.setTrackerVisible(true)
                }
            }
        }

    private fun verticalScrollBarMoved(newPosition:Int) {
        currentRow = newPosition
        redisplayTheReorderedDataSet()
    }

    private fun horizontalScrollBarMoved(newPosition:Int) {
        cont.element.scrollLeft = newPosition.toDouble()
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

    private fun updateHorizontalScrollbar() {
        val range = jq(dataTable).width().toInt() - jq(cont.element).width().toInt()
        if (range > 0) {
            val handlerSize = range * (jq(cont.element).width().toDouble() / jq(header).width().toDouble())
            scrollBarHorizontal.setup(numberOfItems = range.toInt(), visibleItems = handlerSize.toInt())
            scrollBarHorizontal.setTrackerVisible(true)
        } else {
            scrollBarHorizontal.setTrackerVisible(false)
        }
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
                    val update = { event: dynamic, ui: dynamic ->
                        visibleColumns = readCurrentColumnLayout()
                        createRowsWithColumns()
                        redisplayTheReorderedDataSet()
                    }
                })
    }

    private fun setOnClick(td:HTMLElement, column:GridColumn<TYPE>) {
        if (column.editor != null) {
            td.onclick = {
                if (td.getAttribute("editing") != "true") {
                    td.setAttribute("editing", "true")
                    val rowIndex = getTBody().getIndexOfChildNode(td.parentNode)
                    val item = dataList.get(currentRow + rowIndex)
                    val editor = column.editor.createEditor(
                            column.width,
                            item,
                            {
                                td.removeAttribute("editing")
                                td.removeAllContent()
                                HTMLComponent("", td) with {
                                    column.render(item)
                                }
                            })
                    td.removeAllContent()
                    td.appendChild(editor)

                }
            }
        } else {
            td.onclick = {}
        }
    }

    private fun createGrid() {

        jq(dataTable).on("mousewheel") { event ->
            val previousRow = currentRow
            val e = event.originalEvent
            val delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail) as Int));
            event.preventDefault()
            if (delta < 0) {
                currentRow = Math.min(currentRow + 1, dataList.size() - visibleRows)
            } else {
                currentRow = Math.max(0, currentRow - 1)
            }
            if (previousRow != currentRow) {
                redisplayTheReorderedDataSet(previousRow)
                scrollBarVertical.position = currentRow
            }
        }

        gridIsCreated = true

    }

    private fun createRowsWithColumns() {

        val visibleColumns = getVisibleColumns()

        var tbody = createElement("tbody")

        (1..visibleRows).forEach { rowIndex ->
            val tr = createElement("tr") with { setAttribute("style", "height: ${rowHeight}px;")}
            visibleColumns.forEach { column ->

                val td = createElement("td")
                if (column.align != Align.LEFT) {
                    td.setAttribute("class", "text-${column.align.code}")
                }
                if (rowIndex == 1) {
                    td.setAttribute("style", "min-width: ${column.width};  max-width: ${column.width};")
                }
                setOnClick(td, column)
                tr.appendChild(td)
            }
            tbody.appendChild(tr)
        }

        dataTable.removeChildByName("tbody")
        dataTable.appendChild(tbody)

        updateHorizontalScrollbar()

    }

    private fun redisplayTheReorderedDataSet(previousRow:Int? = null) {

        val columns = getVisibleColumns()
        val tbody = getTBody()
        val rows = tbody.childNodes

        rowsReferences.clear()

        val maxOptimizedMove:Int = Math.min(1, visibleRows / 2);

        if (previousRow != null && previousRow in (currentRow - maxOptimizedMove) .. (currentRow - 1)) {
            val movedRowsCount:Int = currentRow - previousRow
            (1..movedRowsCount).forEach { index ->
                val movedRow = rows.item(0)
                tbody.appendChild(movedRow)
                val itemForLastRow = dataList.get(visibleRows + currentRow - (movedRowsCount - index + 1))
                updateRow(columns, itemForLastRow, movedRow)
                rowsReferences.put(getKey(itemForLastRow), movedRow as HTMLElement)
            }
        } else if (previousRow != null && previousRow in ((currentRow + 1) .. (currentRow + maxOptimizedMove))) {
            val movedRowsCount:Int = previousRow -currentRow
            (1..movedRowsCount).forEach { index ->
                val movedRow = rows.item(visibleRows-1)
                val firstRow = rows.item(0)
                tbody.insertBefore(movedRow, firstRow)
                val itemForLastRow = dataList.get(currentRow - index + 1)
                updateRow(columns, itemForLastRow, movedRow)
                rowsReferences.put(getKey(itemForLastRow), movedRow as HTMLElement)
            }
        } else {
            (1..Math.min(visibleRows, dataList.size()))
                    .forEach {
                        val tr = rows.item(it - 1) as HTMLElement
                        val item = dataList.get(it + currentRow - 1)
                        updateRow(columns, item, tr)
                        rowsReferences.put(getKey(item), tr)
                    }
        }

    }

    private fun getTBody() = dataTable.getElementsByTagName("tbody").item(0)

    private fun updateRow(columns: List<GridColumn<TYPE>>, item: TYPE, tr: Node, columnsToUpdate: Collection<String>? = null) {
        columns.forEachIndexed { columnIndex, column ->
            if (columnsToUpdate == null || columnsToUpdate.contains(column.id)) {
                val td = tr.childNodes.item(columnIndex) as HTMLElement
                HTMLComponent("", td) with {
                    removeAllChildren()
                    column.render(item)
                }
            }
        }
    }

    public fun updateItem(item: TYPE, columnsToUpdate: Collection<String>? = null) {

        val originalItem = dataListAsKeyMap.get(getKey(item))
        if (originalItem == null) {
            throw Exception("Item ${item}} not found in the list")
        }

        val index = dataList.indexOf(originalItem)

        dataList.remove(originalItem)
        dataList.add(index, item)
        dataListAsKeyMap.put(getKey(item), item)

        val tr = rowsReferences.get(getKey(item))
        if (tr != null) {
            updateRow(getVisibleColumns(), item, tr, columnsToUpdate)
        }
        //disable sorting for performance reason
        sortColumn = null
        setSortingArrow()

    }

}