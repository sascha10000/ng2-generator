package generator.gen.typemappings

/**
  * Created by Sascha on 12.02.2017.
  */
object Typescript {
  implicit def c(clazz:Class[_]):String = {
    val con = clazz.getTypeName.substring(clazz.getTypeName.lastIndexOf(".") + 1, clazz.getTypeName.length)
    val conv = con.toLowerCase
    val simple = map.getOrElse(conv.toLowerCase, "")

    if(simple == "") {
      if(mapFunc.contains(conv.toLowerCase)){
        val f = mapFunc(conv.toLowerCase)
        f(clazz)
      }
      else {
        con
      }
    }
    else
      simple
  }

  val map = Map[String, String](
    "string" -> "string",
    "float" -> "number",
    "double" -> "number",
    "int" -> "number",
    "boolean" -> "boolean",
    "any" -> "any"
  )

  val mapFunc = Map[String, (Class[_]) => String] (
    "array" -> toArray,
    "list" -> toArray
  )

  private def toArray(clazz:Class[_]):String = {
    val types = clazz.getTypeParameters.map(f => {
      f.getTypeName.substring(f.getTypeName.lastIndexOf(".") + 1, f.getTypeName.length)
    })
    println(clazz.getTypeParameters)
    types.foreach(println)
    types(0) + "[]"
  }
}
