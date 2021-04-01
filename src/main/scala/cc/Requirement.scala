package cc

import scala.collection.mutable.Set

class Requirement(var name: String) extends Complete {
  var and = Set[Course]()
  var or = Set[Set[Course]]()

  override def toString(): String = {
    var temp = and.count(_.done)
    temp+= or.count(isDone(_))
    var ret = name + {
      if (done) { "\tDone" }
      else { "\tNot Done" }
    } + "\t("+temp+"/"+(and.size+or.size)+")\n"
    and.foreach(x => {
      ret += "\n"+x.getShort + {
        if (x.done) { "\tDone" }
        else { "\tNot Done" }
      } + ","
    })
    or.foreach(s => {
      ret += "\n"
      s.foreach(x => ret += x.code + "/ ")
      ret += {
        if (isDone(s)) { "\tDone" }
        else { "\tNot Done" }
      }
    })
    ret
  }
  def isDone(s: Set[Course]): Boolean = {
    or.foreach(x => {
      var temp = false
      x.foreach(y => if (y.done) { temp = true })
      if (!temp) { return false }
    })
    return true
  }
  def checkReady(): Unit = {
    var ret = true
    and.foreach(x => if (!x.done) { ret = false })
    if (ret) {
      or.foreach(x => {
        var temp = false
        x.foreach(y => if (y.done) { temp = true })
        if (!temp) { ret = false }
      })
    }
    if (ret) { done = true }
  }
}
