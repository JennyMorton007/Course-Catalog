package cc

import scala.collection.mutable.Set

class Course (var code:String, var name:String, var prereq:Set[Course], var des:String) extends Complete{
    var grade = ""
    var prq = ""
    def getLong():String = {
        var ret = getShort()
        ret+= "\nDescription: "+des+"\nPreRequisites: "
        prereq.foreach((x)=>{ret+= x.code + (if(x.done){" (Complete)"}else{" (Incomplete)"})+"\t"})
        ret+"\n"
    }
    def getShort():String = {
        code+"  \t"+name+"\n"
    }
    def makePrereq(all:Group):Unit = {
        var arr = prq.split(",")
        arr.foreach(x=> if(x!="none"){prereq+=all.findCourse(x)})
    }
    override def toString(): String = {name}
    override def clone():Course = {
        val clon = new Course(code,name,prereq,des)
        clon.grade=grade;clon.prq=prq;clon.done=done;clon.ready=ready
        return clon
    }
    def >(other:Course):Boolean = {
        if(code.substring(0,4)==other.code.substring(0,4)){code.substring(5,9).toInt>other.code.substring(5,9).toInt}
        else {code.substring(0,4)>other.code.substring(0,4)}
    }    //still want to fix this
    def isReady():Boolean = {
        if(done==true){return false}
        else{
            prereq.foreach(x=> if(!x.done){return false})
        }
        return true
    }
    def getHour():Int = {code.substring(6,7).toInt}
}
object Course {
    def apply():Course = {new Course("","",Set[Course](),"")}
}