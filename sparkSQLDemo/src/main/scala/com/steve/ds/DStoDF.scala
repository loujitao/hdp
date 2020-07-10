package com.steve.ds

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object DStoDF {

  //1、创建一个样例类
  case class Person(name:String, age:Int)

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")

    val spark: SparkSession = SparkSession
      .builder()
      .appName("tesst")
      .config(conf)
      .getOrCreate()

    import spark.implicits._
    val personDS: Dataset[Person] = Seq(Person("zhangsan",20)).toDS()

    //调用toDF方法
    val peopleDF: DataFrame = personDS.toDF()
    peopleDF.show()

    spark.stop()
  }
}
