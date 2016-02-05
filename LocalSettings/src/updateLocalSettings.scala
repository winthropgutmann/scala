import scala.io._
import java.io._
import java.nio._
import scala.util.matching.Regex

//checking for master. looking at how to return the master version from Localsettings.rb

object tst {
  def main(args: Array[String]): Unit = {

    //variable declarations
    var dbname = if(args(1).replace(".", "").replace("-", "") =="") args(0).replace(".", "").replace("-", "") else args(1).replace(".", "").replace("-", "")
    val name = args(2)
    val mpi = args(3)
    val web = args(4)
    val sourceFile = "C:\\Users\\" + name + "\\dev\\qdw-deployment\\Build\\LocalSettings2.rb"
    val writer = new PrintWriter(new File("C:\\Users\\" + name + "\\dev\\qdw-deployment\\Build\\LocalSettings.rb"))
    
    //functions
    def checkForMaster(line:String, dbname:String):String={
      if(dbname == "master"){
        val version = line.split(":")
        return version(1)  
      }else{
        return dbname
      }
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
    
    def writeLine(line: String){
      writer.write(line + "\n")
    }
    
    
    def checkForWeb(line:String, web:String){
      if(line.contains("deploy_layers") && web.toLowerCase() == "y")
        writeLine("  #deploy_layers: %w(Staging WAREHOUSE), #comment out to deploy web_warehouse_dev")
      else
        writeLine("  deploy_layers: %w(Staging WAREHOUSE), #comment out to deploy web_warehouse_dev")
    }
    
    def checkForComment(line:String, dbname:String) :Int={
      if(line.charAt(0).equals('#')){
        if(line.contains("#master_version:"))
          checkForMaster(line, dbname)
        return 1  
      }else{
        return 0
      }
    }
    
    def checkForRepository(line:String, dbname:String, mpi:String){
      if(line.contains("repositories")){

        var writeThis = "  repositories: [:core"
        
        if(checkVersion(dbname).toInt > 47)
          writeThis = writeThis + ", :client"
        
        if(mpi.toLowerCase() =="y")
          writeThis = writeThis + ", :mpi"
          
         writeThis = writeThis + "],"
         writeLine(writeThis)
      }
    }
    
    def checkVersion(line:String):String ={
        val pattern = new Regex("[0-9]");
        val version = (pattern findAllIn line).mkString
        
        return version
    }
    
    
    //main

    for (line <- Source.fromFile(sourceFile).getLines()){
      if(checkForComment(line, dbname) == 1){
        writeLine(line)
      }else{
        checkForProvisioningClient(line, dbname)
        checkForRepository(line, dbname, mpi)
        checkForWeb(line, web)
      }
    }
    writer.close()
  }
} 