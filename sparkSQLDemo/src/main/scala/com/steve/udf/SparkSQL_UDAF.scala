package com.steve.udf

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object SparkSQL_UDAF {

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

    val udaf = new MyAvg
    //注册function
    spark.udf.register("avgAge",udaf)

    //使用自定义聚合函数
    df.createOrReplaceTempView("persons")
    spark.sql("SELECT avgAge(age) FROM persons").show()

    spark.stop()
  }
}

//用户自定义聚合函数    求平均值
class MyAvg extends UserDefinedAggregateFunction{
  //聚合函数输入参数的数据类型
  override def inputSchema: StructType ={
    new StructType().add("age",LongType)
  }
  //聚合缓冲区中值的数据类型   计算时的数据类型
  override def bufferSchema: StructType = {
    new StructType().add("sum",LongType).add("count",LongType)
  }
  //函数最终返回的类型
  override def dataType: DataType = DoubleType
  //函数是否稳定
  override def deterministic: Boolean = true
  //计算前的初始化值
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0L    // <=>  sum
    buffer(1) = 0L   // <=>  count
  }
  //根据查询结果，更新缓冲区数据
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getLong(0) + input.getLong(0)   //sum
    buffer(1) = buffer.getLong(1) + 1           //count
  }
  //将多个节点的缓冲区数据合并
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)   //sum汇总
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)   //count汇总
  }
  //计算最终结构   最终表达式的类型要和上面的dataType类型一致
  override def evaluate(buffer: Row): Any = {
    buffer.getLong(0).toDouble / buffer.getLong(1)
  }
}
