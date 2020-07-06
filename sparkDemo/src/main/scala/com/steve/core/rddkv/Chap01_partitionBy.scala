package com.steve.core.rddkv

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

object Chap01_partitionBy {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、partitionBy
    //    对pairRDD进行分区操作，如果原有的partionRDD和现有的partionRDD是
    // 一致的话就不进行分区， 否则会生成ShuffleRDD，即会产生shuffle过程。
    val rdd1: RDD[(Int, String)] = sc.parallelize(
              Array((1,"aaa"),(2,"bbb"),(3,"ccc"),(4,"ddd")),4)
    println(rdd1.partitions.size)

    val rdd2: RDD[(Int, String)] = rdd1.partitionBy(
              new org.apache.spark.HashPartitioner(2))
    //使用自定义的分区器
    val rdd3: RDD[(Int, String)] = rdd1.partitionBy(new MyPartitioner(2))

    println(rdd2.partitions.size)

   }
}
//自定义分区规则
class MyPartitioner(partitions: Int) extends Partitioner {
  override def numPartitions: Int = {
    partitions
  }

  override def getPartition(key: Any): Int = key match {
    case null => 0
    case _ =>  key.hashCode % partitions
  }
}