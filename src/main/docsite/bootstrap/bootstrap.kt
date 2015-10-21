package bootstrap

import net.yested.Component
import net.yested.Div
import net.yested.bootstrap.Medium
import net.yested.bootstrap.enableScrollSpy
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.row
import net.yested.with
import org.w3c.dom.HTMLElement

class BootstrapPage : Component {

    override val element: HTMLElement = (Div() with {
        enableScrollSpy(id = "bootstrapNavbar")
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
                    +createTooltips(id = "bootstrapComponents_Tooltips")
                    +createSplitButtonDropdown(id = "bootstrapComponents_SplitButtonDropdown")
                    +createDropDownsSection(id = "bootstrapComponents_Dropdown")
                    +createTypographySection(id = "bootstrapComponents_Typography")
                    +buttonGroupsSection(id = "bootstrapComponents_ButtonGroups")
                    +createForm(id = "bootstrapComponents_Form")
                    +createSelectSection(id = "bootstrapComponents_Select")
                    +TagsSection(id = "bootstrapComponents_Tags")
                    +createInputs(id = "bootstrapComponents_Inputs")
                    +DateFieldSection(id = "bootstrapComponents_DateField")
                    +createListGroupSection(id = "bootstrapComponents_ListGroup")
                    +createCheckboxSection(id = "bootstrapComponents_Checkbox")
                    +createGrid(id = "bootstrapComponents_Grid")
                    +createTabs(id = "bootstrapComponents_Tabs")
                    +createCollapsibleSection(id = "bootstrapComponents_Collapsible")
                    +createPanelSection(id = "bootstrapComponents_Panel")
                    +createRowPanelContainerSection(id = "bootstrapComponents_PanelContainer")
                    +createFloatingPanelContainerSection(id = "bootstrapComponents_FloatingPanelContainer")
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

                            li { a(href = "#bootstrapComponents_Buttons") { +"Buttons" }}
                            li { a(href = "#bootstrapComponents_Tooltips") { +"Tooltip" }}
                            li { a(href = "#bootstrapComponents_SplitButtonDropdown") { +"Split button dropdowns " }}
                            li { a(href = "#bootstrapComponents_Dropdown") { +"Dropdowns" }}
                            li { a(href = "#bootstrapComponents_Typography") { +"Typography" } }
                            li { a(href = "#bootstrapComponents_ButtonGroups") { +"Button Group" } }
                            li { a(href = "#bootstrapComponents_Form") { +"Form" } }
                            li { a(href = "#bootstrapComponents_Select") { +"Select" } }
                            li { a(href = "#bootstrapComponents_Tags") { +"Tags" } }
                            li { a(href = "#bootstrapComponents_Inputs") { +"Text Input with Validation" } }
                            li { a(href = "#bootstrapComponents_DateField") { +"DateField" } }
                            li { a(href = "#bootstrapComponents_ListGroup") { +"List Groups" } }
                            li { a(href = "#bootstrapComponents_Checkbox") { +"Checkbox" } }
                            li { a(href = "#bootstrapComponents_Grid") { +"Grid" } }
                            li { a(href = "#bootstrapComponents_Tabs") { +"Tabs" } }
                            li { a(href = "#bootstrapComponents_Collapsible") { +"Collapsible" } }
                            li { a(href = "#bootstrapComponents_Panel") { +"Panels" } }
                            li { a(href = "#bootstrapComponents_PanelContainer") { +"Row Panel Container" } }
                            li { a(href = "#bootstrapComponents_FloatingPanelContainer") { +"Float Panel Container" } }
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
    }).element
}
