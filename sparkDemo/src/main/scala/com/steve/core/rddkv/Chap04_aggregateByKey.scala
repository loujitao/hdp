package com.steve.core.rddkv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap04_aggregateByKey {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、aggregateByKey  先分区内计算，后分区间计算       科里化函数
 //  参数：(zeroValue:U,[partitioner: Partitioner]) (seqOp: (U, V) => U,combOp: (U, U) => U)
//    （1）zeroValue：给每一个分区中的每一个key一个初始值；
//    （2）seqOp：函数用于在每一个分区中用初始值逐步迭代value；
//    （3）combOp：函数用于合并每个分区中的结果。

//    需求：创建一个pairRDD，取出每个分区相同key对应值的最大值，然后相加
    val rdd = sc.parallelize(List(("a",3),("a",2),("c",4),("b",3),("c",6),("c",8)),2)
    rdd.saveAsTextFile("sparkDemo/output")
    val agg = rdd.aggregateByKey(0)(math.max(_,_),_+_)
    agg.collect().foreach(t=>println(t._1,t._2))
   }

}
