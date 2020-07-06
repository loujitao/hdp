package com.steve.core.rddaction

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

//action算子，计算均发生在excuter端，数据需要driver端传递，并且能序列化
object Chap01_reduce {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    // reduce(func)
    //通过func函数聚集RDD中的所有元素，先聚合分区内数据，再聚合分区间数据

//    需求：创建一个RDD，将所有元素聚合得到结果。
    val rdd: RDD[Int] = sc.parallelize(1 to 10,4)
    val i: Int = rdd.reduce(_+_)
    println(i)

    val rdd2 = sc.makeRDD(Array(("a",1),("a",3),("c",3),("d",5)))
    val t: (String, Int) = rdd2.reduce((x, y) => {
      (x._1 + y._1, x._2 + y._2)
    })
    println(t._1,t._2)
  }
}
