package generator.model

import generator.model.setting.component.GenView

/**
  * If fullpath = false; The Route is prepend + "compName" + append
  * Else; The Route is append+prepend
  */
class Route(val actions:Int ,val prepend:String = "", val append:String = "", val fullpath:Boolean = false) extends GenView(actions, List[Route]()){
  // TODO: needs to be interpreted by GenModule (Routes) and GenAppCompnentHtml for the Menu.
  //        - there are also Data inputs possible e.g. instance/show/:id

  // return the route, defined by the parameters of the instance. The infix may or may not be used.
  def route(infix:String) = {
    if(fullpath)
      prepend + append
    else prepend + infix + append
  }
}

object Route {
  def apply(actions:Int, route:String, appendix:Boolean = true) = new Route(actions, "", route, !appendix)
  def apply(actions:Int) = new Route(actions, "", "", false) // this will only work once and result in duplicate routes
}
