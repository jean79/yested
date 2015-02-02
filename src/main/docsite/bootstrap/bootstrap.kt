package bootstrap

import net.yested.Div
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.Medium

import net.yested.bootstrap.enableScrollSpy

fun bootstrapPage(): Div {

    enableScrollSpy(id = "bootstrapNavbar")

    return div {

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
					+createSplitButtonDropdown(id = "bootstrapComponents_SplitButtonDropdown")
                    +createDropDownsSection(id = "bootstrapComponents_Dropdown")
                    +createTypographySection(id = "bootstrapComponents_Typography")
                    +buttonGroupsSection(id = "bootstrapComponents_ButtonGroups")
                    +createForm(id = "bootstrapComponents_Form")
                    +createSelectSection(id = "bootstrapComponents_Select")
                    +createInputs(id = "bootstrapComponents_Inputs")
                    +createCheckboxSection(id = "bootstrapComponents_Checkbox")
                    +createGrid(id = "bootstrapComponents_Grid")
                    +createTabs(id = "bootstrapComponents_Tabs")
                    +createPanelSection(id = "bootstrapComponents_Panel")
                    +createPanelContainerSection(id = "bootstrapComponents_PanelContainer")
                    +createDialogs(id = "bootstrapComponents_Dialogs")
                    +createAlertsSection(id = "bootstrapComponents_Alerts")
                    +createMediaObjectSection(id = "bootstrapComponents_MediaObject")
                    +createPaginationSection(id = "bootstrapComponents_Pagination")
                    +createNavbarSection(id = "bootstrapComponents_Navbar")
                    +createBreadcrumbsSection(id = "bootstrapComponents_Breadcrumbs")
                }
                col(Medium(2)) {
                    div {
                        id = "bootstrapNavbar"
                        ul {
                            clazz = "nav nav-pills nav-stacked affix"

                            li { a(href = "#bootstrapComponents_Buttons") { +"Buttons" }; clazz = "active" }
							li { a(href = "#bootstrapComponents_SplitButtonDropdown") { +"Split button dropdowns " }; clazz = "active" }
                            li { a(href = "#bootstrapComponents_Dropdown") { +"Dropdowns" }; clazz = "active" }
                            li { a(href = "#bootstrapComponents_Typography") { +"Typography" } }
                            li { a(href = "#bootstrapComponents_ButtonGroups") { +"Button Group" } }
                            li { a(href = "#bootstrapComponents_Form") { +"Form" } }
                            li { a(href = "#bootstrapComponents_Select") { +"Select" } }
                            li { a(href = "#bootstrapComponents_Inputs") { +"Text Input with Validation" } }
                            li { a(href = "#bootstrapComponents_Checkbox") { +"Checkbox" } }
                            li { a(href = "#bootstrapComponents_Grid") { +"Grid" } }
                            li { a(href = "#bootstrapComponents_Tabs") { +"Tabs" } }
                            li { a(href = "#bootstrapComponents_Panel") { +"Panels" } }
                            li { a(href = "#bootstrapComponents_PanelContainer") { +"Panel Container" } }
                            li { a(href = "#bootstrapComponents_Dialogs") { +"Dialogs" } }
                            li { a(href = "#bootstrapComponents_Alerts") { +"Alerts" } }
                            li { a(href = "#bootstrapComponents_MediaObject") { +"Media Object" } }
                            li { a(href = "#bootstrapComponents_Pagination") { +"Pagination" } }
                            li { a(href = "#bootstrapComponents_Navbar") { +"Navbar" } }
                            li { a(href = "#bootstrapComponents_Breadcrumbs") { +"Breadcrumbs" } }
                        }
                    }
                }
            }
        }
    }
}