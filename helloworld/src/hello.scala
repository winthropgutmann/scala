import scala.io._
import java.io._
import java.nio._
import scala.util.matching.Regex

object tst {
  def main(args: Array[String]): Unit = {
      val version = "#master_version: v4.10-rc"
      val ver = version.split(":")
      println(ver(1))
  }
} 