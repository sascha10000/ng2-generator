package generator.gen.impl.angular.components.app

import basic.{FileOps, Ops}
import generator.gen.GenFile
import generator.gen.impl.angular.Ng2Vars
import generator.gen.impl.angular.components.component.{GenComponentClass, GenComponentFiles}
import generator.gen.template.BasicTemplate
import generator.gen.template.manipulators.StringReplacer

/**
  * Created by Sascha on 08.03.2017.
  */
class GenAppComponentHtml(ngRoot:String, val imports:List[GenComponentFiles]) extends GenFile {
  val basePath = ngRoot + "/" + Ng2Vars.appSrcPath
  val templatePath = "generator/gen/impl/angular/components/app/AppComponentHtml.tmpl"
  val template = BasicTemplate.fromFile(templatePath)

  Ops.print{ FileOps.deleteFile(basePath + "/"+ filename()) }
  Ops.print { FileOps.createFileWithContent(basePath + "/" + filename, content) }

  override def path(): Option[String] = None
  override def filename(): String = "app.component.html"
  override def content(): String = template.template
}
