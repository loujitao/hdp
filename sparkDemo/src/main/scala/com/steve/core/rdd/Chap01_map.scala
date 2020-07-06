package com.steve.core.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap01_map {

  //创建一个1-10数组的RDD，将所有元素*2形成新的RDD

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //1、map(func)
    //返回一个新的RDD，该RDD由每一个输入元素经过func函数转换后组成
    val rdd1: RDD[Int] = sc.makeRDD(1 to 5)
//    rdd1.collect().foreach(println)
      println(rdd1.collect().mkString(","))

    val rdd2: RDD[Int] = rdd1.map(_*2)
//    rdd2.collect().foreach(println)
    println(rdd2.collect().mkString(","))


    //2、mapPartitions(func)
    //类似于map，但独立地在RDD的每一个分片上运行
    //假设有N个元素，有M个分区，那么map的函数的将被调用N次,
    // 而mapPartitions被调用M次,一个函数一次处理所有分区。
    //每次处理一个分区的数据，这个分区的数据处理完后，原RDD中分区的数据才能释放，可能导致OOM
    val rdd = sc.parallelize(Array(1,2,3,4))
    val result: RDD[Int] = rdd.mapPartitions(x=>x.map(_*2))
    println(result.collect().mkString(","))

    //3、mapPartitionsWithIndex(func)
    //类似于mapPartitions，func的函数类型必须是(Int, Interator[T]) => Iterator[U]
    val rdd3 = sc.parallelize(Array(1,2,3,4,5,6,7,8,9,0))
    val result3: RDD[(Int, Int)] = rdd3.mapPartitionsWithIndex((a,b)=>b.map((a,_)))
    result3.foreach(println)

    //4、flatMap(func)
    val arr1=Array(Array(1,2,3,4),Array(5,6,7,8,9))
    val rdd4: RDD[Array[Int]] = sc.parallelize(arr1)
    val result4: RDD[Int] = rdd4.flatMap(x=>x)
    result4.foreach(println)
  }
}
