package cc



//code = PHIL 1300, name = Philosophy: the basics, des = An intro course to the basics of philosophy
class Course (var code:String, var name:String, var prereq:String, var grade:String, var des:String) extends Complete{
    
    def getLong():String = {
        var ret = getShort()
        ret+= "\nDescription: "+des+"\nPreRequisites: "+prereq
        ret
    }
    def getShort():String = {
        code+" \t"+name
    }
    def checkReady(all:Group):Unit = {
        ready=true
        var num = prereq.length()/10
        for(x <- 0 to num){
            if(all.findCourse(prereq.substring(0+10*x,9+10*x)).taken==0) ready=false
        }
        //not sure if this works
    }
    override def toString(): String = {name}
    override def clone():Course = {
        val clon = new Course(code,name,prereq,grade,des)
        clon.taken=taken;clon.ready=ready
        return clon
    }
    def >(other:Course):Boolean = {
        if(code.substring(0,4)==other.code.substring(0,4)){code.substring(5,9).toInt>other.code.substring(5,9).toInt}
        else {code.substring(0,4)>other.code.substring(0,4)}
    }   
    def getHour():Int = {code.substring(6,7).toInt}
}
object Course {
    def apply():Course = {new Course("","","","","")}
}