package com.steve.core.rddkv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap02_groupByKey {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、groupByKey
    //    groupByKey也是对每个key进行操作，但只生成一个sequence。
    val strings = Array("a","zhang","li","sun","wang","li","sun","wang")
    val tupleRdd: RDD[(String, Int)] = sc.parallelize(strings).map((_,1))
    val rdd: RDD[(String, Iterable[Int])] = tupleRdd.groupByKey()
    val t: Array[(String, Iterable[Int])] = rdd.collect()
    t.foreach(t=>{
      println(t._1, t._2.mkString(";"))
    })

   }
}
