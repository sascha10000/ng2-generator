package generator.gen.impl.angular.codegen

import generator.gen.template.manipulators.Replacer
import generator.model.ModelClass

/**
  * Created by Sascha on 14.03.2017.
  */
class Constructor(clazz:ModelClass) extends Replacer("ConstructorSignature", Some(clazz)) {
  import generator.gen.typemappings.Typescript._

  override def content: String = {
    clazz.attributes.foldLeft("")((prev, el) => {
      if(prev != "")
        prev + ",\n\t\tpublic " + el.name + ":" + c(el.typee)
      else
        "\n\t\tpublic " + el.name + ":" + c(el.typee)
    })
  }
}

object Constructor {
  def apply(clazz: ModelClass): Constructor = new Constructor(clazz)
}
