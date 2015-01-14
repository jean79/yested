package bootstrap

import net.yested.Div
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.Medium

import jquery.jq
import jquery.JQuery
import net.yested.bootstrap.navbar
import net.yested.ParentComponent
import net.yested.with
import net.yested.HTMLParentComponent



fun bootstrapPage(): Div =
		div {
			row {
				row {
					col(Medium(12)) {
						pageHeader {
							h3 { +"Twitter Bootstrap wrappers" }
						}
						+"Yested Framework provides simple wrappers for some Twitter Boootstrap components."
					}
				}
				row {
					col(Medium(10)) {
						+createButtons(id = "bootstrapComponents_Buttons")
						+createTypographySection(id = "bootstrapComponents_Typography")
						+buttonGroupsSection(id = "bootstrapComponents_ButtonGroups")
						+createForm(id = "bootstrapComponents_Form")
						+createSelectSection(id = "bootstrapComponents_Select")
						+createInputs(id = "bootstrapComponents_Inputs")
						+createGrid(id = "bootstrapComponents_Grid")
						+createTabs(id = "bootstrapComponents_Tabs")
						+createPanelSection(id = "bootstrapComponents_Panel")
						+createDialogs(id = "bootstrapComponents_Dialogs")
						+createMediaObjectSection(id = "bootstrapComponents_MediaObject")
						+createPaginationSection(id = "bootstrapComponents_Pagination")
						+createNavbarSection(id = "bootstrapComponents_Navbar")
					}
					col(Medium(2)) {
						div {
							id = "bootstrapNavbar"
							ul {
								clazz = "nav nav-pills nav-stacked affix"
								li { a(href = "#bootstrapComponents_Buttons") { +"Buttons" }; clazz = "active" }
								li { a(href = "#bootstrapComponents_Typography") { +"Typography" } }
								li { a(href = "#bootstrapComponents_ButtonGroups") { +"Button Group" } }
								li { a(href = "#bootstrapComponents_Form") { +"Form" } }
								li { a(href = "#bootstrapComponents_Select") { +"Select" } }
								li { a(href = "#bootstrapComponents_Inputs") { +"Text Input with Validation" } }
								li { a(href = "#bootstrapComponents_Grid") { +"Grid" } }
								li { a(href = "#bootstrapComponents_Tabs") { +"Tabs" } }
								li { a(href = "#bootstrapComponents_Panel") { +"Panels" } }
								li { a(href = "#bootstrapComponents_Dialogs") { +"Dialogs" } }
								li { a(href = "#bootstrapComponents_MediaObject") { +"Media Object" } }
								li { a(href = "#bootstrapComponents_Pagination") { +"Pagination" } }
								li { a(href = "#bootstrapComponents_Navbar") { +"Navbar" } }
							}
						}
					}
				}
			}
		}