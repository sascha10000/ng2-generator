package generator.gen.template.manipulators

import generator.model.ModelClass

/**
  * Created by Sascha on 14.03.2017.
  */
abstract class Replacer(val placeholder:String, clazz:Option[ModelClass]) {
  def content:String
}
