package generator.gen.template.manipulators

/**
  * Created by Sascha on 14.03.2017.
  */
class StringReplacer(placeholder:String, _content:String) extends Replacer(placeholder, None) {
  override def content: String = this._content
}

object StringReplacer {
  def apply(placeholder: String, _content: String): StringReplacer = new StringReplacer(placeholder, _content)
}
