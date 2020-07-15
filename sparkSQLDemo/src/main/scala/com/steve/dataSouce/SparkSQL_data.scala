package com.steve.dataSouce

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkSQL_data {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")

    val spark: SparkSession = SparkSession
                    .builder()
                    .appName("tesst")
                    .config(conf)
                    .getOrCreate()

    //1、数据加载   spark
    //1)json文件
    val df: DataFrame = spark.read.json("sparkSQLDemo/input/people.json")
    //2)通用写法  format参数： json  parquet  jdbc  orc  libsvm  csv  text
    //对应简写有各自的方法： json()  parquet()
//    spark.read.format("json").load("sparkSQLDemo/input/people.json")
    df.show()
    //3）jdbc
//    val jdbcDF: DataFrame = spark.read.format("jdbc")
//      .option("url", "jbcd:mysql://192.168.0.1:3306/test")
//      .option("dbtable", "person")
//      .option("user", "root")
//      .option("password", "000000")
//      .load()

    //2、数据保存   DF
    //mode保存模式： error  append  overwrite  ignore
    df.write.format("json").mode("append").save("sparkSQLDemo/input/1.json")
    spark.stop()
  }
}
