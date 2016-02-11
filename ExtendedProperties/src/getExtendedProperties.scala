import scala.io._
import scala.collection.JavaConversions._
import scala.util.matching.Regex
import java.io._
import java.lang._

object getExtendedProperties {


  def getListOfSubDirectories(directoryName: String): Array[String] = {
      return (new File(directoryName)).listFiles.filter(_.isDirectory).map(_.getName)
    }
  
  def getTable(filePath:String):String={
    val splitPath = filePath.split(raw"\\")
    for(i <- 0 until splitPath.length){
      if(splitPath(i).contains(".sql")){
        return splitPath(i).toString.replace(".sql", "").replace(".table", "")
      }
    }
    return "Table not found"
  }
  
  def getDesc(line:String, pattern:Regex):String={
    
    var returnValue = ""
    var commaArr = line.toCharArray()
    var countComma:Int = 0
    for(j <- 0 until commaArr.length){
      if(commaArr(j) == '@')
        countComma = countComma + 1
    }
    if(countComma <2){
      returnValue = (pattern findAllIn line).mkString
    }else{
      var properties = line.split("@")
      for(i <- 0 until properties.length){
        if(properties(i).contains("value")){
          return(properties(i))
        }
      }
      
    }
    return returnValue
  }
  
  def getValue(line:String, pattern:Regex, parse:String, typep:String):String={
    var properties = line.split(",")
    for(i <- 0 until properties.length){
      if(properties(i).contains(parse)){
        val retVal = properties(i).split("=")
        for(j <-0 until retVal.length){
          if(!retVal(j).contains("@")){
              if ((pattern findAllIn retVal(j)).mkString !="")
                return (pattern findAllIn retVal(j)).mkString
              else{
                if(!retVal(j).isEmpty())
                  return retVal(j).replace(";","")
                else
                  return "Needs update line: " + retVal(j)
              }
          }
        }
      }
    }
    return"Property not found with line: "+ line
  }
  
  
  def main(args: Array[String]): Unit = {
    
    val writer = new PrintWriter(new File("C:\\Users\\Winthrop\\dev\\ExtProperties.txt"))
    writer.write("TRUNCATE TABLE dbo.DataDictionary \nINSERT INTO dbo.DataDictionary ([Schema], [Table], [Column], [Description], [Usage]) \n VALUES \n (\'delete\', \'delete\', \'delete\', \'delete\', \'delete\') \n")
    var schemas = getListOfSubDirectories("C:\\Users\\Winthrop\\dev\\qdw\\Database\\Warehouse\\Schema Objects\\Schemas")
    val pattern = new Regex(raw"'[A-z]*.*'");

    for(j <- 0 until schemas.length){
      if(schemas(j) != "staging" && schemas(j) != "testdata"){
        var tables = new java.io.File("C:\\Users\\Winthrop\\dev\\qdw\\Database\\Warehouse\\Schema Objects\\Schemas\\" + schemas(j) +"\\Tables\\").listFiles.filter(_.getName.endsWith(".sql"))
        
        for(i <- 0 until tables.length){
          
          var writeMe:String = ""
          var desc:String = ""
          val source = Source.fromFile(tables(i))
          for (line <- source.getLines()){
            if(line.toLowerCase.contains("@value")){
              desc = getDesc(line, pattern)
              
            }          
            var col:String =""
            
            if(line.contains("@level2name")){
              col = getValue(line, pattern, "@level2name", "column")
            }
  
            if(col !=""){
              if(!col.contains("\'")){
                col = "\'" + col + "\'"
              }
                  
              writeMe = ",(\'" + schemas(j) + "\', \'" + getTable(tables(i).toString) + "\', " + col + ", " + desc.replace("value = N", "").replace(",","") + ", \'core\')\n"
              writer.write(writeMe)
              writeMe = ""
            }
          }//lines
          source.close()
        }//tables
      }//if good
    }//j
    
     
  writer.close()
  }//main

}//obj
