package com.steve.dataSources.customSource
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
  *     非并行数据源
  */
object CustomerSourceWithNoParallelSource {
  def main(args: Array[String]): Unit = {
    //1.准备环境
    val senv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    //2.获取数据
    import org.apache.flink.api.scala._
    //val data: DataStream[Long] = senv.addSource(new MyNoParallelSource).setParallelism(2)//Source: 1 is not a parallel source
    val data: DataStream[Long] = senv.addSource(new MyNoParallelSource).setParallelism(1)
    //3.处理数据
    data.print()
    //4.启动执行
    senv.execute()
  }

}

/**
  * 自定义非并行数据源,每隔1s生成递增数字
  */
class MyNoParallelSource extends SourceFunction[Long]{
  var count: Long = 1L
  var isRunning: Boolean = true
  override def run(ctx: SourceContext[Long]): Unit = {
    while (isRunning){
      ctx.collect(count)
      count += 1
      Thread.sleep(1000)
    }
  }

  override def cancel(): Unit = {
    isRunning = false
  }
}
