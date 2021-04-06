package com.steve.sink

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.core.fs.FileSystem.WriteMode

object FileSink {

  /**  基于文件的sink
    *     writeAsText()：
    *  可以sink到本地文件,hdfs文件(
    *    支持多种文件的存储格式，包括text文件，CSV文件等)
    * @param args
    */
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val ds: DataSet[Map[Int, String]] = env.fromElements(Map(1 -> "spark", 2 -> "flink"))

    //sink到本地文件
    ds.setParallelism(1).writeAsText("D:\\data\\output", WriteMode.OVERWRITE)

    //sink到hdfs
//    ds.setParallelism(1).writeAsText("hdfs://node01:8020/wordcount/output", WriteMode.OVERWRITE)

    env.execute()
    //主意：不论是本地还是hdfs.
    //如果Parallelism>1将把path当成目录名称，
    //如果Parallelism=1将把path当成文件名。
    //OVERWRITE模式下如果文件已经存在，则覆盖
  }

}
