package generator.model

/**
  * Created by Sascha on 11.02.2017.
  */
class Model(val classes:List[ModelClass]) {

}

object Model {
  def apply(classes: ModelClass*): Model = new Model(classes.toList)

  def apply(classes: List[ModelClass]): Model = new Model(classes)

  def apply(classes: List[Class[_]], nothing:Unit = null): Model = Model(classes.map(f => ModelClass(f)))
}
