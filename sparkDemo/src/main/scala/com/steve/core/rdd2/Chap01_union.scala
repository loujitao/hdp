package com.steve.core.rdd2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap01_union {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、union(otherDataset)
    //    对源RDD和参数RDD求并集后返回一个新的RDD
    val rdd1: RDD[Int] = sc.parallelize(1 to 5)
    val rdd2: RDD[Int] = sc.parallelize(4 to 8)

    val rdd3: RDD[Int] = rdd1.union(rdd2)
    println(rdd3.collect().mkString(" "))
    
   }
}
