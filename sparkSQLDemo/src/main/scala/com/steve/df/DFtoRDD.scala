package com.steve.df

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object DFtoRDD {

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

    //DataFrame 转化为RDD，直接调用方法rdd()
    val peopleRDD: RDD[Row] = df.rdd
    peopleRDD.collect().foreach(println)
    spark.stop()
  }
}
