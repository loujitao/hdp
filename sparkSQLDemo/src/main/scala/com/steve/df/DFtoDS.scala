package com.steve.df

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object DFtoDS {

  case class Person(name:String, age:Long)

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")

    val spark: SparkSession = SparkSession
      .builder()
      .appName("tesst")
      .config(conf)
      .getOrCreate()

    //从spark数据源进行创建
    val df: DataFrame = spark.read.json("sparkSQLDemo/input/people.json")

    //DS的字段类型必须比DF的取值范围大， 比如: Int -> Long, 否则会报错
    import spark.implicits._
    val peopleDS: Dataset[Person] = df.as[Person]

    peopleDS.show()
    spark.stop()
  }
}
