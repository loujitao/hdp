package com.steve.core.rddkv

import org.apache.spark.{SparkConf, SparkContext}

object Chap07_sortByKey {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、sortByKey([ascending], [numTasks])
// 作用：在一个(K,V)的RDD上调用，K必须实现Ordered接口，返回一个按照key进行排序的(K,V)的RDD

//    需求：创建一个pairRDD，按照key的正序和倒序进行排序
    val rdd = sc.parallelize(Array((3,"aa"),(6,"cc"),(2,"bb"),(1,"dd")))

    //正序
    val result1 = rdd.sortByKey(true)
    result1.collect().foreach(t=>println(t._1,t._2))
    //倒序
    val result2 = rdd.sortByKey(false)
    result2.collect().foreach(t=>println(t._1,t._2))
   }

}
