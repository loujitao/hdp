package com.steve.core.casedemo

import org.apache.spark.{SparkConf, SparkContext}

object Chap04_checkPoint {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //设置检查点
    sc.setCheckpointDir("file:///E:/ideawork/hdp/sparkDemo/checkpoint")

    val rdd = sc.makeRDD(Array("steve"))
    // 将RDD转换为携带当前时间戳并做checkpoint
    val ch = rdd.map(_.toString+System.currentTimeMillis)
    ch.checkpoint()  //该函数将会创建一个二进制的文件
    //多次调用
    println(ch.collect().mkString(","))
    println(ch.collect().mkString(","))
    println(ch.collect().mkString(","))
   }


}
