package cc

import scala.collection.mutable.Map
import scala.io.Source
import scalafx.scene.control.Labeled
import scalafx.scene.control.Button
import javafx.event.ActionEvent
import scala.collection.mutable.Buffer
import scala.collection.mutable.Set


class CourseCatalog {
  var line = ""
  var course: Course = Course()
  var grad = Buffer[Requirement]()
  var reqs = Map[String,Requirement]()
  var qwerty = 0

  var ap = new Group("AP Credit")
  var fl20 = new Group("Fall 2020")
  var sp21 = new Group("Spring 2021")
  var fl21 = new Group("Fall 2021")
  var all = new Group("All Courses")
  var courses = Map[String, Course]()
  var current = sp21
  var next = fl21
  var txtFiles = Map[String, Group](
    "ap" -> ap,
    "fl20" -> fl20,
    "sp21" -> sp21,
    "fl21" -> fl21,
    "other" -> all
  )
  txtFiles.foreach(x =>{
    for (a <- Source.fromFile(x._1 + ".txt").getLines) {
      line = a
      if (line == " ") {
        x._2.add(course.clone());
        course = Course()
      } 
      else if (course.code == "") { course.code = line }
      else if (course.name == "") { course.name = line }
      else if (course.des == "") { course.des = line }
      else if (course.prq == "") { course.prq = line }
      else { course.grade = line }
    }
    x._2.classes.foreach(a=> courses+=(a.code->a))
  }
  )
  
  all ++= ap
  all ++= fl20
  all ++= sp21
  all ++= fl21
  courses.foreach(x => x._2.makePrereq(all))
  ap.classes.foreach(_.done = true)
  fl20.classes.foreach(_.done = true)
  sp21.classes.foreach(_.done = true)
  all.classes.foreach(x => if (x.isReady) { x.ready = true })
  Array("PHED 1153","CSCI 2322","CLAS 1305","HCOM 1305","CSCI 2320","CSCI 2094","PLSI 1361").foreach(x => fl21.add(all.findCourse(x)))
  txtFiles.foreach(x => x._2.sort())

    for(a <- Source.fromFile("gradreqs.txt").getLines()){
      line = a
      if(line==""){grad+=new Requirement(qwerty.toString())}
      else if(line==" "){qwerty+=1}
      else if(grad(qwerty).name==qwerty.toString()){grad(qwerty).name=line}
      else {
        if(line.contains("/")){
          var temp = Set[Course]()
          line.split("/").foreach(x=> temp+=courses(x).clone())
          grad(qwerty).or+=temp.clone()
        }
        else {
          line.split(",").foreach(x=> grad(qwerty).and+=courses(x).clone())
        }
      }
    }
    grad.foreach(x=> reqs+=(x.name->x))

  def getGradShort():Array[Button] = {
    var ret = new Array[Button](grad.length)
    for(i<- 0 until ret.length){
      ret(i)=new Button(grad(i).name)
    }
    ret
  }
  def getReqCompl():String = {
    "("+grad.count(_.done)+"/"+grad.length+" done)"
  }
  def getGrades(): String = {
    var ret = ""
    all.classes.foreach((x) => if(x.grade!=""){ret += (x.code + "\t" + x.grade + "\n")})
    ret + "\n"
  }
  def getGPA(): String = {
    var ret = 0.0
    var sum = 0
    all.classes.foreach((x) => if(x.grade!=""){
      ret += (x.getHour * getGradePoint(x.grade))}
    )
    all.classes.foreach((x) => if(x.grade!=""){sum += x.getHour()})
    "GPA: " + "%.3f".format(ret / sum) + "\n"
  }
  def getGradePoint(grade: String): Double = {
    return grade match {
      case "A"  => 4.0
      case "A-" => 3.667
      case "B+" => 3.333
      case "B"  => 3.0
      case "B-" => 2.667
      case "C+" => 2.333
      case "C"  => 2.0
      case "C-" => 1.667
      case "D+" => 1.333
      case "D"  => 1.0
      case _    => 0.0
    }
  }
  def getCredit():String = {
    var credit = ""
    all.classes.foreach(x=> if(x.done){credit+=x.getShort()})
    credit
  }
  def getHours():String = {
    var ret = 0
    all.classes.foreach(x=> if(x.done){ret+=x.getHour()})
    ret.toString()
  }
  def getCurrent():Array[Button] = {
    var ret = new Array[Button](current.classes.length)
    for(i <- 0 until ret.length){
      ret(i)= new Button(current.classes(i).getShort())
    }
    ret
  }
  def getNext():Array[Button] = {
    var ret = new Array[Button](next.classes.length)
    for(i <- 0 until ret.length){
      ret(i)= new Button(next.classes(i).getShort())
    }
    ret
  }
  def getAll():Array[Button] = {
    var ret = new Array[Button](all.classes.length)
    for(i <- 0 until ret.length){
      ret(i)= new Button(all.classes(i).getShort())
    }
    ret
  }
  def getReady():Buffer[Button] = {
    var ret = Buffer[Button]()
    for(i <- 0 until all.classes.length){
      if(all.classes(i).ready){
        ret+= new Button(all.classes(i).getShort())
      }
    }
    ret
  }
}
