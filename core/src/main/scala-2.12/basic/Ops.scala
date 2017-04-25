package basic

import java.io.{BufferedReader, InputStreamReader}

/**
  * Created by Sascha on 08.03.2017.
  */
object Ops {
  def print(out:Either[String, String]) = {
    out match {
      case Right(s) => println("[Success] "+s)
      case Left(s) => println("[Error] "+s)
    }
  }

  def printErr(out:Either[String, String]) = {
    out match {
      case Left(s) => println("[Error] "+s)
      case _ => Unit
    }
  }

  def print(in:BufferedReader) = {
    val lines = in.lines()
    lines.forEach((t: String) => println(t))
  }
}
