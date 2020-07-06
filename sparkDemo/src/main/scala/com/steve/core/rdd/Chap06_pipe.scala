package com.steve.core.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap06_pipe {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、 pipe(command, [envVars])
    //    管道，针对每个分区，都执行一个shell脚本，返回输出的RDD。
    val rdd: RDD[String] = sc.parallelize(Array("a","b","c"))
    val rdd2: RDD[String] = rdd.pipe("sparkDemo/echo.sh")
    println(rdd2.collect().mkString(" "))
    
   }
}
