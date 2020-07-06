package com.steve.core.rddaction

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

//action算子，计算均发生在excuter端，数据需要driver端传递，并且能序列化
object Chap02_count {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    // count()   返回RDD中元素的个数

//    需求：创建一个RDD，统计该RDD的条数
    val rdd: RDD[Int] = sc.parallelize(1 to 10 )
    val i = rdd.count()
    println(i)

  }
}
