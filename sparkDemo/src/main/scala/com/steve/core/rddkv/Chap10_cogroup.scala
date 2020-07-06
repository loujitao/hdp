package com.steve.core.rddkv

import org.apache.spark.{SparkConf, SparkContext}

object Chap10_cogroup{

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

//1、cogroup(otherDataset, [numTasks])
// 作用： 在类型为(K,V)和(K,W)的RDD上调用，返回一个(K,(Iterable<V>,Iterable<W>))类型的RDD

// 需求：创建两个pairRDD，并将key相同的数据聚合到一个迭代器。
    val rdd = sc.parallelize(Array((1,"a"),(2,"b"),(3,"c")))
    val rdd1 = sc.parallelize(Array((1,4),(2,5),(3,6)))

    val t: Array[(Int, (Iterable[String], Iterable[Int]))] = rdd.cogroup(rdd1).collect()
    t.foreach(z=> println(z._1,z._2._1.mkString(",")+"||"+z._2._2.mkString(",")))

   }
}
