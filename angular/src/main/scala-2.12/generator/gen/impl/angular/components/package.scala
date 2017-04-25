package generator.gen.impl.angular

import generator.gen.GenFile
import generator.gen.impl.angular.components.component.{GenComponentClass, GenComponentFiles}
import generator.model.Route
import generator.model.setting.component.ViewOpts

/**
  * Created by Sascha on 16.03.2017.
  */
package object components {
  def extract(elem:GenFile)(conc:(GenComponentClass) => String): String ={
    if(elem.isInstanceOf[GenComponentClass])
      conc(elem.asInstanceOf[GenComponentClass])
    else
      ""
  }

  def extractRoute(elem:(Option[String],Option[GenComponentClass])) = {
    if(elem._1.isDefined && elem._2.isDefined){
      elem._1.get
    }
    else if(elem._1.isDefined && elem._2.isEmpty)
      ""
    else if(elem._2.isDefined)
      elem._2.get.compNameWeb
    else ""
  }

  def extractRoute(el:GenComponentFiles, findRouteFor: GenComponentClass):Option[String] = {
    el.genComps.find(p => p._2.getOrElse(Unit) == findRouteFor).lastOption match {
      case None => None
      case Some(r) => Some(r._1.getOrElse(Route(ViewOpts.NOP).route(r._2.get.compNameWeb)))
    }
  }
}
