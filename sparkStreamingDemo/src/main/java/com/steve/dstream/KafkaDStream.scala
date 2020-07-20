package com.steve.dstream

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

//启动虚拟机通过命令 nc -lk 9999  发送数据流
object KafkaDStream {

  def main(args: Array[String]): Unit = {

    //1、初始化spark配置信息
    val streamWC: SparkConf = new SparkConf().setMaster("local[*]").setAppName("streamWC")
    //2、初始化sparkStreamingContext
    val ssc = new StreamingContext(streamWC,Seconds(5))
    //3、通过监控端口创建DStream, 读进来的数据为一行

    val lineStreams: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(
      ssc,
      "hdp001:2181",
      "topic01",
      Map("topic01" -> 2)
    )

    //4、将每一行数据切分，形成一个个单词
    val wordStreams: DStream[String] = lineStreams.flatMap(t=>t._2.split(" "))

    //5、将单词映射为元组（word,1）
    val tupStreams: DStream[(String, Int)] = wordStreams.map((_,1))
    //将相同的单词聚合，计算次数
    val wordAndCount: DStream[(String, Int)] = tupStreams.reduceByKey(_+_)

    wordAndCount.print()

    //启动sparkStreamingContext
    ssc.start()
    ssc.awaitTermination()
  }

}
