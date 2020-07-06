package com.steve.core.extDemo

import org.apache.spark.rdd.RDD
import org.apache.spark.{Accumulator, SparkConf, SparkContext}

//累加器用来对信息进行聚合，通常在向 Spark传递函数时，比如使用 map() 函数或者用
//filter() 传条件时，可以使用驱动器程序中定义的变量，但是集群中运行的每个任务都
//会得到这些变量的一份新的副本，更新这些副本的值也不会影响驱动器中的对应变量。如
//果我们想实现所有分片处理时更新共享变量的功能，那么累加器可以实现我们想要的效果。
object Chap02_accumulator {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[2]").setAppName("jdbcRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //需求，统计分区中各个数字的总个数
    val sum: Accumulator[Int] = sc.accumulator[Int](0)   //创建分区间累加器

    val rdd: RDD[String] = sc.makeRDD(List("1","2","3","a","b","d"),3)

//    累加器的用法
    rdd.foreach(x=>{   //各个分区迭代数据，执行业务代码时
      sum +=1    //可以使用累加器的 += 方法实现统计数字总个数的功能
      println(x)
    })

    println("sum: "+sum)

  }


}
