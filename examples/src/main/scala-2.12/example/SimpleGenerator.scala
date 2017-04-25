package example

import example.model.{Item, Person}
import generator.gen.Generator
import generator.gen.impl.angular.AngularGen
import generator.model.setting.component.{GenView, ViewOpts}
import generator.model.{Model, ModelClass, Route}

/**
  * Created by Sascha on 09.04.2017.
  */
object SimpleGenerator {
  def main(args: Array[String]): Unit = {
    import ViewOpts._
    val model = Model(
      ModelClass(classOf[Person], GenView(S + L + N , Route(S, "/show"), Route(L, "person-list", appendix = false))),
      ModelClass(classOf[Item], GenView(S + L)),
      ModelClass(classOf[Item]) /* would generate all routes, but is already defined by the previous line */
    )

    val generator = new Generator("C:/tmp", "ng2gentest", model) with AngularGen
    generator.generate
  }
}
