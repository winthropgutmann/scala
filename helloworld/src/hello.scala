import scala.io._
import java.io._
import java.nio._
import scala.util.matching.Regex

object tst {
  
  def main(args: Array[String]): Unit = {
    //variables
      var custom = args(1)
      var user = args(2)
      val version = 0
      var dbname = if(args(1).replace(".", "").replace("-", "") =="") args(0).replace(".", "").replace("-", "") else args(1).replace(".", "").replace("-", "")
      val writer = new PrintWriter(new File("C:\\Users\\" + user + "\\dev\\qdw-deployment\\Build\\LocalSettings.rb"))
      println(dbname)
      val sourceFile = "C:\\Users\\"+user+"\\dev\\qdw-deployment\\Build\\LocalSettings2.rb"

      if(dbname.toLowerCase == "master"){val version = getVersionCheck(sourceFile); println(version)}
      
      for(line <- Source.fromFile(sourceFile).getLines()){
        if(!checkForComment(line)){
          checkForProvisioningClient()
        }
      }
      
      
      
      
      
      //functions
      def checkForComment(line:String):Boolean={if(line.charAt(0).equals('#')){ writeLine(line); return true}else{return false}}
      
      def writeLine(line:String){writer.write(line + "\n")}
  
      def getVersionCheck(file:String):Int={
        println("incheck")
        var myVersion = Array[String]()
        for (line <- Source.fromFile(file).getLines()){
          if (line.contains("#master_version:")){
            val pattern = new Regex("[0-9]");
            return (pattern findAllIn line).mkString.toInt
          }
        }
        return 0
      }
      
      
      def checkForProvisioningClient(){
        
      }
      
  } //end of main
} //end object