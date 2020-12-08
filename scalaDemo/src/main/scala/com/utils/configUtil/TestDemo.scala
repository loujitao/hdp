package com.utils.configUtil

object TestDemo {

  def main(args: Array[String]): Unit = {
    // 获取Kafka配置
    val broker_list = ConfigurationManager.config.getString("kafka.broker.list")
    val topics = ConfigurationManager.config.getString("kafka.topics")

    println("kafka.broker.list: "+broker_list)
    println("kafka.topics: "+broker_list)
  }
}
