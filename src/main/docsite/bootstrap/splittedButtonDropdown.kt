package bootstrap

import net.yested.bootstrap.Grid
import net.yested.bootstrap.Column
import net.yested.text
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.Div
import net.yested.bootstrap.btsButton
import net.yested.ButtonType
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.Medium
import net.yested.bootstrap.splitButtonDropdown
import net.yested.bootstrap.badge

fun createSplitButtonDropdown(id: String): Div {

	return div {
		this.id = id
		row {
			col(Medium(12)) {
				pageHeader { h3 { +"Split Dropdowns" } }
			}
		}
		row {
			col(Medium(4)) {
				div {
					+"""
Refer to Bootstrap Split button dropdowns.
"""
				}
				br()
				h4 { +"Demo" }
				var div = Div()
				div {
					splitButtonDropdown(id = "splitDropDownId", label = {+"Primary"},
							look = ButtonLook.PRIMARY,
							size = ButtonSize.DEFAULT,
							onClick = {div.setContent("Clicked: Primary")}) {
						link(href = "#One", onclick = {div.setContent("Clicked: One")} ) {
							+"One"
						}
						link(href = "#Two", onclick = {div.setContent("Clicked: Two")} ) {
							+"Two"
							badge { +"2"}
						}
						divider()
						link(href = "#Three", onclick = {div.setContent("Clicked: Three")} ) {
							+"Three"
						}
					}
				}
				+div
				div.setContent("Click on the buttons")
			}
			col(Medium(8)) {
				h4 { +"Code" }
				code(lang = "kotlin", content="""var div = Div()
div {
	splitButtonDropdown(id = "splitDropDownId", label = {+"Primary"},
			look = ButtonLook.PRIMARY,
			size = ButtonSize.DEFAULT,
			onClick = {div.setContent("Clicked: Primary")}) {
		onclick = {div.setContent("Clicked: Primary")}
		link(href = "#One", onclick = {div.setContent("Clicked: One")} ) {
			+"One"
		}
		link(href = "#Two", onclick = {div.setContent("Clicked: Two")} ) {
			+"Two"
			badge { +"2"}
		}
		divider()
		link(href = "#Three", onclick = {div.setContent("Clicked: Three")} ) {
			+"Three"
		}
	}
}
+div
div.setContent("Click on the buttons")""")
			}
		}
	}

}