import scala.io._

object damn {
  def main(args: Array[String]): Unit = {
    
    val sourceFile = "C:\\Users\\Winthrop\\dev\\qdw-deployment\\Build\\LocalSettings.rb"
    println("hello world")
    val bufferSource = Source.fromFile("C:\\Users\\Winthrop\\dev\\qdw-deployment\\Build\\LocalSettings.rb")
    for (line <- bufferSource.getLines) {
      println(line.mkString)
    }
    bufferSource.close
    println("eof")
  }
}