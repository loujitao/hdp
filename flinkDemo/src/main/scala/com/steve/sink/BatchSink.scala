package com.steve.sink

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}

object BatchSink {

  /**  基于本地集合的sink
    *
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val ds: DataSet[Map[Int, String]] = env.fromElements(Map(1 -> "spark", 2 -> "flink"))
    //sink到标准输出
    ds.print()

    //sink到标准error输出
//    ds.printToErr()

    //sink到本地Collection
//    print(ds.collect())
  }

}
