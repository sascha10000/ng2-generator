package generator.gen.template

import java.util.regex.Pattern

import generator.gen.template.manipulators.{Replacer, StringReplacer}

/**
  * Created by Sascha on 13.02.2017.
  */
trait Template {
  val start:String
  val end:String
  lazy val regexStart = Pattern.quote(start)
  lazy val regexEnd = Pattern.quote(end)
  val rawTemplate:String
  val replacer:Option[Map[String, List[Replacer]]] // for cascading the code in the end

  def replace(placeholder:String, str:String):BasicTemplate = {
    //val newTemplate = rawTemplate.replaceAll(regexStart+"( )*"+Pattern.quote(placeholder)+"( )*"+regexEnd, str)
    this << StringReplacer(placeholder, str)
  }

  def applyReplace(placeholder:String, str:String):BasicTemplate = {
    BasicTemplate(rawTemplate.replaceAll(regexStart+"( )*"+Pattern.quote(placeholder)+"( )*"+regexEnd, str))
  }

  def <<(that:Replacer):BasicTemplate = {
    replacer match {
      case None => new BasicTemplate(rawTemplate, Some(Map(that.placeholder -> List(that))))
      case Some(map) => {
        if(map.contains(that.placeholder)){
          val newList = map(that.placeholder):+that
          val newMap = map + (that.placeholder -> newList)
          new BasicTemplate(rawTemplate, Some(newMap))
        }
        else {
          new BasicTemplate(rawTemplate, Some(map + (that.placeholder -> List(that))))
        }
      }
      case _ => new BasicTemplate(rawTemplate, None)
    }
  }

  def template:String = {
    applyReplacerPipeline.removePlaceholders
  }

  private def applyReplacerPipeline:BasicTemplate = {
    // generates the content and condenses it to a simple list with placeholders and the code to replace
    replacer match {
      case Some(map) => {
        // concatenate content for a single placeholder
        val replaceList = map.toList.map(el => {
          val content =  el._2.foldLeft("")((prev, iel) => {
            if(prev == "")
              iel.content
            else
              prev + "\n" + iel.content
          })

          (el._1, content)
        })

        // apply all replacers
        def applyPipe(repl: List[(String, String)], tmpl:BasicTemplate, i:Int):BasicTemplate = {
          if(i < repl.length){
            val newBasicTmpl = tmpl.applyReplace(repl(i)._1, repl(i)._2)
            applyPipe(repl, newBasicTmpl, i + 1)
          }
          else {
            tmpl
          }
        }

        applyPipe(replaceList, new BasicTemplate(rawTemplate, replacer), 0)
      }
      case _ => new BasicTemplate(rawTemplate, None)
    }
  }

  def removePlaceholders:String = {
    rawTemplate.replaceAll(regexStart+"( )*[A-Za-z1-2_-]*( )*"+regexEnd, "")
  }
}
