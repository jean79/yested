package bootstrap

import net.yested.*
import net.yested.bootstrap.*
import org.w3c.dom.HTMLElement

data class People(val name: String, val age: Int)

class TagsSection(id: String) : Component {

    private fun showMsg(msg: String) {
        eventDiv with { setContent(msg) }
    }

    val someData = listOf(
            People("Balázs", 25),
            People("Jan", 35),
            People("Eliza", 31)
    )

    val eventDiv = Div()
    val tagsField = TagsInputField<People>(
            textFactory = { it.name },
            idFactory = { it.name },
            typeFactory = {
                if (it.name == "Jan") {
                    TagsInputFieldType.SUCCESS
                } else if (it.age < 30) {
                    TagsInputFieldType.INFO
                } else if (it.age < 40) {
                    TagsInputFieldType.WARNING
                } else {
                    TagsInputFieldType.DANGER
                }
            }
    )

    val randomPeople = arrayOf(People("Luke Skywalker", 22), People("Leia Organa", 22), People("Yoda", 900), People("Obi-Wan Kenobi", 57), People("Darth Vader", 46))
    var iterator = randomPeople.iterator()
    val btn = BtsButton(label = { +"Add People" }) {
        tagsField.add(if (iterator.hasNext()) iterator.next() else {
            iterator = randomPeople.iterator(); iterator.next()
        })
    }

    override val element: HTMLElement = (div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"TagsInputField" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"""TagsInputField demo with listener."""
                }
                br()
                h4 { +"Demo" }
                +tagsField
                +eventDiv
                br()
                +btn
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content = """
val tagsField = TagsInputField<People>(
    textFactory = { it.name },
    idFactory = { it.name },
    typeFactory = {
        if (it.name == "Jan") {
            TagsInputFieldType.SUCCESS
        } else if (it.age < 30) {
            TagsInputFieldType.INFO
        } else if (it.age < 40) {
            TagsInputFieldType.WARNING
        } else {
            TagsInputFieldType.DANGER
        }
    }
)
...
tagsField.onAddExistingTag = { addingPeople, jqTag ->
    jqTag.hide { jqTag.fadeIn(400, {}) }
    showMsg("onAddExistingTag: $\{addingPeople.name}")
}
tagsField.onAfterItemAdded = { item ->
    showMsg("added: $\{item.name}")
}
tagsField.onAfterItemRemoved = { item ->
    showMsg("removed: $\{item.name}")
}
tagsField.onBeforeItemAdd = { item ->
    if (item.name == "Darth Vader") {
        showMsg("Vader is just too evil to appear here: $\{item.name}")
        BeforeEventPermission.PREVENT
    } else {
        BeforeEventPermission.ALLOW
    }
}
tagsField.onBeforeItemRemove = { item ->
    if (item.name == "Leia Organa") {
        showMsg("Leia stays here!")
        BeforeEventPermission.PREVENT
    } else {
        showMsg("removed: $\{item.name}")
        BeforeEventPermission.ALLOW
    }
}
""")
            }
        }
    }).element

    init {
        element.whenAddedToDom {
            tagsField.tags = someData.toTypedArray()
        }
        tagsField.onAddExistingTag = { addingPeople, jqTag ->
            jqTag.hide { jqTag.fadeIn(400, {}) }
            showMsg("onAddExistingTag: ${addingPeople.name}")
        }
        tagsField.onAfterItemAdded = { item ->
            showMsg("added: ${item.name}")
        }
        tagsField.onAfterItemRemoved = { item ->
            showMsg("removed: ${item.name}")
        }
        tagsField.onBeforeItemAdd = { item ->
            if (item.name == "Darth Vader") {
                showMsg("Vader is just too evil to appear here: ${item.name}")
                BeforeEventPermission.PREVENT
            } else {
                BeforeEventPermission.ALLOW
            }
        }
        tagsField.onBeforeItemRemove = { item ->
            if (item.name == "Leia Organa") {
                showMsg("Leia stays here!")
                BeforeEventPermission.PREVENT
            } else {
                showMsg("removed: ${item.name}")
                BeforeEventPermission.ALLOW
            }
        }
    }
}