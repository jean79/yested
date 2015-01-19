package net.yested.bootstrap

import net.yested.ComponentContainer
import net.yested.Div
import net.yested.with
import net.yested.bootstrap.ButtonSize
import net.yested.P
import net.yested.Anchor
import net.yested.Component
import org.w3c.dom.Element
import net.yested.HTMLComponentContainer

public enum class MediaAlign(val className: String) {
	Left : MediaAlign("pull-left")
	Right : MediaAlign("pull-right")
}

public class MediaBody() : HTMLComponentContainer("div") {

	private val heading = HTMLComponentContainer("h4") with { clazz = "media-heading" }

	{
		setAttribute("class", "media-body")
	}

	public fun heading(init: ComponentContainer.() -> Unit) {
		heading.init()
		appendChild(heading)
	}

	public fun content(init: ComponentContainer.() -> Unit) {
		this.init()
	}
}

public class MediaObject(align: MediaAlign) : HTMLComponentContainer("div") {

	private val media = Anchor() with { clazz = align.className; href = "#" }
	private val body = MediaBody() with { }

	{
		setAttribute("class", "media")
		appendChild(media)
		appendChild(body)
	}

	public fun media(init: ComponentContainer.() -> Unit) {
		media.init()
		val childElement = media.element.firstChild as Element
		val clazz = childElement.getAttribute("class") as String? ?: ""
		childElement.setAttribute("class", "$clazz media-object")
	}

	public fun content(init: MediaBody.() -> Unit) {
		body.init()
	}
}

public fun ComponentContainer.mediaObject(align: MediaAlign, init:MediaObject.() -> Unit) {
	val mediaObject = MediaObject(align)
	mediaObject.init()
	appendChild(mediaObject)
}
