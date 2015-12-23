package net.yested.bootstrap.smartgrid

import net.yested.Component
import net.yested.bootstrap.InputSize
import net.yested.bootstrap.StringInputField
import net.yested.with

public class TextInputFilterFactory<TYPE>(val filterFunctionFactory : Function1<String, Function1<TYPE, Boolean>>) : FilterFactory<TYPE> {

    override fun createFilterElement(newFilterHandler: (Filter<TYPE>?) -> Unit, filterConfig: String?): Component {
        return StringInputField(inputSize = InputSize.SMALL) with {
            addOnChangeLiveListener {
                if (this.data != "") {
                    newFilterHandler(Filter(filterFunctionFactory(this.data), filterConfig = this.data) )
                } else {
                    newFilterHandler(null)
                }
            }
            if (filterConfig != null) {
                this.data = filterConfig
                newFilterHandler(Filter(filterFunctionFactory(this.data), filterConfig = this.data) )
            }
            this.element.focus()
        }
    }

}

