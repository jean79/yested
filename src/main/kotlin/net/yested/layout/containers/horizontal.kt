package net.yested.layout.containers


import jquery.jq
import net.yested.*
import net.yested.utils.css
import net.yested.utils.registerResizeHandler
import org.w3c.dom.HTMLElement

class HorizontalContainer(width: Dimension, val height: Dimension? = null, val gap:Int = 0) : Component {

    private val items = arrayListOf<ContainerItem>()

    override val element = createElement("div") with {
        setAttribute("style", "position: relative; width: ${width.toHtml()}; height: ${height?.toHtml() ?: ""};")
    }

    init {
        element.whenAddedToDom {
            recalculatePositions()
            registerResizeHandler(element.parentNode as HTMLElement) { x, y ->
                recalculatePositions()
            }
        }
    }

    private fun needToCalculateHeight() = height == null

     fun column(width: Dimension, height: Dimension? = null, init: HTMLComponent.() -> Unit) {
        val child = Div() with {
            style = "position: absolute; overflow-x: hidden; height: ${height?.toHtml()};"
            init()
        }
        if (items.size > 0 && gap > 0) {
            val gap = createElement("div") with { setAttribute("style", "width: ${gap}px;")}
            element.appendChild(gap)
        }
        items.add(ContainerItem(child, width))
        element.appendChild(child.element)
        recalculatePositions()
        if (needToCalculateHeight()) {
            recalculateHeight()
            registerResizeHandler(child.element) { x, y ->
                recalculateHeight()
            }
        }
    }

    private fun recalculatePositions() {

        val gaps = (items.size - 1) * gap
        val totalDimension = jq(element).width()
        val totalFixed = items.filter { it.dimension is Pixels }.map { (it.dimension as Pixels).value }.sum()
        val totalPercents = items.filter { it.dimension is Percent }.map { (it.dimension as Percent).value }.sum()
        val dimensionAvailableToPct = totalDimension.toInt() - totalFixed - gaps

        var position = 0
        items.forEach { item ->
            val calculatedDimension: Int = if (item.dimension is Pixels) {
                item.dimension.value
            } else if (item.dimension is Percent) {
                (item.dimension.value / totalPercents * dimensionAvailableToPct).toInt()
            } else {
                throw Exception("Unsupported dimension type for horizontal column width: ${item.dimension}")
            }
            jq(item.div.element).css("left", "${position}px")
            jq(item.div.element).css("width", "${calculatedDimension}px")
            position += calculatedDimension + gap
        }
    }

    private fun recalculateHeight() {
        val maxHeightOfChildren = items.map { jq(it.div.element).height().toInt() }.max()
        jq(element).css("height", "$maxHeightOfChildren")
    }

}

 fun HTMLComponent.horizontalContainer(width: Dimension, height: Dimension? = null, gap: Int = 0, init: HorizontalContainer.() -> Unit) {
    +(HorizontalContainer(width = width, height = height, gap = gap) with { init() })
}
