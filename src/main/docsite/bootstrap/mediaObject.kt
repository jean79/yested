package bootstrap

import net.yested.Div
import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.bootstrap.Medium
import net.yested.bootstrap.mediaObject
import net.yested.bootstrap.MediaAlign

fun createMediaObjectSection(id: String): Div {

	return div {
		this.id = id
		row {
			col(Medium(12)) {
				pageHeader { h3 { +"Media Object" } }
			}
		}
		row {
			col(Medium(4)) {
				div {
					+"""Media object is used for creating components that should contain left- or rightaligned
						media (image, video, or audio) alongside some textual content. It is best
						suited for creating features such as a comments section, displaying tweets, or
						showing product details where a product image is present."""
				}
				br()
				h4 { +"Demo" }
				mediaObject(MediaAlign.Left) {
					media {
						img(src = "demo-site/img/leaf.gif")
					}
					content {
						heading {
							+"Media Object"
						}
						content {
							p {
								+"""Media object is used for creating components that should contain left- or rightaligned
							media (image, video, or audio) alongside some textual content. It is best
							suited for creating features such as a comments section, displaying tweets, or
							showing product details where a product image is present."""
							}
							mediaObject(MediaAlign.Left) {
								media {
									img(src = "demo-site/img/leaf.gif")
								}
								content {
									heading {
										+"Nested Media Object"
									}
									content {
										p {+" Nested Text"}
									}
								}
							}
						}
					}
				}
			}
			col(Medium(8)) {
				h4 { +"Code" }
				code(lang = "kotlin", content = """
mediaObject(MediaAlign.Left) {
	media {
		img(src = "demo-site/img/leaf.gif")
	}
	content {
		heading {
			+ "Media Object"
		}
		content {
			+ p { "Media object is used ..." }
			mediaObject(MediaAlign.Left) {
				media {
					img(src = "demo-site/img/leaf.gif")
				}
				content {
					heading {
						+ "Nested Media Object"
					}
					content {
						+ p { "Nested Text" }
					}
				}
			}
		}
	}
}
				""")
			}
		}
	}
}