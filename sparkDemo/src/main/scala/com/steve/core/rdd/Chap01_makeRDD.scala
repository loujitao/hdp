package com.steve.core.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 生成RDD的三种方式
  */
object Chap01_makeRDD {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    val arr=Array(1,2,3,4,5,6,7,8)
    //使用parallelize()从集合创建
    val rdd = sc.parallelize(arr)
    //使用makeRDD()从集合创建   底层是使用parallelize()
    val rdd1 = sc.makeRDD(arr)
    rdd1.saveAsTextFile("sparkDemo/out")
    //使用makeRDD()从集合创建   指定分区数
    val rdd2 =sc.makeRDD(arr,2)
    rdd2.saveAsTextFile("sparkDemo/out1")


    //由外部存储系统的数据集创建   底层使用的是Hadoop文件拆分规则，实际分区可能比最小分区数大
    val rdd3: RDD[String] = sc.textFile("sparkDemo/input",2)
    rdd3.foreach(println)

  }
}
