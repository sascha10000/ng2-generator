package generator.gen.impl.angular.components.component.list

import generator.gen.impl.angular.Ng2Vars
import generator.gen.impl.angular.components.component.GenComponentCss
import generator.gen.template.BasicTemplate
import generator.gen.template.manipulators.StringReplacer
import generator.model.ModelClass

/**
  * Created by Sascha on 15.03.2017.
  */
class GenListCompCss(clazz:ModelClass) extends GenComponentCss(clazz) {
  val jTemplate = BasicTemplate.fromFile( Ng2Vars.templateBasePath + "/list/ComponentCss.tmpl") << StringReplacer("Css", "/* Nothing here */")

  def path() = Some(compNameWeb + "-list")
  def filename() = compNameWeb + "-list.component" + Ng2Vars.css
  def content() = (template() << StringReplacer("Body", jTemplate.template)).template
}
