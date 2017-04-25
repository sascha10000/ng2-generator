package generator.gen

import generator.model.{Model, ModelClass}

/**
  * Created by Sascha on 11.02.2017.
  */
class Generator(override val targetPath:String, override val projectName:String,  override val model:Model) extends IGenerator {
}
