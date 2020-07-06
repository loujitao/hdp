package com.steve.core.rddkv

import org.apache.spark.{SparkConf, SparkContext}

object Chap08_mapValues{

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

//1、mapValues
// 作用：针对于(K,V)形式的类型只对V进行操作

//    需求：创建一个pairRDD，并将value添加字符串"|||"
    val rdd = sc.parallelize(Array((1,"a"),(1,"d"),(2,"b"),(3,"c")))

    val result= rdd.mapValues(_+"|||").collect().foreach(t=>println(t._1,t._2))
   }

}
