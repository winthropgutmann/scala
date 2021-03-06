import scala.io._
import java.io._
import java.nio._
import scala.util.matching.Regex

object tst {
  
  def main(args: Array[String]): Unit = {
    //variables
    var custom = args(1)
    var user = args(2)
    val mpi = args(3)
    val web = args(4)
    val version = 0
    var dbname = if(args(1).replace(".", "").replace("-", "") =="") args(0).replace(".", "").replace("-", "") else args(1).replace(".", "").replace("-", "")
    val writer = new PrintWriter(new File("C:\\Users\\" + user + "\\dev\\qdw-deployment\\Build\\winTstMe.rb"))
    val sourceFile = "C:\\Users\\"+user+"\\dev\\qdw-deployment\\Build\\LocalSettings.rb"
    
    //functions
    def checkForComment(line:String):Boolean={
      if(line.charAt(0).equals('#') && line.length() !=0){ 
        writeLine(line)
        println( "writing " + line)
        return true
      }else{
        println("return false")
        return false
      }
    }
      
    def writeLine(line:String){
      println("writing line: " + line +"  ****")
      writer.write(line + "\n")
    }

    def getVersionCheck(file:String):Int={
      println("checking version")
      val bufferSource = Source.fromFile(file)
      for (line <- bufferSource.getLines) {
        println(line)
        if (line.contains("master_version:")){
          val pattern = new Regex("[0-9]");
          bufferSource.close
          return (pattern findAllIn line).mkString.toInt
        }
      }
      bufferSource.close
      return 0
    }
      
      
   def checkForProvisioningClient(line : String, dbname:String){
    if(line.contains("provisioning_client")){         
      var myVar = line.split(":")
      if(myVar(1).contains("\"")){
        var writethis = "  provisioning_client: \"" + dbname + "\"," + "\n" 
        writeLine(writethis)
      }
    }else{
        writeLine(line)
    }
  }
     
     
  def checkForWeb(line:String, web:String){
    if(line.contains("deploy_layers") && web.toLowerCase() == "y")
      writeLine("  #deploy_layers: %w(Staging WAREHOUSE), #comment out to deploy web_warehouse_dev")
    else
      writeLine("  deploy_layers: %w(Staging WAREHOUSE), #comment out to deploy web_warehouse_dev")
  }

  
  def checkForRepository(line:String, dbname:String, mpi:String, version:Int){
    if(line.contains("repositories")){
      var writeThis = "  repositories: [:core"
      if(version > 47) writeThis = writeThis + ", :client"
      if(mpi.toLowerCase() =="y") writeThis = writeThis + ", :mpi"
      writeThis = writeThis + "],"
      writeLine(writeThis)
    }
  }
    
    
    
    
      //beginning of program  
      println("custom: " +custom +" user: "+ user +" mpi: " + mpi + " web :" + web + " version : " + version + " dbname: "+ dbname)
      if(dbname.toLowerCase == "master")
      {
        val version = getVersionCheck(sourceFile)
        println("in master: " + version)
      }
      
      val source = Source.fromFile(sourceFile)
      println("Before for")
      for (line <- source.getLines) {
        println(line)
        if(!checkForComment(line)){
          println("this is not a comment: ")
//          checkForProvisioningClient(line, dbname)
//          checkForWeb(line, web)
//          checkForRepository(line, dbname, mpi, version)
        }
      }
      

      writer.close()
      println("end of main")
  } //end of main
} //end object