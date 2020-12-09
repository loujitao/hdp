package com.steve.core.extDemo

import java.util

import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{Accumulator, SparkConf, SparkContext}

//累加器用来对信息进行聚合，通常在向 Spark传递函数时，比如使用 map() 函数或者用
//filter() 传条件时，可以使用驱动器程序中定义的变量，但是集群中运行的每个任务都
//会得到这些变量的一份新的副本，更新这些副本的值也不会影响驱动器中的对应变量。如
//果我们想实现所有分片处理时更新共享变量的功能，那么累加器可以实现我们想要的效果。

//自定义累加器类型
class LogAccumulator extends org.apache.spark.util.AccumulatorV2[String, java.util.Set[String]] {
  private val _logArray: java.util.Set[String] = new java.util.HashSet[String]()

  override def isZero: Boolean = {
    _logArray.isEmpty
  }

  override def copy(): AccumulatorV2[String, java.util.Set[String]] = {
    val newAcc = new LogAccumulator()
    _logArray.synchronized{
      newAcc._logArray.addAll(_logArray)
    }
    newAcc
  }

  override def reset(): Unit = {
    _logArray.clear()
  }

  override def add(v: String): Unit = {
    _logArray.add(v)
  }

  override def merge(other: AccumulatorV2[String, java.util.Set[String]]): Unit = {
    other match {
      case o: LogAccumulator => _logArray.addAll(o.value)
    }
  }

  override def value: java.util.Set[String] = {
    java.util.Collections.unmodifiableSet(_logArray)
  }

}
object Chap03_defAccum {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[2]").setAppName("jdbcRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //创建自定义累加器
    val accum = new LogAccumulator
    //将累加器注册到spark上下文中
    sc.register(accum, "logAccum")

    // 过滤掉带字母的
    val sum = sc.parallelize(Array("1222", "2a", "a3", "4b", "533", "6", "7cd", "18", "9"), 2).filter(line => {
      val pattern = """^-?(\d+)"""
      val flag = line.matches(pattern)
        if (!flag) {
          accum.add(line)
        }
        flag
    }).map(_.toInt).reduce(_ + _)

    import scala.collection.JavaConversions._
    println("sum: " + sum)
    for (v <- accum.value) print(v + " : ")
    sc.stop()
  }


}
