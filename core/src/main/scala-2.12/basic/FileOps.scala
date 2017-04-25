package basic

import java.io._
import java.nio.file.{Files, Path, Paths}

import org.apache.commons.io.FileUtils

/**
  * Created by Sascha on 11.02.2017.
  */
object FileOps {
  def copyDir(srcPath:String, targetPath:String):Either[String, String] = {
    val src = new File(srcPath)
    val target = new File(targetPath)

    if(src.isDirectory && target.isDirectory){
      FileUtils.copyDirectory(src, target)

      Right("Folder copied")
    }
    else if(!src.isDirectory && !target.isDirectory)
      Left("Neither " + srcPath + " nor " + target +" are directories.")
    else
      Left("Either "+ srcPath + " or " + target +" is not a directory.")
  }

  def createDir(name:String, path:String, overwrite:Boolean = false):Either[String, String] = {
    new File(path) match {
      case f:File if(f.isDirectory) => {
        val nf = new File(path + "/" + name)
        if(!nf.exists()){
          nf.mkdir()
          Right("Folder "+nf.getAbsolutePath + " created.")
        }
        else if(nf.exists() && overwrite) {
          FileUtils.deleteDirectory(nf)
          nf.mkdir()
          Right("Folder " + nf.getAbsolutePath + " created.")

        }
        else {
          Left("Folder "+nf.getAbsolutePath + " not created it already exists.")
        }
      }
      case f:File if(!f.isDirectory) =>
        Left(f.getAbsolutePath + " is not a directory.")
    }
  }

  def createDirs(absolutePath:String):Either[String, String] = {
    val dirCreated = new File(absolutePath).mkdirs()
    if(dirCreated) {
      Right("Directory" + absolutePath + " created")
    }
    else {
      Left("Directory" + absolutePath + " not created")
    }
  }

  def createFile(file:String, path:String): Either[String,String] = {
    val folder = new File(path)
    if(folder.exists()){
      new File(path + "/" + file).createNewFile()
      Right("File " + file + " created at "+ path +".")
    }
    else Left("Folder "+ path + " doesn't exist.")
  }


  def createFile(absoluteName:String): Either[String, String] = {
    new File(absoluteName).createNewFile()
    Right("Created File " + absoluteName + ".")
  }

  def createFileWithContent(absoluteName:String, content:String): Either[String, String] = {
    new File(absoluteName).createNewFile()

    val writer = new BufferedWriter(new PrintWriter(new File(absoluteName)))
    writer.write(content)
    writer.close()

    Right("Created File " + absoluteName + ".")
  }

  // :: Copies specific files in a folder of the jar to the local file-system
  // basePath: Base-Path of the project
  // foldername: Path/Name of the target folder
  // filespath: Path of the folder where the files that should be copied is placed
  def copyFileListFromJar(basePath:String, foldername:String, filespath:String ,files:List[String]):(Either[String,String], List[List[Either[String,String]]]) = {
    val retCreateDir = FileOps.createDir(foldername ,basePath)

    val retCreatedFiles = files.map(f => {
      val retReadFile = FileOps.readFile(filespath + f)

      val content = retReadFile match {
        case Right(s) => s
        case s:Either[String, String] =>
          Ops.printErr(s)
          ""
      }

      val retCreateFile = FileOps.createFileWithContent(basePath + "/"+ foldername + "/" + f, content)
      List(retReadFile, retCreateFile)
    })

    (retCreateDir, retCreatedFiles)
  }

  def readFile(name: String): Either[String, String] = {
    val in = Thread.currentThread().getContextClassLoader.getResourceAsStream(name)
    if(in == null){
      Left("File "+ name + " not found.")
    }
    else {
      val data = new BufferedReader(new InputStreamReader(in)).lines().toArray
      Right(data.foldLeft("")((prev, el) => if(prev != "") prev + "\n" + el else el.toString ))
    }
  }

  def deleteFile(absoluteName:String): Either[String, String] = {
    try {
      FileUtils.forceDelete(new File(absoluteName))
      Right("File deleted " + absoluteName + ".")
    }
    catch {
      case i:IOException => Left(i.getMessage)
      case e:Exception => Left(e.getMessage)
    }
  }
}
