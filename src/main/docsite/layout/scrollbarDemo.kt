package layout

import net.yested.*
import net.yested.bootstrap.*
import net.yested.layout.ScrollBar
import net.yested.layout.ScrollBarOrientation


fun createScrollBarSection(): Div {

    val positionTextVertical = Span()
    val positionTextHorizontal = Span()

    val scrollBarVetical = ScrollBar(
                        orientation = ScrollBarOrientation.VERTICAL,
                        size = 150.px(),
                        numberOfItems = 100,
                        visibleItems = 20,
                        positionHandler = { positionTextVertical.setContent("Vertical: ${it}")} )

    val scrollBarHorizontal = ScrollBar(
            orientation = ScrollBarOrientation.HORIZONTAL,
            size = 150.px(),
            numberOfItems = 100,
            visibleItems = 20,
            positionHandler = { positionTextHorizontal.setContent("Horizontal: ${it}")} )

    val fieldTotalNumber = IntInputField() with { data  = 100 }
    val fieldViewportSize = IntInputField() with { data  = 20 }
    val fieldNewPosition = IntInputField() with { data = 30 }

    return div {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"ScrollBar" } }
            }
        }
        row {
            col(Medium(4)) {
                h4 { +"Demo" }
                btsForm(formStyle = FormStyle.HORIZONTAL) {
                    item(label = { +"Total number:" }) {
                        +fieldTotalNumber
                    }
                    item(label = { +"Viewport size:" }) {
                        +fieldViewportSize
                    }
                    item(label = {}) {
                        btsButton(size = ButtonSize.SMALL, label = { +"Set scrollbar"}) {
                            scrollBarVetical.setup(
                                    numberOfItems = fieldTotalNumber.data?:100,
                                    visibleItems = fieldViewportSize.data?:20,
                                    newPosition = 10)
                        }
                    }
                    item(label = {} ) { br() }
                    item(label = { +"Set position:" }) {
                        +fieldNewPosition
                    }
                    item(label = {}) {
                        btsButton(size = ButtonSize.SMALL, label = { +"Set scrollbar"}) {
                            scrollBarVetical.position = fieldNewPosition.data?:0
                        }
                    }
                }
                +positionTextVertical
                br()
                +scrollBarVetical
                br()
                +scrollBarHorizontal
                +positionTextHorizontal
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content=
"""TODO:""")
            }
        }
    }

}