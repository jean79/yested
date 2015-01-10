package gettingstarted

import net.yested.Div
import net.yested.bootstrap.row
import net.yested.bootstrap.pageHeader
import net.yested.div
import net.yested.bootstrap.Medium

/**
 * Created by jean on 6.1.2015.
 */
fun gettingStartedSection(): Div {

    return div {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Getting Started" } }
            }
        }
        row {
            col(Medium(6)) {
                div {
                    h4 { +"1. Get Intellij Idea" }
                    p {
                        a(href = "https://www.jetbrains.com/idea/") { +"Get Intellij Idea 14 from JetBrains." }
                    }
                }
            }
        }
        row {
            col(Medium(12)) {
                h4 { +"2. Enable Kotlin Plugin" }
                p {
                    +"Install JetBrains Kotlin plugin into Idea."
                    img(src = "demo-site/img/plugin_install.png")
                }
            }
        }
        row {
            col(Medium(12)) {
                h4 { +"3. Create Kotlin - Javascript project" }
                p { +"Call it 'YestedSample'"}
                img(src = "demo-site/img/create_project.png")
            }
        }
        row {
            col(Medium(12)) {
                h4 { +"4. Create Kotlin Javascript Library" }
                img(src = "demo-site/img/create_project_create_lib.png")
            }
        }
        row {
            col(Medium(12)) {
                p {
                    +"Select:"
                    emph { +"Copy to: lib" }
                }
                img(src = "demo-site/img/create_library.png")
            }
        }
        row {
            col(Medium(12)) {
                h4 { +"4. Add Yested Library dependency" }
                img(src = "demo-site/img/add_library_dependency.png")
            }
        }
        row {
            col(Medium(12)) {
                h4 { +"5. Create index.html file" }
                +"Create HTML wrapper for our Yested application. It is a simple HTML that contains placeholder div with id 'page',"
                +"Place index.html in the root of your project."
                +"It mainly references Boostrap and JQuery libraries. If you are not going to use Boostrap, you can have empty index.html with just placeholder div."
                code(lang = "html", content =
                """<!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Yested Sample</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>

    <body role="document">

        <div id="page"></div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

        <script src="out/production/YestedSample/lib/kotlin.js"></script>
        <script src="out/production/YestedSample/lib/Yested.js"></script>
        <script src="out/production/YestedSample/YestedSample.js"></script>

    </body>
    </html>
    """)
            }
        }
        row  {
            col(Medium(12)) {
                h4 { + "6. Create basic Yested application"}
            }
        }
        row {
            col(Medium(6)) {
                +"""Create file sample.kt in src/main/kotlin and insert content on the right.<br />
                Kotlin Javascript calls this main function when page is loaded.
                """
            }
            col(Medium(6)) {
                code(lang="kotlin", content=
"""import net.yested.bootstrap.page

fun main(args: Array<String>) {
    page("page") {
        content {
             +"Hello World"
            br()
            a(href = "http://www.yested.net") { +"link to yested.net"}
            ol {
                li { +"First item" }
                li { +"Second item" }
            }
        }
    }
}
"""                )
            }
        }
        row  {
            col(Medium(12)) {
                h4 { + "7. Build Project"}
                p {
                    +"Build -> Make Module 'YestedSample'"
                    br()
                    +"It should generate all javascript libraries into out/production/YestedSample."
                    +"We reference these files in our index.html file."
                }
            }
        }
        row  {
            col(Medium(12)) {
                h4 { + "8. Create build configuration"}
                p {
                    +"Create build configuration - Kotlin - Javascript. "
                    +"Set correct path to our index.html"
                    img(src="demo-site/img/build.png")
                }
            }
        }
        row  {
            col(Medium(12)) {
                h4 { + "9. Run It!"}
            }
        }
    }
}



