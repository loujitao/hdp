package com.steve.core.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 生成RDD的三种方式
  */
object Chap01_glom {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //将每一个分区形成一个数组，形成新的RDD类型时RDD[Array[T]]
    val rdd: RDD[Int] = sc.parallelize(1 to 16,4)
    rdd.saveAsTextFile("sparkDemo/glom")

    val result: RDD[Array[Int]] = rdd.glom()
    result.foreach(i =>{
        println(i.mkString(","))
    })

  }
}
