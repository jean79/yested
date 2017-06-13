package net.yested.layout

import jquery.jq
import jquery.ui.draggable
import net.yested.*
import net.yested.utils.css
import net.yested.utils.on
import net.yested.utils.registerResizeHandler
import org.w3c.dom.HTMLElement
import kotlin.js.Math
import kotlin.js.json

enum class ScrollBarOrientation(val directionProperty:String, val nonDirectionProperty:String, val axis:String, val cssPosProperty:String) {
    VERTICAL(directionProperty = "height", nonDirectionProperty = "width", axis = "y", cssPosProperty = "top"),
    HORIZONTAL(directionProperty = "width", nonDirectionProperty = "height", axis = "x", cssPosProperty = "left")
}

/**
 * https://github.com/cowboy/jquery-throttle-debounce
 */
 class ScrollBar(
        val orientation: ScrollBarOrientation,
        val size: Dimension,
        var numberOfItems: Int,
        var visibleItems: Int,
        val className:String? = null,
        val positionHandler:(Int) -> Unit) : Component {

    override val element = createElement("div")

    private val handle = Div()

    private var currentPosition:Int = 0

    private var trackerDimension:Int = 0

    private var handleDimension:Int = 0

     fun setTrackerVisible(visibleTracker:Boolean) {
        if (visibleTracker) {
            jq(handle.element).css("visibility", "visible")
        } else {
            jq(handle.element).css("visibility", "hidden")
        }
    }

    init {

        element.appendChild(handle.element)
        element.setAttribute("style", "${orientation.directionProperty}: ${size.toHtml()};")
        element.setAttribute("position", "absolute")

        if (className != null) {
            handle.clazz = className
        } else {
            handle.style = "${orientation.nonDirectionProperty}: 15px; background-color: #5c92e7; cursor: move; position: relative; ${orientation.cssPosProperty}: 0"
        }

        jq(handle.element).draggable(
                json(
                        Pair("axis", orientation.axis),
                        Pair("containment", "parent"),
                        Pair("drag", {
                            val top = jq(handle.element).css(orientation.cssPosProperty).toInt()
                            updatePosition(top)
                        })
                ))

        jq(element).on("mousewheel") { event ->
            val e = event.originalEvent
            val delta = Math.max(-1, Math.min(1, (e.wheelDelta ?: -e.detail) as Int))
            event.preventDefault()
            if (delta < 0) {
                if (currentPosition < numberOfItems) {
                    currentPosition += delta*-1
                    changePositionOfHandler()
                    positionHandler(currentPosition)
                }
            } else {
                if (currentPosition > 0) {
                    currentPosition += delta*-1
                    changePositionOfHandler()
                    positionHandler(currentPosition)
                }
            }
        }

        var touchStartMouse:Int = 0
        var touchStartTop:Int = 0

        jq(handle.element).on("touchstart", { event->
            touchStartTop = jq(handle.element).css(orientation.cssPosProperty).toInt()
            touchStartMouse = getMouseTouchPosition(event)
            event.preventDefault()
        })

        jq(handle.element).on("touchmove", { event ->
            event.preventDefault()
            val newMousePos = getMouseTouchPosition(event)
            val diff: Int = newMousePos - touchStartMouse
            val newPosition = Math.max(0, Math.min(touchStartTop + diff, trackerDimension - handleDimension))
            jq(handle.element).css(orientation.cssPosProperty, "${newPosition}px")
            updatePosition(newPosition)
        })

        handle.element.whenAddedToDom {
            recalculate()
            registerResizeHandler(element.parentNode as HTMLElement) { x, y ->
                recalculate()
            }
        }

    }

    private fun getMouseTouchPosition(event: dynamic) = if (orientation == ScrollBarOrientation.VERTICAL) {
            event.originalEvent.touches[0].clientY
        } else {
            event.originalEvent.touches[0].clientX
        }

    private fun updatePosition(top: Int) {
        currentPosition = (numberOfItems.toDouble() * top / (trackerDimension - handleDimension)).toInt()
        positionHandler(currentPosition)
    }

     var position:Int
        get() = currentPosition
        set(value:Int) {
            currentPosition = value
            changePositionOfHandler()
        }

    private fun changePositionOfHandler() {
        val position = (currentPosition.toDouble() * (trackerDimension - handleDimension) / numberOfItems).toInt()
        jq(handle.element).css(orientation.cssPosProperty, "${position}px")
    }

     fun setup(numberOfItems: Int, visibleItems: Int, newPosition: Int) {
        this.numberOfItems = numberOfItems
        this.visibleItems = visibleItems
        element.whenAddedToDom {
            recalculate()
            position = newPosition
        }
    }

    private fun recalculate() {
        trackerDimension = trackerDimension()
        handleDimension = handleDimension().toInt()
        jq(handle.element).css(orientation.directionProperty, "${handleDimension}")
    }

    private fun trackerDimension() =
            if (orientation == ScrollBarOrientation.VERTICAL) {
                jq(element).height().toInt()
            } else {
                jq(element).width().toInt()
            }

    private fun handleDimension() = Math.max(30, (trackerDimension * visibleItems/numberOfItems).toInt())

}