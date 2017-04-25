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
abstract class GenComponentClass(override val clazz:ModelClass, val compName:String) extends GenTemplate {
  val templatePath = Ng2Vars.templateBasePath + "/Component.tmpl"
  val compNameWeb:String = WebOps.replaceUpperCases(compName, "-")

  override val basicTemplate: BasicTemplate = BasicTemplate.fromFile(templatePath) << StringReplacer("ComponentName", compNameWeb) << StringReplacer("ClassName", compName)
  override val target: String = Targets.GenComponentClass
}
