package generator.gen.impl.angular.components.component

import basic.WebOps
import generator.gen.{GenFile, GenTemplate}
import generator.gen.impl.angular.Ng2Vars
import generator.gen.impl.angular.components.Targets
import generator.gen.template.BasicTemplate
import generator.gen.template.manipulators.StringReplacer
import generator.model.ModelClass

/**
  * Created by Sascha on 12.02.2017.
  */
abstract class GenComponentHtml(override val clazz:ModelClass) extends GenTemplate {
  val templatePath = Ng2Vars.templateBasePath + "/ComponentHtml.tmpl"
  val compName: String = clazz.name
  val compNameWeb: String = WebOps.replaceUpperCases(clazz.name, "-")

  override val basicTemplate: BasicTemplate = BasicTemplate.fromFile(templatePath)
  override val target: String = Targets.GenComponentHtml
}
