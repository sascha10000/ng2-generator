package generator.gen.template

import java.util.regex.Pattern

import basic.{FileOps, Ops}
import generator.gen.template.manipulators.Replacer

/**
  * Created by Sascha on 13.02.2017.
  */
class BasicTemplate(override val rawTemplate:String ,override val replacer:Option[Map[String, List[Replacer]]]) extends Template {
  override val start: String = "${"
  override val end: String = "}"
}

object BasicTemplate {
  def apply(tmpl:String) = new BasicTemplate(tmpl, None)

  def fromFile(filename:String): BasicTemplate = {
    val res = FileOps.readFile(filename)

    Ops.printErr {
      res
    }

    res match {
      case Left(s) => BasicTemplate("")
      case Right(s) => BasicTemplate(s)
    }
  }
}
