package com.steve.core.wc

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

  def main(args: Array[String]): Unit = {

    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("wordcount")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

//    val line: RDD[String] = sc.textFile("sparkDemo/input")
//    val words: RDD[String] = line.flatMap(_.split(" "))
//    val tuples: RDD[(String, Int)] = words.map((_,1))
//    val tup2: RDD[(String, Int)] = tuples.reduceByKey(_+_)
//    val result: Array[(String, Int)] = tup2.collect()

    val result: Array[(String, Int)] = sc.textFile("sparkDemo/input")
                                          .flatMap(_.split(" ")).map((_,1))
                                          .reduceByKey(_+_).collect()

    result.foreach(println)
  }
}
