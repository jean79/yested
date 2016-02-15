package net.yested.bootstrap

import net.yested.HTMLComponent
import net.yested.with
import net.yested.Anchor
import org.w3c.dom.Element

 enum class MediaAlign(val className: String) {
	Left("pull-left"),
	Right("pull-right")
}

 class MediaBody() : HTMLComponent("div") {

	private val heading = HTMLComponent("h4") with { clazz = "media-heading" }

	init {
		element.setAttribute("class", "media-body")
	}

	 fun heading(init: HTMLComponent.() -> Unit) {
		heading.init()
		+heading
	}

	 fun content(init: HTMLComponent.() -> Unit) {
		this.init()
	}

}

 class MediaObject(align: MediaAlign) : HTMLComponent("div") {

	private val media = Anchor() with { clazz = align.className; href = "#" }
	private val body = MediaBody() with { }

	init {
		element.setAttribute("class", "media")
		appendChild(media)
		appendChild(body)
	}

	 fun media(init: HTMLComponent.() -> Unit) {
		media.init()
		val childElement = media.element.firstChild as Element
		val clazz = childElement.getAttribute("class") ?: ""
		childElement.setAttribute("class", "$clazz media-object")
	}

	 fun content(init: MediaBody.() -> Unit) {
		body.init()
	}
}

 fun HTMLComponent.mediaObject(align: MediaAlign, init:MediaObject.() -> Unit) {
	+(MediaObject(align) with  { init() })
}
