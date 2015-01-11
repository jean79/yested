package net.yested.bootstrap

import net.yested.HTMLParentComponent
import net.yested.UL
import net.yested.with
import net.yested.Li
import java.util.ArrayList

/**
 * <nav>
<ul class="pagination">
<li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
...
</ul>
</nav>
 */
public class Pagination(val count:Int, val defaultSelection:Int = 1, val listener:(Int) -> Unit) : HTMLParentComponent("nav") {

    private var _selected:Int = defaultSelection

    private val _list = UL() with { clazz = "pagination" }

    private var _items:List<Li> = arrayListOf()

    public var selected:Int
        get() = _selected
        set(newValue) {
            _selected = newValue
            redisplay(_selected)
        }

    {
        add(_list)
        replaceItems()
        redisplay(_selected)
    }

    private fun replaceItems() {
        _items = generateItems()
        _list.replace("")
        _items.forEach { _list.add(it) }
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
        if (_selected > 1) {
            _selected--
            changeSelection()
        }
    }

    private fun forward() {
        if (_selected < count) {
            _selected++
            changeSelection()
        }
    }

    private fun select(newPosition:Int) {
        if (newPosition != _selected) {
            _selected = newPosition
            changeSelection()
        }
    }

    private fun changeSelection() {
        redisplay(_selected)
        listener(_selected)
    }

    private fun redisplay(position:Int) {
        for (i in 1 .. count) { _items.get(i).clazz = "" }
        _items.get(position).clazz = "active"
        _items.get(0).clazz = if (position == 1) "disabled" else ""
        _items.get(_items.size() - 1).clazz = if (position == count) "disabled" else ""
    }

}

public fun HTMLParentComponent.pagination(count:Int, defaultSelection:Int = 1, listener:(Int) -> Unit): Unit =
    add( net.yested.bootstrap.Pagination(count,defaultSelection, listener) )
