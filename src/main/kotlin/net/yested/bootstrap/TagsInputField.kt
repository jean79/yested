package net.yested.bootstrap

import net.yested.BooleanAttribute
import net.yested.HTMLComponent
import jquery.JQuery
import jquery.jq
import net.yested.hide
import net.yested.fadeIn
import net.yested.utils.on
import net.yested.utils.off

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
                               val idFactory: (T) -> Any = {it},
                               inputSize: InputSize = InputSize.DEFAULT) : InputField(inputSize, placeholder = null, type = "text"){

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

    {
        element.setAttribute("data-role", "tagsinput")
    }

    public fun add(newElem: T) {
        jq(this.element).tagsinput("add", newElem)
    }

    public fun remove(newElem: T) {
        jq(this.element).tagsinput("remove", newElem)
    }

    public fun removeAll() {
        jq(this.element).tagsinput("removeAll")
    }

    public fun focus() {
        jq(this.element).tagsinput("focus")
    }

    public fun input(): JQuery = jq(this.element).tagsinput("input")

    public fun refresh() {
        jq(this.element).tagsinput("refresh")
    }

    public fun destroy() {
        val jqElement = jq(this.element);
        jqElement.tagsinput("destroy")
        jqElement.off("beforeItemAdd")
        jqElement.off("itemAdded")
        jqElement.off("beforeItemRemove")
        jqElement.off("itemRemoved")
        initialized = false
    }

    public fun init() {
        val jqElement = jq(this.element)
        this.element.removeAttribute("placeholder")
        jqElement.tagsinput(object {
            val tagClass = {(item: T) -> "label label-${typeFactory(item).className}"}
            val itemValue = idFactory
            val maxTags = maxTagCount
            val maxChars = maxLengthOfSingleTag
            val trimValue = removeWhiteSpacesAroundTagsAutomatically
            val allowDuplicates = this@TagsInputField.allowDuplicates
            val onTagExists = onAddExistingTag
        })
        jqElement.on("beforeItemAdd", {event -> tagsInputBeforeEventHandler(event, onBeforeItemAdd)})
        jqElement.on("itemAdded", {event -> onAfterItemAdded?.invoke(event.item)})
        jqElement.on("beforeItemRemove", {event -> tagsInputBeforeEventHandler(event, onBeforeItemRemove)})
        jqElement.on("itemRemoved", {event -> onAfterItemRemoved?.invoke(event.item)})
        initialized = true
    }

    public var tags: Array<T>
        get() {
            return jq(this.element).tagsinput("items")
        }
        set(value) {
            value.forEach {
                add(it)
            }
        }
}
