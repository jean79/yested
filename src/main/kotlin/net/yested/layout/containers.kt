package net.yested.layout

import net.yested.*

public enum class VerticalAlign(val code:String) {
    TOP: VerticalAlign("top")
    MIDDLE: VerticalAlign("middle")
    BOTTOM: VerticalAlign("bottom")
}

public class HorizontalContainer(width: Dimension? = null, height: Dimension? = null, val gap: Dimension? = null) : HTMLComponent("div") {

    init {
        style = "display: table; width: ${width?.toHtml()?:""}; height: ${height?.toHtml()?:""};"
    }

    public fun column(width:Dimension? = null, height:Dimension? = null, verticalAlign: VerticalAlign? = VerticalAlign.TOP, init: HTMLComponent.()->Unit) {
        if (element.childNodes.length > 0 && gap != null) { //add spacer
            +(Div() with {
                style = "display: table-cell;"
                div {
                    style = "width: ${gap.toHtml()};"
                }
            })
        }
        +(Div() with {
            style = "position: relative; display: table-cell; width: ${width?.toHtml()?:""}; height: ${height?.toHtml()?:""}; vertical-align: ${verticalAlign?.code};"
            init()
        })
    }

}

public fun HTMLComponent.horizontalContainer(width: Dimension? = null, height: Dimension? = null,  gap: Dimension? = null, init:HorizontalContainer.()->Unit) {
    +(HorizontalContainer(width = width, height = height, gap = gap) with {
        init()
    })
}

public class VerticalContainer(width: Dimension? = null, height: Dimension? = null) : HTMLComponent("table") {

    init {
        style = "table-layout: fixed; width: ${width?.toHtml()?:""}; height: ${height?.toHtml()?:""};"
    }

    public fun row(width:Dimension? = null, height:Dimension? = null, init: HTMLComponent.()->Unit) {

        +(TRBody() with {
            this.element.setAttribute("style", "width: ${width?.toHtml()?:""}; height: ${height?.toHtml()?:""}")
            td {
                this.element.setAttribute("style", "width: ${width?.toHtml()?:""}; height: ${height?.toHtml()?:""}")
                init()
            }
        })

    }

}

public fun HTMLComponent.verticalContainer(width: Dimension? = null, height: Dimension? = null, init:VerticalContainer.()->Unit) {
    +(VerticalContainer(width = width, height = height) with {
        init()
    })
}