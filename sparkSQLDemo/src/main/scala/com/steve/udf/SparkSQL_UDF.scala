package com.steve.udf

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkSQL_UDF {

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
    df.show()

//    import spark.implicits._
    //注册function
    spark.udf.register("addName",(x:String)=>"Name: "+ x)

    //创建一个临时表
    df.createOrReplaceTempView("persons")
    spark.sql("SELECT addName(name) ,age FROM persons").show()

    spark.stop()
  }
}
