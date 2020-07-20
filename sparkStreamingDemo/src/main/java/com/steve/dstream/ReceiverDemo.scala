package com.steve.dstream

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}

//启动虚拟机通过命令 nc -lk 9999  发送数据流
object ReceiverDemo {

  def main(args: Array[String]): Unit = {

    //1、初始化spark配置信息
    val streamWC: SparkConf = new SparkConf().setMaster("local[*]").setAppName("streamWC")
    //2、初始化sparkStreamingContext
    val ssc = new StreamingContext(streamWC,Seconds(5))
    //3、通过监控端口创建DStream, 读进来的数据为一行
    val lineStreams: ReceiverInputDStream[String] = ssc.receiverStream(new MyReciever("hdp001",9999))
    //4、将每一行数据切分，形成一个个单词
    val wordStreams: DStream[String] = lineStreams.flatMap(_.split(" "))

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

//自定义接收器
class MyReciever(host:String, port:Int) extends Receiver[String](StorageLevel.MEMORY_ONLY){

  var socket:java.net.Socket=null
  def receiver(): Unit ={
    socket=new Socket(host,port)
    val reader = new BufferedReader(new InputStreamReader(socket.getInputStream,"UTF-8"))
    var line:String =null
    while ((line = reader.readLine())!=null){
          if("END".equals(line)){
            return
          }else{
            this.store(line)
          }
    }
  }

  override def onStart(): Unit = {
      new Thread(new Runnable {
        override def run(): Unit = {
          receiver()
        }
      }).start()
  }

  override def onStop(): Unit = {
    if (socket!=null){
      socket.close()
      socket=null
    }
  }

}
