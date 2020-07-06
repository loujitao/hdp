package com.steve.core.rddkv

import org.apache.spark.{SparkConf, SparkContext}

object Chap06_combineByKey {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、combineByKey[C]
// 作用：对相同K，把V合并成一个集合
//  参数：(createCombiner: V => C,  mergeValue: (C, V) => C,  mergeCombiners: (C, C) => C)
//    （1）createCombiner：如果这是一个新的元素,创建那个键对应的累加器的初始值
//    （2）mergeValue：如果这是一个在处理当前分区之前已经遇到的键，将该键的累加器对应的当前值与这个新的值进行合并
//    （3）mergeCombiners：由于每个分区都是独立处理的，将各个分区的结果进行合并。

//    需求：创建一个pairRDD，根据key计算每种key的均值。
    val rdd = sc.parallelize(Array(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98)),2)
//    rdd.saveAsTextFile("sparkDemo/output")
    val combine = rdd.combineByKey(
                                    (_,1),
                                    (acc:(Int,Int),v)=>(acc._1+v,acc._2+1),
                                    (acc1:(Int,Int),acc2:(Int,Int))=>(acc1._1+acc2._1,acc1._2+acc2._2)
                                  )
//    combine.collect().foreach(t=>println(t._1,t._2._1+":"+t._2._2))
    combine.collect().foreach(t=>println(t._1,t._2._1/t._2._2))
   }

}
