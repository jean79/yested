package net.yested.bootstrap

import net.yested.BooleanAttribute
import net.yested.HTMLComponent
import jquery.JQuery
import jquery.jq
import net.yested.hide
import net.yested.fadeIn
import net.yested.utils.on
import net.yested.utils.off
import kotlin.js.dom.html.HTMLInputElement
import net.yested.whenAddedToDom

public enum class TagsInputFieldType(val className: String) {
    INFO: TagsInputFieldType("info")
    PRIMARY : TagsInputFieldType("primary")
    DANGER: TagsInputFieldType("danger")
    SUCCESS: TagsInputFieldType("success")
    DEFAULT: TagsInputFieldType("default")
    WARNING: TagsInputFieldType("warning")
}

native
private fun JQuery.tagsinput(command: dynamic = null, data: dynamic = null): dynamic = noImpl

private data class TagsInputBeforeEvent<T>(val item: T, var cancel: Boolean)
private data class TagsInputAfterEvent<T>(val item: T)

public enum class BeforeEventPermission {
    PREVENT
    ALLOW
}

private fun tagsInputBeforeEventHandler<T>(event: TagsInputBeforeEvent<T>, func: ((T) -> BeforeEventPermission)?) {
    event.cancel = when (func?.invoke(event.item) ?: BeforeEventPermission.ALLOW) {
        BeforeEventPermission.PREVENT -> true
        BeforeEventPermission.ALLOW -> false
    }
}

public class TagsInputField<T>(val textFactory: (T) -> String = {it.toString()},
                               val typeFactory: (T) -> TagsInputFieldType = {TagsInputFieldType.DEFAULT},
                               val idFactory: ((T) -> Any)? = null,
                               inputSize: InputSize = InputSize.DEFAULT) : InputField<Array<T>>(inputSize, placeholder = null, type = "text"){

    public var maxTagCount: Int? = null
    public var onAddExistingTag: (T, JQuery) -> Unit = {item, jqTag -> jqTag.hide {jqTag.fadeIn(400, {})}}
    public var onBeforeItemAdd: ((T) -> BeforeEventPermission)? = null
    public var onAfterItemAdded: ((T) -> Unit)? = null
    public var onBeforeItemRemove: ((T) -> BeforeEventPermission)? = null
    public var onAfterItemRemoved: ((T) -> Unit)? = null
    public var maxLengthOfSingleTag: Int? = null
    public var removeWhiteSpacesAroundTagsAutomatically: Boolean = false
    public var allowDuplicates: Boolean = false

    // TODO freeInput, typeahead
    public var initialized: Boolean = false

	override var data: Array<T>
		get() = tags
		set(value) {
			tags = value
		}

    {
        element.setAttribute("data-role", "tagsinput")
    }

    public fun add(newElem: T) {
        if (!initialized) {
            return
        }
        jq(this.element).tagsinput("add", newElem)
    }

    public fun remove(newElem: T) {
        if (!initialized) {
            return
        }
        jq(this.element).tagsinput("remove", newElem)
    }

    public fun removeAll() {
        if (!initialized) {
            return
        }
        jq(this.element).tagsinput("removeAll")
    }

    override fun clear() {
        removeAll()
    }

    public fun focus() {
        if (!initialized) {
            return
        }
        jq(this.element).tagsinput("focus")
    }

    public fun input(): JQuery =
            if (!initialized) {
                jq(this.element)
            } else {
                jq(this.element).tagsinput("input")
            }

    public fun refresh() {
        if (!initialized) {
            return
        }
        jq(this.element).tagsinput("refresh")
    }

    public fun destroy() {
        if (!initialized) {
            return
        }
        val jqElement = jq(this.element);
        jqElement.tagsinput("destroy")
        jqElement.off("beforeItemAdd")
        jqElement.off("itemAdded")
        jqElement.off("beforeItemRemove")
        jqElement.off("itemRemoved")
        initialized = false
    }

    public fun init() {
        if (initialized) {
            return
        }
        val jqElement = jq(this.element)
        this.element.removeAttribute("placeholder")
        val options = object {
            val tagClass = {(item: T) -> "label label-${typeFactory(item).className}" }
            val itemValue = idFactory
            val itemText = textFactory
            val maxTags = maxTagCount
            val maxChars = maxLengthOfSingleTag
            val trimValue = removeWhiteSpacesAroundTagsAutomatically
            val allowDuplicates = this@TagsInputField.allowDuplicates
            val onTagExists = onAddExistingTag
        }
        // HACK: if itemValue is defined for non-object data, then it doesn't work
        if (idFactory == null) {
            js("delete options.itemValue")
            js("delete options.itemText")
        }
        jqElement.tagsinput(options)
        jqElement.on("beforeItemAdd", {event -> tagsInputBeforeEventHandler(event, onBeforeItemAdd)})
		jqElement.on("itemAdded", {event -> onAfterItemAdded?.invoke(event.item)})
		jqElement.on("beforeItemRemove", {event -> tagsInputBeforeEventHandler(event, onBeforeItemRemove)})
		jqElement.on("itemRemoved", {event -> onAfterItemRemoved?.invoke(event.item)})
        initialized = true
    }

    public var tags: Array<T>
        get() {
            return if (initialized) {
                jq(this.element).tagsinput("items")
            } else {
                array<T>()
            }
        }
        set(value) {
            if (initialized) {
                removeAll()
                value.forEach {add(it)}
            } else {
                this.value = value.joinToString(",")
            }
        }

    {
        element.whenAddedToDom {
            init()
        }
    }
}
