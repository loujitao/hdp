package com.steve.core.rddaction

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

//action算子，计算均发生在excuter端，数据需要driver端传递，并且能序列化
object Chap07_foreach {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    val tupRDD: RDD[(String, Int)] = sc.parallelize(
     List(("a",3),("a",2),("a",4),("b",3),("c",6),("c",8)),3)

    //RDD的foreach
    tupRDD.foreach(f=>{
      println(f._1,f._2)
    })

    println("========================")
    //Array的foreach
    tupRDD.collect().foreach(t=>println(t._1,t._2))


  }
}
