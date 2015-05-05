package net.yested.bootstrap.smartgrid

import jquery.jq
import net.yested.*
import net.yested.bootstrap.Align
import net.yested.bootstrap.glyphicon
import net.yested.layout.*
import net.yested.layout.containers.VerticalContainer
import net.yested.layout.containers.horizontalContainer
import net.yested.utils.*
import org.w3c.dom.Node
import java.util.ArrayList
import java.util.HashMap
import kotlin.js.dom.html.HTMLElement
import kotlin.js.dom.html.window

public trait CellEditorFactory<TYPE> {
    fun createEditor(width:String, item:TYPE, closeHandler: ()->Unit):HTMLElement
}

public class Filter<T>(
        val filteringFunction: Function1<T, Boolean>,
        val filterConfig: String
)

public trait FilterFactory<T> {
    fun createFilterElement(newFilterHandler : Function1<Filter<T>?, Unit>, filterConfig : String? = null): Component
}

public data class GridColumn<T>(
        val id: String,
        val width: String,
        val label: String,
        val render: HTMLComponent.(T) -> Unit,
        val editor: CellEditorFactory<T>? = null,
        val align:Align = Align.LEFT,
        val filterFactory: FilterFactory<T>? = null,
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

    private val scrollBarVertical = ScrollBar(
                                            orientation = ScrollBarOrientation.VERTICAL,
                                            size = 100.pct(),
                                            numberOfItems = 1,
                                            visibleItems = 1,
                                            positionHandler = throttle(35, { verticalScrollBarMoved(it) }))

    private val scrollBarHorizontal = ScrollBar(
                                            orientation = ScrollBarOrientation.HORIZONTAL,
                                            size = 100.pct(),
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

    private val cont = ScrollPane(horizontal = Overflow.HIDDEN, height = 100.pct()) {
        element.appendChild(dataTable)
    }

    override val element =
            (VerticalContainer(width = 100.pct(), height = 100.pct()) with {
                row(width = 100.pct(), height = (rowHeight).px()) {
                    horizontalContainer(width = 100.pct()) {
                        column(width = 100.pct()) {
                            scrollPane(horizontal = Overflow.HIDDEN) {
                                +columnHeaderContainer
                            }
                        }
                        column(width = 15.px()) {
                            a(onclick = { showDialogCustom() }) {
                                "style".."cursor: pointer;"
                                glyphicon("cog")
                            }
                        }
                    }
                }
                row(width = 100.pct(), height = 100.pct()) {
                    horizontalContainer(width = 100.pct(), height = 100.pct()) {
                        column(width = 100.pct(), height = 100.pct()) {
                            +cont
                        }
                        column(width = 15.px(), height = 100.pct()) {
                            +scrollBarVertical
                        }
                    }
                }
                row(width = 100.pct(), height = 15.px()) {
                    horizontalContainer(width = 100.pct()) {
                        column(width = 100.pct()) {
                            +scrollBarHorizontal
                        }
                        column(width = 15.px()) {
                            div { "style".."width:15px;" }
                        }
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

    private val filters = hashMapOf<String, Filter<TYPE>>()

    private fun showDialogCustom() {
        openConfigurationDialog(columns.values(), visibleColumns, {
            visibleColumns = it;
            createRowsWithColumns()
            renderHeaderInto(header)
            redisplayTheReorderedDataSet()
            updateHorizontalScrollbar()
        })
    }

    init {

        visibleColumns = columns.map { it.id }

        //register scrolling
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
            val limitedNewRow = Math.max(0, Math.min(newRow, filteredDataList.size() - visibleRows))
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
            recalculateVisibleRows()
            createGrid()
            registerResizeHandler(cont.element) { x,y->
                recalculateVisibleRows()
                createRowsWithColumns()
                if (filteredDataList.size() > 0) {
                    displayNewData()
                }
                updateHorizontalScrollbar()
            }
        }

        jq(window).on("scroll") {
            columnHeaders?.forEach { it.repositionFilter() }
        }
        jq(cont.element).on("scroll") {
            columnHeaders?.forEach { it.repositionFilter() }
        }
        jq(window).on("resize") {
            columnHeaders?.forEach { it.repositionFilter() }
        }

    }

    private fun recalculateVisibleRows() {
        val viewPortHeight = jq(cont.element).height().toInt()
        visibleRows = Math.floor(viewPortHeight / rowHeight)
    }

    private var fullDataList: ArrayList<TYPE> = arrayListOf()
    private var filteredDataList: ArrayList<TYPE> = arrayListOf()
    private var dataListAsKeyMap: MutableMap<KEY, TYPE> = hashMapOf()

    public var list: ArrayList<TYPE>?
        get() = fullDataList
        set(value) {
            fullDataList = value ?: arrayListOf()
            dataListAsKeyMap = fullDataList.toMap { getKey(it) } as MutableMap
            currentRow = 0
            refilterData()
            repeatWithDelayUntil( { gridIsCreated }, 100) {
                createRowsWithColumns()
                displayNewData()
            }
        }

    private fun displayNewData() {
        currentRow = Math.min(currentRow, Math.max(0, filteredDataList.size() - visibleRows))
        redisplayTheReorderedDataSet()
        updateVerticalScrollbarToReflectChangeNumberOfItems()
    }

    private fun updateVerticalScrollbarToReflectChangeNumberOfItems() {
        val adjustedVisibleRows = calculateAdjustedVisibleRowsForVerticalScrollbar()
        println("${filteredDataList.size()} ${filteredDataList.size()-visibleRows} ${adjustedVisibleRows}")
        scrollBarVertical.setup(numberOfItems = filteredDataList.size() - visibleRows, visibleItems = adjustedVisibleRows, newPosition = currentRow)
        if (filteredDataList.size() <= visibleRows) {
            scrollBarVertical.setTrackerVisible(false)
        } else {
            scrollBarVertical.setTrackerVisible(true)
        }
    }

    private fun calculateAdjustedVisibleRowsForVerticalScrollbar() =
            Math.max(1, (visibleRows * ((filteredDataList.size() - visibleRows).toDouble() / filteredDataList.size().toDouble())).toInt())

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
            filteredDataList = filteredDataList.sortBy(object : java.util.Comparator<TYPE> {
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
                            filterHandler = { filter -> updateFilter(it.id, filter)},
                            filterConfig = filters.get(it.id)?.filterConfig ?: null,
                            sortFunction = { sortByColumn(it) } )}

        headerDiv.removeAllContent()

        columnHeaders!!
                .map { createColumnHeader(it) }
                .forEach { headerDiv.appendChild(it.element) }

        val filtersOfHiddenColumns = filters.keySet().filter { !visibleColumns.contains(it) }
        filtersOfHiddenColumns.forEach {
            filters.remove(it)
        }

        if (gridIsCreated) {
            filtersChanged()
        }

    }

    private fun updateFilter(columnId:String, filter: Filter<TYPE>?) {
        if (filter != null) {
            filters.put(columnId, filter)
        } else {
            filters.remove(columnId)
        }
        filtersChanged()
    }

    private fun filtersChanged() {
        refilterData()
        displayNewData()
    }

    private fun refilterData() {
        if (filters.size() == 0) {
            filteredDataList = fullDataList
        } else {
            filteredDataList = fullDataList
                    .filter {
                        isItemMatchingFilters(it)
                    }
                    .toArrayList()
        }
    }

    private fun isItemMatchingFilters(item: TYPE): Boolean {
        var matching = true
        for (filter in filters.values()) {
            if (!filter.filteringFunction(item)) {
                matching = false
                break
            }
        }
        return matching
    }

    private fun updateHorizontalScrollbar() {
        val range = jq(dataTable).width().toInt() - jq(cont.element).width().toInt()
        if (range > 0) {
            val handlerSize = range * (jq(cont.element).width().toDouble() / jq(header).width().toDouble())
            val newHorizontalPosition = Math.min(scrollBarHorizontal.position, range.toInt())
            scrollBarHorizontal.setup(numberOfItems = range.toInt(), visibleItems = handlerSize.toInt(), newPosition = newHorizontalPosition)
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
                    val item = filteredDataList.get(currentRow + rowIndex)
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
                currentRow = Math.min(currentRow + 1, filteredDataList.size() - visibleRows)
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

        val maxOptimizedMove:Int = Math.min(1, visibleRows / 2);

        if (previousRow != null && previousRow in (currentRow - maxOptimizedMove) .. (currentRow - 1)) { //scrolling down
            val movedRowsCount:Int = currentRow - previousRow

            (1..movedRowsCount).forEach { index ->

                //remove previous records from rowsReferences
                val removedItem = filteredDataList.get(previousRow + index - 1)
                rowsReferences.remove(getKey(removedItem))

                val movedRow = rows.item(0)
                tbody.appendChild(movedRow)
                val itemForLastRow = filteredDataList.get(visibleRows + currentRow - (movedRowsCount - index + 1))
                updateRow(columns, itemForLastRow, movedRow)
                rowsReferences.put(getKey(itemForLastRow), movedRow as HTMLElement)
                //TODO: remove references to removed rows from rowsReferences
            }
        } else if (previousRow != null && previousRow in ((currentRow + 1) .. (currentRow + maxOptimizedMove))) { //scrolling up
            val movedRowsCount:Int = previousRow -currentRow
            (1..movedRowsCount).forEach { index ->

                //remove previous records from rowsReferences
                val removedItem = filteredDataList.get(previousRow + visibleRows - index)
                rowsReferences.remove(getKey(removedItem))

                val movedRow = rows.item(visibleRows-1)
                val firstRow = rows.item(0)
                tbody.insertBefore(movedRow, firstRow)
                val itemForLastRow = filteredDataList.get(currentRow - index + 1)
                updateRow(columns, itemForLastRow, movedRow)
                rowsReferences.put(getKey(itemForLastRow), movedRow as HTMLElement)
                //TODO: remove references to removed rows from rowsReferences
            }
        } else {
            rowsReferences.clear()
            val rowsToRender = Math.min(visibleRows, filteredDataList.size())
            (1..rowsToRender)
                    .forEach {
                        val tr = rows.item(it - 1) as HTMLElement
                        val item = filteredDataList.get(it + currentRow - 1)
                        updateRow(columns, item, tr)
                        rowsReferences.put(getKey(item), tr)
                    }
            ((rowsToRender+1)..visibleRows)
                .forEach {
                    val tr = rows.item(it - 1) as HTMLElement
                    clearRow(columns, tr)
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

    private fun clearRow(columns: List<GridColumn<TYPE>>, tr: Node, columnsToUpdate: Collection<String>? = null) {
        columns.forEachIndexed { columnIndex, column ->
            if (columnsToUpdate == null || columnsToUpdate.contains(column.id)) {
                val td = tr.childNodes.item(columnIndex) as HTMLElement
                HTMLComponent("", td) with {
                    removeAllChildren()
                }
            }
        }
    }

    public fun updateItem(item: TYPE, columnsToUpdate: Collection<String>? = null) {

        val originalItem = dataListAsKeyMap.get(getKey(item))
        if (originalItem == null) {
            throw Exception("Item ${item}} not found in the list")
        }

        val index = fullDataList.indexOf(originalItem)

        fullDataList.remove(originalItem)
        fullDataList.add(index, item)
        dataListAsKeyMap.put(getKey(item), item)

        val wasInList = filteredDataList.remove(originalItem)
        val isMatchingFilter = isItemMatchingFilters(item)
        if (isMatchingFilter) {
            filteredDataList.add(item)
        }

        //disable sorting for performance reason
        sortColumn = null
        setSortingArrow()

        if (wasInList == isMatchingFilter) {
            val tr = rowsReferences.get(getKey(item))
            if (tr != null) {
                updateRow(getVisibleColumns(), item, tr, columnsToUpdate)
            }
        } else {
            //item was either added or removed from the filtered list, better to redisplay whole content,
            displayNewData()
        }

    }

}