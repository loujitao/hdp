package com.steve.dataSources

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.configuration.Configuration


object GetDataSet02 {

  /**基于文件的方式创建dataSet
    *
    *  1. 读取本地文件数据 readTextFile
    *  2. 读取HDFS文件数据
    *  3. 读取CSV文件数据
    *  4. 读取压缩文件
    *  5. 遍历目录
    * @param args
    */
  def main(args: Array[String]): Unit = {
    //获取env
    val env = ExecutionEnvironment.getExecutionEnvironment

    //1.读取本地文件
    val ds1: DataSet[String] = env.readTextFile("D:\\data\\words.txt")
    ds1.print()

    //2.读取HDFS文件
    val ds2: DataSet[String] = env.readTextFile("hdfs://node01:8020/wordcount/input/words.txt")
    ds2.print()

    //3.读取CSV文件
    case class Student(id:Int, name:String)
    import org.apache.flink.api.scala._
    val ds3: DataSet[Student] = env.readCsvFile[Student]("D:\\data\\subject.csv")
    ds3.print()

    //4.读取压缩文件
    val ds4 = env.readTextFile("D:\\data\\wordcount.txt.gz")
    ds4.print()

    //5.读取文件夹
    val parameters = new Configuration
    //进行递归读取
    parameters.setBoolean("recursive.file.enumeration", true)
    val ds5 = env.readTextFile("D:\\data\\wc").withParameters(parameters)
    ds5.print()

  }
}
