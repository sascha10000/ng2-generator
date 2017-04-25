package generator.gen.template.manipulators

/**
  * Created by Sascha on 14.03.2017.
  */
class Appender(_content:String) extends Replacer("APPENDIX", None){
  def content:String = _content
}

object Appender {
  def apply(_content: String): Appender = new Appender(_content)
}
