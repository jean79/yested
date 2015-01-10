package net.yested.bootstrap

import net.yested.ParentComponent
import net.yested.HTMLParentComponent
import net.yested.Div
import net.yested.with
import net.yested.bootstrap.ButtonSize
import net.yested.P
import net.yested.Anchor
import net.yested.Component
import org.w3c.dom.Element

public enum class MediaAlign(val className: String) {
	Left : MediaAlign("pull-left")
	Right : MediaAlign("pull-right")
}

public class MediaBody() : HTMLParentComponent("div") {

	private val heading = HTMLParentComponent("h4") with { clazz = "media-heading" }

	{
		setAttribute("class", "media-body")
	}

	public fun heading(init: HTMLParentComponent.() -> Unit) {
		heading.init()
		add(heading)
	}

	public fun content(init: ParentComponent.() -> Unit) {
		this.init()
	}
}

public class MediaObject(align: MediaAlign) : HTMLParentComponent("div") {

	private val media = Anchor() with { clazz = align.className; href = "#" }
	private val body = MediaBody() with { }

	{
		setAttribute("class", "media")
		add(media)
		add(body)
	}

	public fun media(init: HTMLParentComponent.() -> Unit) {
		media.init()
		val childElement = media.element.firstChild as Element
		val clazz = childElement.getAttribute("class") as String? ?: ""
		childElement.setAttribute("class", "$clazz media-object")
	}

	public fun content(init: MediaBody.() -> Unit) {
		body.init()
	}
}

public fun HTMLParentComponent.mediaObject(align: MediaAlign, init:MediaObject.() -> Unit) {
	val mediaObject = MediaObject(align)
	mediaObject.init()
	add(mediaObject)
}
