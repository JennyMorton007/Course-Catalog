
package cc

import scala.collection.mutable.Buffer

class Group(val name:String){
    var classes = Buffer[Course]()
    def add(x:Course):Unit = {classes+=x}
    def ++=(other:Group):Unit = {classes++=other.classes}
    def remove(x:Course):Unit = {classes-=x}

    def sort():Unit = {
        var tmp = Course();
        for(i <- classes.length until 1 by -1){
            for(j <- 1 until i){
                if(classes(j-1)>classes(j)){
                    tmp = classes(j-1)
                    classes(j-1)=classes(j)
                    classes(j)=tmp
                }
            }
        }
    }
    def getHours():Int = {
        var sum = 0;
        classes.foreach((x)=>{sum+=x.getHour()})
        sum
    }
    def getLong():String = {
        var ret = name+"\n";
        classes.foreach((x)=>{ret+=x.getLong()+"\n"})
        ret
    }
    def getShort():String = {
        var ret = name+"\n";
        classes.foreach((x)=>{ret+=x.getShort()+"\n"})
        ret
    }
    def findCourse(cod:String):Course = {
        classes.foreach((x)=>{if(cod==x.code){return x}})
        return Course()
    }
    override def toString(): String = {name}

}