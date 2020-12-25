package com.steve.core.rddkv

import org.apache.spark.{SparkConf, SparkContext}

object Chap12_rightOuterJoin{

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)


// 作用： rightOuterJoin 右外连接  对两个RDD进行连接操作，确保第二个RDD的键必须存在

    val rdd = sc.parallelize(Array((1,"a"),(2,"b"),(3,"c")))
    val rdd1 = sc.parallelize(Array((1,"a"),(2,"B"),(7,"c")))

    val t = rdd.rightOuterJoin(rdd1).collect()
    t.foreach(z=> println(z._1,z._2))

    println("====================")

// 作用： leftOuterJoin 右外连接  对两个RDD进行连接操作，确保第一个RDD的键必须存在
    val t2 = rdd.leftOuterJoin(rdd1).collect()
    t2.foreach(z=> println(z._1,z._2))
   }
}
