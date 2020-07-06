package com.steve.core.rdd2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap02_subtract {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、subtract (otherDataset)
    //   计算差的一种函数，去除两个RDD中相同的元素，不同的RDD将保留下来
    val rdd1: RDD[Int] = sc.parallelize(1 to 5)
    val rdd2: RDD[Int] = sc.parallelize(4 to 8)

    val rdd3: RDD[Int] = rdd1.subtract(rdd2)
    println("=====rdd1与rdd2的差集 ======")
    println(rdd3.collect().mkString(" "))

    val rdd4: RDD[Int] = rdd2.subtract(rdd1)
    println("=====rdd2与rdd1的差集 ======")
    println(rdd4.collect().mkString(" "))
    
   }
}
