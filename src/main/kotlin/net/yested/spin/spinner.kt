package net.yested.spin

import net.yested.Component
import net.yested.HTMLComponent
import org.w3c.dom.HTMLElement

external class SpinnerCreated {
     val el: HTMLElement = definedExternally
}

@JsName("Spinner")
external class SpinnerNative(options: Any? = definedExternally) {
    fun spin(): SpinnerCreated = definedExternally
}

data class SpinnerOptions(
        val lines: Int = 13, // The number of lines to draw
        val length: Int = 20, // The length of each line
        val width: Int = 10, // The line thickness
        val radius: Int = 30, // The radius of the inner circle
        val corners: Int = 1, // Corner roundness (0..1)
        val rotate: Int = 0, // The rotation offset
        val direction: Int = 1, // 1: clockwise, -1: counterclockwise
        val color: String = "#000", // #rgb or #rrggbb or array of colors
        val speed: Int = 1, // Rounds per second
        val trail: Int = 60, // Afterglow percentage
        val shadow: Boolean = false, // Whether to render a shadow
        val hwaccel: Boolean = false, // Whether to use hardware acceleration
        val className: String = "spinner", // The CSS class to assign to the spinner
        val zIndex: Double = 2e9, // The z-index (defaults to 2000000000)
        val top: String = "50%", // Top position relative to parent
        val left: String = "50%", // Left position relative to parent
        val scale: Float = 1.0f, // Scales overall size of the spinner
        val opacity: Float = 0.25f, // Opacity of the lines
        val position: String = "absolute", // Element positioning
        val fps: Int = 20 // Frames per second when using setTimeout() as a fallback for CSS
)

 class Spinner(val options:SpinnerOptions = SpinnerOptions()) : Component {

    private val jsSpinnerElement = SpinnerNative(createOptions()).spin().el

    private fun createOptions(): Any = object {
        val lines = options.lines
        val length = options.length
        val width = options.width
        val radius = options.radius
        val corners = options.corners
        val rotate = options.rotate
        val direction = options.direction
        val color = options.color
        val speed = options.speed
        val trail = options.trail
        val shadow = options.shadow
        val hwaccel = options.hwaccel
        val className = options.className
        val zIndex = options.zIndex
        val top = options.top
        val left = options.left
        val scale = options.scale
        val opacity = options.opacity
        val position = options.position
        val fps = options.fps
    }

    override val element: HTMLElement = jsSpinnerElement

}

 fun HTMLComponent.spinner(options:SpinnerOptions = SpinnerOptions()) {
    +(Spinner(options))
}