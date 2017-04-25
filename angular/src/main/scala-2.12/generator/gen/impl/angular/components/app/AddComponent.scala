package generator.gen.impl.angular.components.app

import basic.{FileOps, Ops}

/**
  * Created by Sascha on 25.04.2017.
  */
class AddComponent(projectBasePath:String, targetFolderName:String, srcFolderPath:String, srcFilenames:List[String]) {
  add

  // creates a folder <TARGET_FOLDER_NAME> in the projects <BASE_PATH> and copies the given files to it (no usage of template)
  def add = {
    val ret = FileOps.copyFileListFromJar(projectBasePath, targetFolderName, srcFolderPath, srcFilenames)
    Ops.printErr( ret._1 )
    ret._2.foreach(_.foreach(Ops.printErr))
  }
}
