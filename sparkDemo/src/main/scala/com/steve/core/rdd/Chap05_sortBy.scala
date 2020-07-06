package com.steve.core.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap05_sortBy {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、 sortBy(func,[ascending], [numTasks])
    //    使用func先对数据进行处理，按照处理后的数据比较结果排序，默认为正序。
    val rdd: RDD[Int] = sc.parallelize(Array(2,3,1,4,5,8,7,6))

    //按照自身大小排序
    val str1: String = rdd.sortBy(x=>x).collect().mkString(",")
    //按照与3余数的大小排序
    val str2: String = rdd.sortBy(x=>x%3).collect().mkString(",")

    println(str1)
    println(str2)

   }
}
