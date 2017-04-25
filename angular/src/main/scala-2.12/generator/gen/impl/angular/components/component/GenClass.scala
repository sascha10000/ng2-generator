package generator.gen.impl.angular.components.component

import com.sun.prism.impl.Disposer.Target
import generator.gen.GenTemplate
import generator.gen.impl.angular.Ng2Vars
import generator.gen.impl.angular.codegen.Constructor
import generator.gen.impl.angular.components.Targets
import generator.gen.template.BasicTemplate
import generator.gen.template.manipulators.StringReplacer
import generator.model.ModelClass

/**
  * Created by Sascha on 12.02.2017.
  */
class GenClass(override val clazz:ModelClass) extends GenTemplate {
  override val target = Targets.GenClass
  override val templatePath: String = "generator/gen/impl/angular/components/component/ConceptClass.tmpl"
  override val basicTemplate: BasicTemplate = BasicTemplate.fromFile(templatePath) << StringReplacer("ClassName", clazz.name) << Constructor(clazz)

  def path = None
  def filename():String = clazz.name + Ng2Vars.ts
  def content() = template().template
}
