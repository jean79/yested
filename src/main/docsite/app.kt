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
import bootstrap.effectsPage
import net.yested.Fade
import net.yested.Slide
import net.yested.bootstrap.ContainerLayout

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
			item(href = "#effects") { +"Effects" }
		}
	}

	val divContainer = div {}

	registerHashChangeListener { hash ->
		when (hash.get(0)) {
			"#", "" -> divContainer.setChild(basicPage(), Fade())
			"#gettingstarted" -> divContainer.setChild(gettingStartedSection(), Fade())
			"#html" -> divContainer.setChild(htmlPage(), Fade())
			"#bootstrapComponents" -> {
				if (hash.size() == 1) {
					divContainer.setChild(bootstrapPage(), Fade())
				}
			}
			"#ajax" -> divContainer.setChild(ajaxPage(), Fade())
			"#masterdetail" -> divContainer.setChild(masterDetail(), Fade())
			"#spinner" -> divContainer.setChild(createSpinner(), Fade())
			"#effects" -> divContainer.setChild(effectsPage(), Fade())
		}
	}

	page(placeholderElementId = "page") {
		topMenu(navbar)
		content {
			div {
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