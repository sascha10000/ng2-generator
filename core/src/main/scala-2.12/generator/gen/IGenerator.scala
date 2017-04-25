package generator.gen

import generator.model.Model

/**
  * Created by Sascha on 11.02.2017.
  */
trait IGenerator {
  val targetPath:String
  val projectName:String
  val model:Model

  def generate() = {}
}
