package generator.model

import generator.model.setting.GenSetting

/**
  * Created by Sascha on 11.02.2017.
  */
class ModelClass(val name:String, val attributes:Array[ModelAttribute], val genSettings:List[GenSetting[_]] = List()) {
}

object ModelClass {
  def apply(name:String, attributes:Array[ModelAttribute], genSettings:List[GenSetting[_]]) = new ModelClass(name, attributes, genSettings)

  def apply(name:String, attributes:Array[ModelAttribute]) = new ModelClass(name, attributes)

  def apply(clazz: Class[_]): ModelClass = {
    val attributes = clazz.getDeclaredFields.map(f => ModelAttribute(f))

    new ModelClass(clazz.getName.substring(clazz.getName.lastIndexOf('.')+1, clazz.getName.length), attributes)
  }

  def apply(clazz: Class[_],  genSettings:GenSetting[_]*): ModelClass = {
    val attributes = clazz.getDeclaredFields.map(f => ModelAttribute(f))

    new ModelClass(clazz.getName.substring(clazz.getName.lastIndexOf('.')+1, clazz.getName.length), attributes, genSettings.toList)
  }
}
