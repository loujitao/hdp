package com.steve.core.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap04_coalesce {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、coalesce(numPartitions)   缩减分区数，用于大数据集过滤后，提高小数据集的执行效率。
    //默认情况下，只有8个并行任务来操作，但是可以传入一个可选的numTasks参数改变它。
    val rdd: RDD[Int] = sc.parallelize(1 to 8,4)
     rdd.saveAsTextFile("sparkDemo/part1")

    val rdd2: RDD[Int] = rdd.coalesce(3)  //默认不进行shuffle过程
//    val rdd2: RDD[Int] = rdd.coalesce(3,true)
    rdd2.saveAsTextFile("sparkDemo/part2")

    // repartition(numPartitions)    根据分区数，重新通过网络随机洗牌所有数据。
    //底层就是调用coalesce(numPartitions) 的方法。
    val rdd3: RDD[Int] = rdd.repartition(3)  //默认进行shuffle过程
    rdd3.saveAsTextFile("sparkDemo/part3")

  }
}
