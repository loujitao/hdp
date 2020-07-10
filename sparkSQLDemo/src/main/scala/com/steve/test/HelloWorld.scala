package com.steve.test

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object HelloWorld {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")

    val spark: SparkSession = SparkSession
                    .builder()
                    .appName("tesst")
                    .config(conf)
                    .getOrCreate()

    val df: DataFrame = spark.read.json("sparkSQLDemo/input/people.json")
    import  spark.implicits._
    df.filter($"age">20).show()

    //创建一个临时表
    df.createOrReplaceTempView("persons")
    spark.sql("SELECT * FROM persons where age > 20").show()

    //创建一个全局表   SQL中表名前需要带上global_temp
    df.createGlobalTempView("people")
    spark.newSession().sql("SELECT * FROM global_temp.people where age > 20").show()


    spark.stop()
  }
}
