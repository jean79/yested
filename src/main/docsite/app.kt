import net.yested.bootstrap.page
import net.yested.bootstrap.CheckBox
import net.yested.bootstrap.btsButton
import net.yested.bootstrap.ButtonLook
import net.yested.bootstrap.ButtonSize
import net.yested.text
import net.yested.bootstrap.row
import net.yested.bootstrap.Navbar
import net.yested.bootstrap.navbar
import net.yested.registerHashChangeListener
import net.yested.div
import bootstrap.bootstrapPage
import basics.basicPage
import html.htmlPage
import ajax.ajaxPage
import complex.masterDetail
import gettingstarted.gettingStartedSection
import complex.createSpinner
import net.yested.with
import net.yested.bootstrap.glyphicon
import net.yested.bootstrap.textInput
import net.yested.ButtonType
import net.yested.bootstrap.NavbarLook
import net.yested.bootstrap.NavbarPosition

fun main(args: Array<String>) {

	val navbar = Navbar(id = "appMenuBar", look = NavbarLook.INVERSE, position = NavbarPosition.FIXED_TOP) with {
		brand(href = "#") { +"Yested" }
		item(href = "#gettingstarted") { +"Getting Started" }
		dropdown(label = { +"Examples" }) {
			item(href = "#html") { +"Basic HTML" }
			item(href = "#bootstrapComponents") { +"Twitter Bootstrap" }
			item(href = "#ajax") { +"Ajax Call" }
			item(href = "#masterdetail") { +"Master/Detail" }
			item(href = "#spinner") { +"Spinner" }
		}
	}

	val divContainer = div {}

	registerHashChangeListener { hash ->
		when (hash.get(0)) {
			"#", "" -> divContainer.fade(basicPage())
			"#gettingstarted" -> divContainer.fade(gettingStartedSection())
			"#html" -> divContainer.fade(htmlPage())
			"#bootstrapComponents" -> {
				if (hash.size() == 1) {
					divContainer.fade(bootstrapPage())
				}
			}
			"#ajax" -> divContainer.fade(ajaxPage())
			"#masterdetail" -> divContainer.fade(masterDetail())
			"#spinner" -> divContainer.fade(createSpinner())
		}
	}

	page("page") {
		topMenu(navbar)
		content {
			div {
				+"Ahoj"
				br(); br();
				+divContainer
			}
		}
		footer {
			small {
				emph { +"Contact: " }
				a(href = "mailto:jan.kovar79@gmail.com") { +"jan.kovar79@gmail.com" }
			}
			br(); br()
		}
	}

}