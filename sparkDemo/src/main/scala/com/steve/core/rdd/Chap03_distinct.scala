package com.steve.core.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap03_distinct {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //distinct([numTasks]))   对源RDD进行去重后返回一个新的RDD
    //默认情况下，只有8个并行任务来操作，但是可以传入一个可选的numTasks参数改变它。
    val rdd: RDD[Int] = sc.parallelize(Array(1,2,2,3,1,3,4,5,4))
     println(rdd.collect().mkString(","))

//    val result: RDD[Int] = rdd.distinct()
    val result: RDD[Int] = rdd.distinct(2)
    println(result.collect().mkString(","))
  }
}
