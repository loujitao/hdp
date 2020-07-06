package com.steve.core.rddkv

import org.apache.spark.{SparkConf, SparkContext}

object Chap05_foldByKey {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、foldByKey
// 作用：aggregateByKey的简化操作，seqop和combop相同  先分区内计算，后分区间计算       科里化函数
//  参数：(zeroValue:U,[partitioner: Partitioner]) (seqOp: (U, V) => U,combOp: (U, U) => U)
//    （1）zeroValue：给每一个分区中的每一个key一个初始值；
//    （2）seqOp：函数用于在每一个分区中用初始值逐步迭代value；
//    （3）combOp：函数用于合并每个分区中的结果。

//    需求：创建一个pairRDD，计算相同key对应值的相加结果
    val rdd = sc.parallelize(List((1,3),(1,2),(1,4),(2,3),(3,6),(3,8)),3)
    rdd.saveAsTextFile("sparkDemo/output")
//    val agg = rdd.aggregateByKey(0)(_+_,_+_)
    val agg = rdd.foldByKey(0)(_+_)
    agg.collect().foreach(t=>println(t._1,t._2))
   }

}
