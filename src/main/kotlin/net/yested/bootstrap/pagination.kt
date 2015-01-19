package net.yested.bootstrap

import net.yested.ComponentContainer
import net.yested.UL
import net.yested.with
import net.yested.Li
import java.util.ArrayList
import net.yested.HTMLComponentContainer

/**
 * <nav>
<ul class="pagination">
<li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
...
</ul>
</nav>
 */
public class Pagination(val count:Int, val defaultSelection:Int = 1, val listener:(Int) -> Unit) : HTMLComponentContainer("nav") {

    private var selectedItem:Int = defaultSelection

    private val list = UL() with { clazz = "pagination" }

    private var items:List<Li> = arrayListOf()

    public var selected:Int
        get() = selectedItem
        set(newValue) {
            selectedItem = newValue
            redisplay(selectedItem)
        }

    {
        appendChild(list)
        replaceItems()
        redisplay(selectedItem)
    }

    private fun replaceItems() {
        items = generateItems()
        list.setContent("")
        items.forEach { list.appendChild(it) }
    }

    private fun generateItems(): List<Li> {
        val newList = ArrayList<Li>()
        newList.add(Li().with { "style" .. "cursor: pointer;"; a(onclick = { backward() }) { span { +"&laquo;" } } })
        for (i in 1..count) {
            newList.add(Li() with { a(onclick = { select(i) } ) { "style" .. "cursor: pointer;"; span { +"${i}" } } })
        }
        newList.add(Li() with { "style" .. "cursor: pointer;"; a(onclick = { forward() }) { span() { +"&raquo;"}} })
        return newList
    }

    private fun backward() {
        if (selectedItem > 1) {
            selectedItem--
            changeSelection()
        }
    }

    private fun forward() {
        if (selectedItem < count) {
            selectedItem++
            changeSelection()
        }
    }

    private fun select(newPosition:Int) {
        if (newPosition != selectedItem) {
            selectedItem = newPosition
            changeSelection()
        }
    }

    private fun changeSelection() {
        redisplay(selectedItem)
        listener(selectedItem)
    }

    private fun redisplay(position:Int) {
        for (i in 1 .. count) { items.get(i).clazz = "" }
        items.get(position).clazz = "active"
        items.get(0).clazz = if (position == 1) "disabled" else ""
        items.get(items.size() - 1).clazz = if (position == count) "disabled" else ""
    }

}

public fun ComponentContainer.pagination(count:Int, defaultSelection:Int = 1, listener:(Int) -> Unit): Unit =
    appendChild( net.yested.bootstrap.Pagination(count,defaultSelection, listener) )
