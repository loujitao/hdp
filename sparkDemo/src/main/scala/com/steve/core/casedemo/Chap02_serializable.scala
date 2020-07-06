package com.steve.core.casedemo

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
class Search1(query:String) {  //extends Serializable{
  //不实现序列化接口，会报以下错误：
//Exception in thread "main" org.apache.spark.SparkException: Task not serializable

  //过滤出包含字符串的数据
  def isMatch(s: String): Boolean = {
    s.contains(query)
  }

  //过滤出包含字符串的RDD
  def getMatch1 (rdd: RDD[String]): RDD[String] = {
    rdd.filter(isMatch)
  }

  //过滤出包含字符串的RDD
  def getMatche2(rdd: RDD[String]): RDD[String] = {
    rdd.filter(x => x.contains(query))
  }
}

object Chap02_serializable {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //2.创建一个RDD
    val rdd: RDD[String] = sc.parallelize(Array("hadoop", "spark", "hive", "scala"))
    //3.创建一个Search对象
    val search = new Search1("a")
    //4.运用第一个过滤函数并打印结果
//    getMatch1 程序在运行过程中需要将Search对象序列化以后传递到Executor端。
    val match1: RDD[String] = search.getMatche2(rdd)

    match1.collect().foreach(println)
   }
}
