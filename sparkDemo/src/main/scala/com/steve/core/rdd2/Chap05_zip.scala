package com.steve.core.rdd2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap05_zip {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、zip(otherDataset)
    //       将两个RDD组合成Key/Value形式的RDD,
    // 这里默认两个RDD的partition数量以及元素数量都相同，否则会抛出异常。
    val rdd1: RDD[Int] = sc.parallelize(1 to 3)
    val rdd2: RDD[String] = sc.parallelize(Array("A","B","C"))

    val tupRdd: RDD[(String, Int)] = rdd2.zip(rdd1)
    tupRdd.collect().foreach(t=> println(t._1,t._2))


   }
}
