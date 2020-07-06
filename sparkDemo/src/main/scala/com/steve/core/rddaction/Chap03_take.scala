package com.steve.core.rddaction

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

//action算子，计算均发生在excuter端，数据需要driver端传递，并且能序列化
object Chap03_take {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    // take(n)  返回一个由RDD的前n个元素组成的数组
    //    需求：创建一个RDD，统计该RDD的条数
    val rdd: RDD[Int] = sc.makeRDD(Array(3,2,6,8,4,5,1))

    val ints: Array[Int] = rdd.take(3)
    println(ints.mkString(","))

//    takeOrdered(n)   返回排序后的前n个元素组成的数组
    val result: Array[Int] = rdd.takeOrdered(3)
    println(result.mkString(","))
  }
}
