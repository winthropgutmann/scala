import scala.io._
import java.io._
import java.nio._
import scala.util.matching.Regex

object ulc {
  def main(branch:String, custom:String, username:String): Unit = {
    
  } //EO MAIN
  
  class Setting(var Branch: String, Custom:String, Username:String){
    this.setVersion(version)
    
    protected var version = ""
    
    def setVersion(branch:String){
      val pattern = new Regex("[0-9]");
      this.Branch = (pattern findAllIn branch).mkString
    }
    
  }

}//end of object