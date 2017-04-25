package generator.model.setting

import generator.model.setting.SettingType.SettingType

/**
  * Created by Sascha on 12.02.2017.
  */
case class GenSetting[T](typee:SettingType, _target:Option[String],setting:T) {
  def target:String = _target.getOrElse("")
}

object GenSetting {
  def apply[T](typee: SettingType, _target: String, setting: T): GenSetting[T] = new GenSetting(typee, Some(_target), setting)
}

object SettingType extends Enumeration {
  type SettingType = Value
  val Replace, Route, ViewComp, Nothing = Value
}
