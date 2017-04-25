package generator.gen.impl.angular.components.component.create

import generator.gen.impl.angular.Ng2Vars
import generator.gen.impl.angular.components.component.GenComponentHtml
import generator.gen.template.BasicTemplate
import generator.gen.template.manipulators.StringReplacer
import generator.model.ModelClass

/**
  * Created by Sascha on 15.03.2017.
  */
class GenNewCompHtml(clazz:ModelClass) extends GenComponentHtml(clazz) {
  val jTemplate = BasicTemplate.fromFile( Ng2Vars.templateBasePath + "/create/ComponentHtml.tmpl")

  def path() = Some(compNameWeb + "-new")
  def filename() = compNameWeb + "-new.component" + Ng2Vars.html
  def content() = (template() << StringReplacer("Body", jTemplate.template)).template
}
