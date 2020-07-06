package com.steve.core.rdd2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap04_cartesian {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、cartesian(otherDataset)
    //       创建两个RDD，计算两个RDD的笛卡尔积（尽量避免使用）
    val rdd1: RDD[Int] = sc.parallelize(1 to 2)
    val rdd2: RDD[Int] = sc.parallelize(1 to 5)

    val value: RDD[(Int, Int)] = rdd1.cartesian(rdd2)
    val tuples: Array[(Int, Int)] = value.collect()
    tuples.foreach(t=>println( t._1,t._2))

   }
}
