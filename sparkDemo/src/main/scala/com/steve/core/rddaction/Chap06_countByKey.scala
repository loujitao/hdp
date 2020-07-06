package com.steve.core.rddaction

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

//action算子，计算均发生在excuter端，数据需要driver端传递，并且能序列化
object Chap06_countByKey {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

   val tupRDD: RDD[(String, Int)] = sc.parallelize(
     List(("a",3),("a",2),("a",4),("b",3),("c",6),("c",8)),3)
   val map: collection.Map[String, Long] = tupRDD.countByKey()
   map.foreach( m=>println(m._1,m._2))
  }
}
