package com.steve.core.sparkfile

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap01_textFile {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //创建RDD
    val rdd: RDD[Int] = sc.makeRDD(Array(1,2,3,4,1,7,9))
    //存储为textFile
    rdd.saveAsTextFile("file:///E:/ideawork/hdp/sparkDemo/textFile")
    //读取为textFile
    val textRDD: RDD[String] = sc.textFile("file:///E:/ideawork/hdp/sparkDemo/textFile/part-00000")
    println(textRDD.collect().mkString(","))


   }


}
