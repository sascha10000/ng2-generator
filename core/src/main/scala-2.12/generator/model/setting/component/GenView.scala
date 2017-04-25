package generator.model.setting.component

import generator.model.Route
import generator.model.setting.{GenSetting, SettingType}

/**
  * Created by Sascha on 15.03.2017.
  */
class GenView(actions:Int, val routes: List[Route] = List[Route]()) extends GenSetting(SettingType.ViewComp, None, ""){
  def NOTHING : Boolean = {
    actions == 0
  }
  def SHOW:Boolean = {
    ViewOpts.is(actions, ViewOpts.S)
  }
  def LIST:Boolean = {
    ViewOpts.is(actions, ViewOpts.L)
  }
  def NEW:Boolean = {
    ViewOpts.is(actions, ViewOpts.N)
  }
  def EDIT:Boolean = {
    ViewOpts.is(actions, ViewOpts.E)
  }
  def DELETE:Boolean = {
    ViewOpts.is(actions, ViewOpts.D)
  }
}

object GenView {
  def apply(actions: Int, routes: Route*): GenView = new GenView(actions, routes.toList)
}

object ViewOpts extends Enumeration{
  type ViewOpts = Value

  val NOTHING, SHOW, LIST, NEW, EDIT, DELETE = Value

  def NOP = 0
  def S = 1
  def L = 2
  def N = 4
  def E = 8
  def D = 16

  // Converts a given Number to its binary representation and evaluates if it's 1 at a given position.
  // The position is determined by log2(basePos) thus one can call it like is(13, ViewOpts.E) which would check if Edit is set.
  // log2(ViewOpts.E) = 3 and 13 = 01101, beginning from left the 3rd position is 1 so Edit is set.
  def is(bytes:Int, basePos:Double):Boolean = {
    val pos =  (Math.log10(basePos) / Math.log10(2.0)).asInstanceOf[Int]
    String.format("%5s",Integer.toBinaryString(bytes)).replace(' ', '0').charAt(4 - pos) == '1'
  }

  /* generate components for:
      new: creating new instance
      delete: deleting an instance
      edit: editing an instance
      show: show a single instance
      list: show a list of all instances
  */
}
