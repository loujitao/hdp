package com.steve.core.sparkfile

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap03_sequenceFile {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //创建rdd
    val rdd: RDD[(String, Int)] = sc.makeRDD(Array(("a",1),("b",2),("c",3),("d",4)))
    //保存为序列化文件
//    rdd.saveAsSequenceFile("file:///E:/ideawork/hdp/sparkDemo/seqFile")

    val rdd1: RDD[(String, Int)] = sc.sequenceFile[String, Int]("file:///E:/ideawork/hdp/sparkDemo/seqFile")
    rdd1.collect().foreach(t =>println(t._1,t._2))

   }


}
