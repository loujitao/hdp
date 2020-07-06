package com.steve.core.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap02_filter {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //过滤。返回一个新的RDD
    val rdd: RDD[Int] = sc.parallelize(1 to 16)
     println(rdd.collect().mkString(","))

    val result: RDD[Int] = rdd.filter(i=>i%2==0)
    println(result.collect().mkString(","))
  }
}
