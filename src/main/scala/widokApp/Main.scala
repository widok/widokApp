package widokApp

import org.widok._
import org.widok.html._
import org.widok.bindings.Bootstrap._
import org.widok.bindings.HTML.Label

case class Issue(name: String, id: Long)

object Main extends PageApplication {

  val jsonResult = Var("")
  val hasResult = jsonResult.map { x => x.nonEmpty }
  val name = Var("")
  val hasName = name.map(_.nonEmpty)
  val prodName = "Skan"

  val issues = Buffer[Ref[Issue]]()
  val hasIssues = issues.nonEmpty

  val navbar = NavigationBar(Container(
    NavigationBar.Header(NavigationBar.Toggle(), NavigationBar.Brand(prodName)),
    NavigationBar.Collapse()))

  val qryPane = div(
    Label("Enter URL").forId("url"),
    Input.Text().placeholder("URL").id("url").bind(name).size(Size.Large),
    br(),
    Button("Create").onClick(x => jsonResult := FactExtractor.extactFact(name.get)).css("btn-primary"),
    span(" "),
    Button("Clear").onClick(x => {
      jsonResult := ""
      name := ""
      issues.+=(Ref(FactExtractor.createIssue(name.get)))
    }).css("btn-primary")).css("col-md-3")

  lazy val issuesList = div(ul(issues.map {
    x => li(x.get.id)
  })).show(hasIssues)

  val resultsPane = div(h3("Extracted Data in JSON"), jsonResult, ul(li("list"))).css("col-md-9").show(hasResult)
  val container = Container(qryPane, resultsPane) // , resultsPane

  def view() = {
    Inline(navbar, container, issuesList)
  }

  def ready() {
    issues.+=(Ref(FactExtractor.createIssue("")))
  }
}

object FactExtractor {

  def extactFact(url: String) = s"""{
  "source" : "${url}", 
  "date" : "${System.currentTimeMillis()}"},
  "fact-data" : "loren Ipsum"  
  }"""

  def createIssue(url: String) = Issue(url, System.currentTimeMillis())
}

