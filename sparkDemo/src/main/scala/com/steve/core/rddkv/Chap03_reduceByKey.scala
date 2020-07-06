package com.steve.core.rddkv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap03_reduceByKey {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、reduceByKey(func, [numTasks])
    //    在一个(K,V)的RDD上调用，返回一个(K,V)的RDD，使用指定的reduce函数，
    // 将相同key的值聚合到一起，reduce任务的个数可以通过第二个可选的参数来设置。
    val rdd = sc.parallelize(List(("female",1),("male",5),("female",5),("male",2)))
    val result: RDD[(String, Int)] = rdd.reduceByKey((x,y) => x+y)
    result.collect().foreach(t=>println(t._1,t._2))
   }

// TODO 1. reduceByKey：按照key进行聚合，在shuffle之前有combine（预聚合）操作，返回结果是RDD[k,v].
// TODO 2. groupByKey：按照key进行分组，直接进行shuffle。
// TODO 3. 开发指导：reduceByKey比groupByKey，建议使用。但是需要注意是否会影响业务逻辑。
}
