package com.steve.core.rddkv

import org.apache.spark.{SparkConf, SparkContext}

object Chap09_join{

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

//1、join(otherDataset, [numTasks])
// 作用： 在类型为(K,V)和(K,W)的RDD上调用，返回一个相同key对应的所有元素对在一起的(K,(V,W))的RDD

// 需求：创建两个pairRDD，并将key相同的数据聚合到一个元组。
    val rdd = sc.parallelize(Array((1,"a"),(1,"d"),(2,"b"),(3,"c")))

    val result= rdd.mapValues(_+"|||").collect().foreach(t=>println(t._1,t._2))
   }

}
