package layout

import net.yested.*
import net.yested.bootstrap.*
import net.yested.layout.*

fun createHorizontalLayoutSection(): Div {

    return div {
        this.id = id
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Layout" } }
            }
        }
        row {
            col(Medium(4)) {
                h4 { +"Demo" }
                panel {
                    heading { +"Sample Panel" }
                    content {
                        horizontalContainer {
                            column(width = 100.pct()) {
                                scrollPane(horizontal = Overflow.AUTO) {
                                    div {
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF"
                                    }
                                }
                            }
                            column {
                                +"CCC"
                            }
                        }
                    }
                }
                panel {
                    heading { +"Sample Panel" }
                    content {
                        div {
                            "style".."height: 200px"
                            scrollPane(horizontal = Overflow.AUTO, vertical = Overflow.AUTO) {
                                div {
                                    +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFffffffffffffffffffffffffffffffffHHF"
                                }
                            }
                        }
                    }
                }
                panel {
                    heading { +"Sample Panel" }
                    content {
                        verticalContainer(width = 100.pct(), height = 300.px()) {
                            row(height = 100.pct(), width = 100.pct()) {
                                scrollPane(horizontal = Overflow.AUTO, vertical = Overflow.AUTO) {
                                    div {
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                        +"BBBCCCDDDEEEFFFGGGHHHJJJKKKFFFSASAEEERTTYUUJDFHFHFHFHFHHF<br>"
                                    }
                                }
                            }
                            row {
                                +"CCC"
                            }
                        }
                    }
                }
            }
            col(Medium(8)) {
                h4 { +"Code" }
                code(lang = "kotlin", content=
                """TODO:""")
            }
        }
    }

}