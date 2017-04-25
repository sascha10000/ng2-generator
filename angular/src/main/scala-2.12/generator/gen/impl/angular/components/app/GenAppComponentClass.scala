package generator.gen.impl.angular.components.app

import basic.{FileOps, Ops}
import generator.gen.GenFile
import generator.gen.impl.angular.Ng2Vars
import generator.gen.impl.angular.components.component.{GenComponentClass, GenComponentFiles}
import generator.gen.impl.angular.components.extractRoute
import generator.gen.template.BasicTemplate
import generator.gen.template.manipulators.StringReplacer

/**
  * Created by Sascha on 09.04.2017.
  */
class GenAppComponentClass(ngRoot:String, val imports:List[GenComponentFiles]) extends GenFile {
  val basePath = ngRoot + "/" + Ng2Vars.appSrcPath
  val templatePath = "generator/gen/impl/angular/components/app/AppComponent.tmpl"
  val template = BasicTemplate.fromFile(templatePath) << StringReplacer("MenuDefinition", genMenu)

  Ops.print { FileOps.deleteFile(basePath + "/" + filename()) }
  Ops.print { FileOps.createFileWithContent(basePath + "/" + filename, content) }

  override def path(): Option[String] = None
  override def filename(): String = "app.component.ts"
  override def content(): String = template.template

  def genMenu = {
    "navMenu:NavMenu = { items: [" +
      imports.foldLeft("")((prev, elem) => {
        val menuItems = List(
          addMenueItem("Show",elem, elem.genCompShow),
          addMenueItem("List",elem, elem.genCompList),
          addMenueItem("New..",elem, elem.genCompNew)
        )

        prev + (if(prev != "") "," else "") + "{ title: \"" + elem.compName + "\", items : [" +
          menuItems.foldLeft("")((prev, el) => {
            prev + (if(prev != "" && el != "") "," else "") + el
          }) +
        "]}"
      }) +
    "]};"
  }

  def addMenueItem(menuTitle:String, elem: GenComponentFiles, concreteComp: Option[GenComponentClass]):String = {
    if(concreteComp.isDefined) {
      val route = extractRoute(elem, concreteComp.get)
      route match {
        case None => ""
        case Some(eroute) =>  "{ title: \""+ menuTitle + "\", routerLink: \""+ eroute +"\" }"
      }
    }
    else
      ""
  }
}
