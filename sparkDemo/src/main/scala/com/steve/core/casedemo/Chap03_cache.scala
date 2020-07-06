package com.steve.core.casedemo

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap03_cache {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //2.RDD通过persist方法或cache方法可以将前面的计算结果缓存
    val rdd = sc.makeRDD(Array("steve"))

    //没有缓存的时候
    val nocache = rdd.map(_.toString+System.currentTimeMillis)
    //多次调用
    println(nocache.collect().mkString(","))
    println(nocache.collect().mkString(","))
    println(nocache.collect().mkString(","))

    //缓存     cache最终也是调用了persist方法
    val cache =  rdd.map(_.toString+System.currentTimeMillis).cache
    //多次调用
    println(cache.collect().mkString(","))
    println(cache.collect().mkString(","))
    println(cache.collect().mkString(","))
   }
}
