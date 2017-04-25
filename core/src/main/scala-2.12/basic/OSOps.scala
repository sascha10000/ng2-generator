package basic

/**
  * Created by Sascha on 12.02.2017.
  */
object OSOps {
  def isWindows():Boolean = {
    System.getProperty("os.name").toLowerCase().contains("win");
  }
}
