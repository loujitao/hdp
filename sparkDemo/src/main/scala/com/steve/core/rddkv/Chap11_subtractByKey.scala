package com.steve.core.rddkv

import org.apache.spark.{SparkConf, SparkContext}

object Chap11_subtractByKey{

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)


// 作用： 删除rdd中与other RDD中的键相同的元素

    val rdd = sc.parallelize(Array((1,"a"),(2,"b"),(3,"c")))
    val rdd1 = sc.parallelize(Array((1,"a"),(2,"B"),(7,"c")))

    val t:Array[(Int, String)] = rdd.subtractByKey(rdd1).collect()
    t.foreach(z=> println(z._1,z._2))

   }
}
