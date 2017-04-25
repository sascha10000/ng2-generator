package generator.model

import java.lang.reflect.Field

/**
  * Created by Sascha on 11.02.2017.
  */
class ModelAttribute(val name:String,val typee:Class[_]) {

}

object ModelAttribute{
  def apply(name: String, typee: Class[_]): ModelAttribute = new ModelAttribute(name, typee)

  def apply(field:Field): ModelAttribute = new ModelAttribute(field.getName, field.getType)
}
