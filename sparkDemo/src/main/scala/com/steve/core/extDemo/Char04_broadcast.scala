package com.steve.core.extDemo

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * 广播变量用来高效分发较大的对象。
  * 向所有工作节点发送一个较大的只读值，以供一个或多个Spark操作使用。
  *
  *  任何可序列化的类型都可以这么实现。
  *变量只会被发到各个节点一次，应作为只读值处理
  * (修改这个值不会影响到别的节点)。
  */
object Char04_broadcast {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[2]").setAppName("jdbcRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

    //通过对一个类型 T 的对象调用 SparkContext.broadcast 创建出一个 Broadcast[T] 对象。
    //定义广播变量
    val broadcastVar = sc.broadcast("S")

    val rdd: RDD[String] = sc.makeRDD(List("1","2","3","a","b","d"),3)

    rdd.foreach(x=>{   //各个分区迭代数据，执行业务代码时
      //使用广播变量    通过 value 属性访问该对象的值
       val y = broadcastVar.value+x
      println(y)
    })



  }

}
