package bootstrap

import net.yested.*
import net.yested.bootstrap.*

data class MailFolder(val name:String, val unread:Int)

fun createListGroupSection(id: String): Div {

    val selectedItems = Span()

    val data = arrayListOf(MailFolder("Inbox", 15), MailFolder("Sent", 3), MailFolder("Work", 154))

    val listGroup = ListGroup<MailFolder>(selectionMode = SelectionMode.MULTI, sortable = true, renderer = { badge { +"${it.unread}"; }; +it.name })

    listGroup.dataProvider = data
    listGroup.addOnChangeListener {
        selectedItems.setContent(listGroup.data.map { it.name }.joinToString(","))
    }

    var someIndex = 1;

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"List Groups" } }
            }
        }
        row {
            col(Medium(4)) {
                div {
                    +"Twitter Bootstrap List Groups with dynamic features:"
                    ul {
                        li { +"Selections (none, single line or multiple lines)" }
                        li { +"Ability to set selection programmatically"}
                        li { +"Ability to set data provider, to add new items and to remove items"}
                        li { +"Sortable by mouse (optional feature)"}
                        li { +"Ability to get back data provider in proper order"}
                        li { +"Ability to listen on changes to selections"}
                    }
                }
                br()
                h4 { +"Demo" }
                +listGroup
                strong { +"Selected items: " }
                +selectedItems
                br()
                br()
                btsButton(size = ButtonSize.EXTRA_SMALL, look = ButtonLook.DANGER, label = { +"Clear selection"}, onclick = { listGroup.clear() })
                nbsp()
                btsButton(size = ButtonSize.EXTRA_SMALL, look = ButtonLook.PRIMARY, label = { +"Add"}, onclick = {
                    listGroup.addItem(
                            MailFolder("Other ${someIndex++}", (Math.random()*10).toInt()))
                })
                nbsp()
                btsButton(size = ButtonSize.EXTRA_SMALL, label = { +"Remove first"}, onclick = {
                    if (listGroup.dataProvider.size > 0) {
                        listGroup.removeItem(listGroup.dataProvider.first())
                    }
                })
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content=
"""val selectedItems = Span()

val data = arrayListOf(MailFolder("Inbox", 15), MailFolder("Sent", 3), MailFolder("Work", 154))

val listGroup = ListGroup<MailFolder>(selectionMode = SelectionMode.MULTI, sortable = true, renderer = { badge { +"$\{it.unread}"; }; +it.name })

listGroup.dataProvider = data
listGroup.addOnChangeListener {
    selectedItems.setContent(listGroup.data.map { it.name }.join(","))
}

var someIndex = 1;
...
+listGroup
strong { +"Selected items: " }
+selectedItems
br()
br()
btsButton(size = ButtonSize.EXTRA_SMALL, look = ButtonLook.DANGER, label = { +"Clear selection"}, onclick = { listGroup.clear() })
nbsp()
btsButton(size = ButtonSize.EXTRA_SMALL, look = ButtonLook.PRIMARY, label = { +"Add"}, onclick = {
    listGroup.addItem(
            MailFolder("Other $\{someIndex++}", (Math.random()*10).toInt()))
})
nbsp()
btsButton(size = ButtonSize.EXTRA_SMALL, label = { +"Remove first"}, onclick = {
    if (listGroup.dataProvider.size() > 0) {
        listGroup.removeItem(listGroup.dataProvider.first())
    }
})""")
            }
        }
    }

}