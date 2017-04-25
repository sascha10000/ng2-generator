package generator.gen.impl.angular.components.component.show

import generator.gen.impl.angular.Ng2Vars
import generator.gen.impl.angular.components.component.GenComponentCss
import generator.gen.template.BasicTemplate
import generator.gen.template.manipulators.StringReplacer
import generator.model.ModelClass

/**
  * Created by Sascha on 15.03.2017.
  */
class GenShowCompCss(clazz:ModelClass) extends GenComponentCss(clazz) {
  val jTemplate = BasicTemplate.fromFile( Ng2Vars.templateBasePath + "/show/ComponentCss.tmpl") << StringReplacer("Css", "/* Nothing here */")

  def path() = Some(compNameWeb + "-show")
  def filename() = compNameWeb + "-show.component" + Ng2Vars.css
  def content() = (template() << StringReplacer("Body", jTemplate.template)).template
}
