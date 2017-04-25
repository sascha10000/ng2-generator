package generator.gen.impl.angular.components.app

import basic.{FileOps, Ops}
import generator.gen.GenFile
import generator.gen.impl.angular.Ng2Vars
import generator.gen.template.BasicTemplate

/**
  * Created by Sascha on 15.03.2017.
  */
class GenStyleCss(ngRoot:String) extends GenFile {
  val basePath = ngRoot + "/" + Ng2Vars.appPath
  val templatePath = "generator/gen/impl/angular/components/app/AppStyleCss.tmpl"
  val template = BasicTemplate.fromFile(templatePath)

  Ops.print{ FileOps.deleteFile(basePath + "/"+ filename()) }
  Ops.print { FileOps.createFileWithContent(basePath + "/" + filename, content) }

  override def path(): Option[String] = None
  override def filename(): String = "styles" + Ng2Vars.css
  override def content(): String = template.template
}
