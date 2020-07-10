package com.steve.df

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

//创建DataFrame三种方式：
//1、通过spark数据源进行创建
//2、从一个存在得RDD转化
//3、从Hive Table进行查询返回
object CreateDF {

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

    df.show()
    spark.stop()
  }
}
