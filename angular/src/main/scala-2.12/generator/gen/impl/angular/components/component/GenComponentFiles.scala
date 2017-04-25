package generator.gen.impl.angular.components.component

import basic.{FileOps, Ops, WebOps}
import generator.gen.GenFile
import generator.gen.impl.angular.Ng2Vars
import generator.gen.impl.angular.components.component.create.{GenNewCompClass, GenNewCompCss, GenNewCompHtml}
import generator.gen.impl.angular.components.component.list.{GenListCompClass, GenListCompCss, GenListCompHtml}
import generator.gen.impl.angular.components.component.show.{GenShowCompClass, GenShowCompCss, GenShowCompHtml}
import generator.model.setting.SettingType
import generator.model.setting.component.{GenView, ViewOpts}
import generator.model.{ModelClass, Route}

/**
  * Created by Sascha on 12.02.2017.
  */
class GenComponentFiles(ngRoot:String, clazz:ModelClass) {
  val basePath = ngRoot + "/" + Ng2Vars.appSrcPath

  val compName = clazz.name
  val compNameWeb:String = WebOps.replaceUpperCases(clazz.name, "-")
  val path = compNameWeb
  val componentFile = compNameWeb + ".component"

  val genComp:Option[GenView] = {
    val cmp = clazz.genSettings.filter(p => p.typee == SettingType.ViewComp )
    if(cmp.length == 0)
      None
    else
      Some(cmp.last.asInstanceOf[GenView])
  }

  val genCompShow = if((genComp.isDefined && genComp.get.SHOW) || genComp.isEmpty) Some(new GenShowCompClass(clazz)) else None
  val genCompList = if((genComp.isDefined && genComp.get.LIST) || genComp.isEmpty) Some(new GenListCompClass(clazz)) else None
  val genCompNew = if((genComp.isDefined && genComp.get.NEW) || genComp.isEmpty) Some(new GenNewCompClass(clazz)) else None
  val genCompEdit = if((genComp.isDefined && genComp.get.EDIT) || genComp.isEmpty) None else None // TODO: this component is nested in the "new" component

  val genComps = List(
    (r(genComp){ _.SHOW }, genCompShow),
    (r(genComp){ _.LIST }, genCompList),
    //(r(genComp, genCompEdit.get.compNameWeb){ _.EDIT }, genCompEdit),
    (r(genComp){ _.NEW }, genCompNew)
  )

  val genFiles:List[GenFile] = {
    List[GenFile](
      new GenClass(clazz)
    ) ::: {
      if (genCompShow.isDefined)
        List[GenFile](genCompShow.get, new GenShowCompHtml(clazz), new GenShowCompCss(clazz))
      else List[GenFile]()
    } ::: {
      if (genCompList.isDefined)
        List[GenFile](genCompList.get, new GenListCompHtml(clazz), new GenListCompCss(clazz))
      else List[GenFile]()
    } ::: {
      if (genCompEdit.isDefined)
        List[GenFile]()
      else List[GenFile]()
    } ::: {
      if( genCompNew.isDefined)
         List[GenFile](genCompNew.get, new GenNewCompHtml(clazz), new GenNewCompCss(clazz))
      else
        List[GenFile]()
    }
  }

  genFiles.foreach(f => {
    f.getFile match {
      case (None, filename:String, content:String) =>
        Ops.print {
          FileOps.createFileWithContent(basePath + "/" + filename, content)
        }
      case (path:Option[String], filename:String, content:String) =>
        FileOps.createDirs(basePath + "/" + path.get)
        Ops.print {
          FileOps.createFileWithContent(basePath + "/" + path.get + "/" + filename, content)
        }
    }
  })

  // @altRoute: Alternative Route
  def r(v:Option[GenView], altRoute:Option[String] = None)(func:(Route) => Boolean):Option[String] = {
    v match {
      case None => None
      case Some(genView) =>
        val filtered = genView.routes.filter(p => func(p))
        if(filtered.length == 0)
          if(altRoute.isDefined)
            Some(altRoute.get)
          else
            None
        else
          Some(filtered.last.route(this.compNameWeb))
    }
  }
}
