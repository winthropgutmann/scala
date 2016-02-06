import scala.io._
import java.io._
import java.nio._
import scala.util.matching.Regex

object ulc {
  def main(branch:String, custom:String, username:String): Unit = {
    
    var myClass = new Setting(branch, custom, username)
    println(myClass.getVersion)
    
  } //EO MAIN
  
  class Setting(var Branch: String, Custom:String, Username:String){
    this.setVersion(Branch)
    
    protected var version = ""
    
    def setVersion(branch:String){
      val pattern = new Regex("[0-9]");
      this.version = (pattern findAllIn branch).mkString
    }
    
    def getVersion():String={ return this.version}
    
  }

}//end of object