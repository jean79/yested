package basics

import net.yested.Div
import net.yested.div
import net.yested.ajaxGet
import net.yested.with
import net.yested.spin.spinner
import net.yested.bootstrap.aligned
import net.yested.bootstrap.TextAlign

fun generateDownloadSection(version:String):Div {
    return div {
        p {
            emph  { +"Binaries: "}
            a(href = "http://nexus.yested.net/nexus/content/repositories/releases/net/yested/Yested/${version}/Yested-${version}.jar") {
                + "Yested-${version}.jar"
            }
        }
        p {
            h4 { +"Maven Repository" }
            code(lang="xml", content =
            """<repository>
  <id>Yested</id>
  <url>http://nexus.yested.net/nexus/content/repositories/releases/</url>
</repository>

<dependency>
    <groupId>net.yested</groupId>
    <artifactId>Yested</artifactId>
    <version>${version}</version>
</dependency>
""")
        }
    }
}

fun createDownloadSection():Div {

    val placeholder = Div() with {
        div {
            br();br();br();br();br();br();br();
            spinner()
            aligned(align = TextAlign.CENTER) {
                +"Fetching latest release version, please wait..."
            }
        }
    }

    ajaxGet<String>(url = "http://nexus.yested.net/latest") {
        placeholder.setChild(generateDownloadSection(it.trim()))
    }

    return placeholder

}