package com.steve.core.rddaction

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

//action算子，计算均发生在excuter端，数据需要driver端传递，并且能序列化
object Chap05_saveAsXXfile {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

//    saveAsTextFile(path)   将数据集的元素以textfile的形式保存到HDFS文件系统或者其他支持的文件系统
//    saveAsSequenceFile(path) 以Hadoop sequencefile的格式保存
//    saveAsObjectFile(path)   将RDD中的元素序列化成对象
    val rdd: RDD[Int] = sc.parallelize(1 to 10 ,2)


  }
}
