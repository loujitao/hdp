package com.steve.udf

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql._

object SparkSQL_UDAF_class {

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

    //创建聚合函数对象
    val udaf = new MyAvgClass
    //将聚合函数对象转化为查询列，起别名
    val col: TypedColumn[UserBean, Double] = udaf.toColumn.name("avgAge")

    //使用查询列，查询dataSet
    import spark.implicits._
    val ds: Dataset[UserBean] = df.as[UserBean]
    ds.select(col).show()

    spark.stop()
  }
}

case class UserBean(name:String, age:Long)
case class AvgBuffer(var sum:Long, var count:Long)
//用户自定义聚合函数 (强类型)   求平均值
class MyAvgClass extends Aggregator[UserBean,AvgBuffer,Double]{

  //初始化
  override def zero: AvgBuffer = {
    AvgBuffer(0,0)
  }
  //计算
  override def reduce(b: AvgBuffer, a: UserBean): AvgBuffer = {
      b.sum = b.sum + a.age
      b.count = b.count + 1
      b
  }
  //多节点合并
  override def merge(b1: AvgBuffer, b2: AvgBuffer): AvgBuffer = {
    b1.sum = b1.sum + b2.sum
    b1.count = b1.count + b2.count
    b1
  }
  //完成计算结果
  override def finish(reduction: AvgBuffer): Double = {
     reduction.sum.toDouble / reduction.count
  }
  //固定写法，指定数据结构类型     product自定义结构
  override def bufferEncoder: Encoder[AvgBuffer] = Encoders.product
  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}
