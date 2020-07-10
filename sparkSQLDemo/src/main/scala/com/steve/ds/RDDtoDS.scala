package com.steve.ds

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Dataset, SparkSession}

object RDDtoDS {

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

    //创建RDD
    val sc: SparkContext = spark.sparkContext
    val peopleRDD: RDD[String] = sc.textFile("sparkSQLDemo/input/people.txt")

    //通过样例类转化
    import spark.implicits._
    val peopleDS: Dataset[Person] = peopleRDD.map {
      x => val p = x.split(","); Person(p(0), p(1).trim.toInt)
    }.toDS()
    peopleDS.show()

    
    spark.stop()
  }
}
