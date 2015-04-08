package widokApp

import org.widok._
import org.widok.html._
import org.widok.bindings.Bootstrap._
import org.widok.bindings.HTML.Label

object Main extends PageApplication {

  val jsonResult = Var("")
  val hasResult = jsonResult.map { x => x.nonEmpty }
  val name = Var("")
  val hasName = name.map(_.nonEmpty)
  val prodName = "Factex"

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
    }).css("btn-primary")).css("col-md-3")

  val resultsPane = div(h3("Extracted Data in JSON"), jsonResult).css("col-md-9").show(hasResult)
  val container = Container(qryPane, resultsPane) // , resultsPane

  def view() = {
    Inline(navbar, container)
  }

  def ready() {}
}

object FactExtractor {

  def extactFact(url: String) = s"""{
  "source" : "${url}", 
  "date" : "${System.currentTimeMillis()}"},
  "fact-data" : "loren Ipsum"  
  }"""
}

