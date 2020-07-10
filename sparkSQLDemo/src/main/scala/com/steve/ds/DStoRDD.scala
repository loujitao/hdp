package com.steve.ds

import com.steve.ds.CreateDS.Person
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object DStoRDD {

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
    personDS.show()

    //直接调用rdd()方法
    val peopleRDD: RDD[Person] = personDS.rdd
    peopleRDD.collect().foreach(println)

    
    spark.stop()
  }
}
