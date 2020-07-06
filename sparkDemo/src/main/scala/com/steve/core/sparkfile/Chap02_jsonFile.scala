package com.steve.core.sparkfile

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Chap02_jsonFile {

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)

//    导入解析json所需的包
    import scala.util.parsing.json.JSON
    //创建RDD
    val json = sc.textFile("file:///E:/ideawork/hdp/sparkDemo/input/people.json")

//    解析json数据
    val result  = json.map(JSON.parseFull)

//    打印
    val opt: Array[Option[Any]] = result.collect()
      opt.foreach( x=>{
        x.mkString(",")
      })
   }


}
