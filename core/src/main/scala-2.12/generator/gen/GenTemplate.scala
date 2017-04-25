package generator.gen

import generator.gen.template.BasicTemplate
import generator.gen.template.manipulators.Replacer
import generator.model.ModelClass
import generator.model.setting.SettingType

/**
  * Created by Sascha on 14.03.2017.
  */
abstract class GenTemplate extends GenFile { parent =>
  val clazz:ModelClass
  val templatePath:String
  val target:String // defines whether the child is of a certain type (Angular2: GenClass, GenComponentClass,....). Needed for applying the settings

  val basicTemplate:BasicTemplate

  val template:() => BasicTemplate = () => {
    val bt = basicTemplate

    // applies a given list of replacers to the BasicTemplate if the target matches
    clazz.genSettings.foldLeft(bt)((prev, el) => {
      if (el.typee == SettingType.Replace && el.target == target)
        prev << el.setting.asInstanceOf[Replacer]
      else
        prev
    })

  }
}
