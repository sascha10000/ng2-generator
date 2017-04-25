package example

import example.model.{Item, Person}
import generator.gen.Generator
import generator.gen.impl.angular.AngularGen
import generator.gen.impl.angular.components.Targets
import generator.gen.template.manipulators.{Appender, Prepender}
import generator.model.setting.component.{GenView, ViewOpts}
import generator.model.setting.{GenSetting, SettingType}
import generator.model.{Model, ModelClass, Route}

/**
  * Created by Sascha on 11.02.2017.
  */
object Generator {
  def main(args: Array[String]): Unit = {
    import ViewOpts._
    val model = Model(
      ModelClass(classOf[Person],
        GenSetting(SettingType.Replace, Targets.GenClass, Prepender("/* START */")),
        GenSetting(SettingType.Replace, Targets.GenClass, Appender("/* END */")),
        GenView(S + L + N , Route(S, "/show"), Route(L, "person-list", appendix = false))
      ),
      ModelClass(classOf[Item],
        GenSetting(SettingType.Nothing,"", "b"),
        GenSetting(SettingType.Replace, Targets.GenComponentHtml, Appender("<!-- END -->")),
        GenSetting(SettingType.Replace, Targets.GenComponentHtml, Prepender("<!-- START -->")),
        GenSetting(SettingType.Replace, Targets.GenComponentHtml, Prepender("<!-- START2 -->")),
        GenView(S + L + N) // , Route(S, "/show"), Route(L) : TODO: This call should yield item-new, item-show, item-list as routes.
      )
    )

    val generator = new Generator("C:/tmp", "ng2gentest", model) with AngularGen
    generator.generate
  }
}
