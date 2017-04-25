package generator.gen.template.manipulators

/**
  * Created by Sascha on 14.03.2017.
  */
class Prepender(_content:String) extends Replacer("PREPENDIX", None){
  def content:String = _content
}

object Prepender {
  def apply(_content: String): Prepender = new Prepender(_content)
}
