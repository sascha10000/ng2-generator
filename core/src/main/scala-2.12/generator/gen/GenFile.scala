package generator.gen

/**
  * Created by Sascha on 12.02.2017.
  */
trait GenFile {
  def path():Option[String]
  def filename():String
  def content():String

  def getFile:(Option[String], String, String) = {
    (path, filename, content)
  }
}
