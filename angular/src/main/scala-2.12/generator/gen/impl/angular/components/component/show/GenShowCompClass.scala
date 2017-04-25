package generator.gen.impl.angular.components.component.show

import generator.gen.impl.angular.Ng2Vars
import generator.gen.impl.angular.components.component.GenComponentClass
import generator.gen.template.BasicTemplate
import generator.gen.template.manipulators.StringReplacer
import generator.model.ModelClass

/**
  * Created by Sascha on 15.03.2017.
  */
class GenShowCompClass(clazz:ModelClass) extends GenComponentClass(clazz, clazz.name + "Show") {
  val jTemplate = BasicTemplate.fromFile(Ng2Vars.templateBasePath + "/show/Component.tmpl") << StringReplacer("Methods", "// TODO: Some Methods")

  def path() = Some(compNameWeb)
  def filename() = compNameWeb + ".component" + Ng2Vars.ts
  def content() = (template() << StringReplacer("Body", jTemplate.template)).template
}
