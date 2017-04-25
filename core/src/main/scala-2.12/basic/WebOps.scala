package basic

/**
  * Created by Sascha on 12.02.2017.
  */
object WebOps {
 def replaceUpperCases(value:String, replaceWith:String): String = {
   value.foldLeft("")((prev, el) =>{
     if(el.isUpper && prev != "")
       prev + "-" + el.toLower
     else if (el.isUpper && prev == "")
       prev + el.toLower
     else
       prev + el
   })
 }
}
