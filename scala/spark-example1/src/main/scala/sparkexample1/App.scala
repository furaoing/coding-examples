package sparkexample1

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import scala.util.matching.Regex

object App {
  def main(args: Array[String]): Unit = {
    val appName = "SparkExample"
    val conf = new SparkConf().setAppName(appName)
    val sc = new SparkContext(conf)
    val lines = sc.textFile("hdfs://localhost:9000/dataset/bliip2")
    val results = lines.map(line => specialCount(line, "and"))
    val finalResult = results.reduce((a, b) => a + b)

    println("Final Result: " + finalResult)
  }

  def specialCount(sen: String, subStr: String): Float = {
       val pattern = new Regex(subStr)
       val occurence: Int = pattern.findAllIn(sen).toList.length

    val result = sen.length match {
      case 0 => 0
      case _ => occurence.toFloat/sen.length.toFloat
    }

    result
  }
}
