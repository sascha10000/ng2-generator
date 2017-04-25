package generator.gen.impl.angular

import java.io.{BufferedReader, File, InputStreamReader}

import basic.{OSOps, Ops}
import generator.gen.IGenerator
import generator.gen.impl.angular.components.app._
import generator.gen.impl.angular.components.component.{GenComponentClass, GenComponentFiles}

/**
  * Created by Sascha on 11.02.2017.
  */
trait AngularGen extends IGenerator{
  val ngPath = targetPath + "/" + projectName

  override def generate() = {
    createAngular2Project

    println("Start Generating")
    createComponents
    createServices
  }

  private def createAngular2Project = {
    println("Creating angular project...")
    val process = new ProcessBuilder(Ng2Vars.ngCommand, "new",  projectName).directory(new File(targetPath)).start()

    Ops.print {
      new BufferedReader(new InputStreamReader(process.getInputStream))
    }
  }

  private def createComponents = {
    val components: List[GenComponentFiles] = model.classes.map(new GenComponentFiles(ngPath ,_))
    val compFiles: List[(Option[String], Option[GenComponentClass])] = components.flatMap(p => p.genComps)

    val module: GenModule = new GenModule(ngPath, compFiles) // TODO: Change back to List[GenComponentFiles] and access the Views via genCompShow, genCompEdit etc.
    val appComponentHtml: GenAppComponentHtml =  new GenAppComponentHtml(ngPath, components)
    val appComponent:GenAppComponentClass = new GenAppComponentClass(ngPath, components)
    val appStyleCss: GenStyleCss = new GenStyleCss(ngPath)

    // add components
    val topMenu = new AddComponent(ngPath, "/" + Ng2Vars.appSrcPath + "/top-menu", "generator/gen/impl/angular/components/app/top-menu/", List("top-menu.component.css", "top-menu.component.html", "top-menu.component.ts", "top-menu.service.ts"))
  }

  private def createServices = {
    // TODO: implement
  }
}

object Ng2Vars {
  val ngCommand:String = if(OSOps.isWindows()) "ng.cmd" else "ng"
  val appPath:String = "src"
  val appSrcPath:String = "src/app"
  val ts:String = ".ts"
  val html:String = ".html"
  val css:String = ".css"
  val templateBasePath: String = "generator/gen/impl/angular/components/component"
}
