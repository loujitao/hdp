package com.steve.core.rddaction

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

//action算子，计算均发生在excuter端，数据需要driver端传递，并且能序列化
object Chap04_aggregate {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    // aggregate()    参数：(zeroValue: U)(seqOp: (U, T) ⇒ U, combOp: (U, U) ⇒ U)
    //将每个分区里面的元素通过seqOp和分区内初始值进行聚合，
    // 然后用combine函数将每个分区的结果和分区间初始值(zeroValue)进行combine操作。

    val rdd: RDD[Int] = sc.parallelize(1 to 10 ,2)
    val i: Int = rdd.aggregate(0)(_+_,_+_)
    println(i)    //55
    val j: Int = rdd.aggregate(10)(_+_,_+_)
    println(j)    //85     zeroValue即是分区内初始值，也是分区间计算的初始值


//    fold(num)(func)    折叠操作，aggregate的简化操作，seqop和combop一样。
    val k: Int = rdd.fold(0)(_+_)
    println(k)
    val h: Int = rdd.fold(10)(_+_)
    println(h)

  }
}
