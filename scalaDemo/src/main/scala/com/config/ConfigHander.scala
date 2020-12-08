package com.config

import com.typesafe.config.ConfigFactory

/**
  * scala版本中配置文件读取工具类
  */
object ConfigHander {
  //默认加载resource目录下的application.properties
  private val config = ConfigFactory.load()

  //spark
  val spark_master: String = config.getString("spark.master")
  val spark_appname: String = config.getString("spark.appname")

  //数据库配置及表信息
  //Oracle
  val db_driver: String = config.getString("Odb.driver")
  val db_url: String = config.getString("Odb.url")
  val db_user: String = config.getString("Odb.user")
  val db_password: String = config.getString("Odb.password")
  val db_initialPoolSize = config.getInt("Odb.initialPoolSize")
  val db_maxIdleTime = config.getInt("Odb.maxIdleTime")
  val db_maxPoolSize = config.getInt("Odb.maxPoolSize")
  val db_minPoolSize = config.getInt("Odb.minPoolSize")
  val db_table_white = config.getString("Odb.dbtable.white")
  val db_table_black = config.getString("Odb.dbtable.black")

  //offersetTABLE
  val db_offersetTable: String = config.getString("Odb.offersetTable")
  val db_ruletable=config.getString("Odb.ruletable")

  //redis
//  val redis_host = config.getString("redis.host")
  val redis_port = config.getInt("redis.port")
  val redis_maxTotal = config.getInt("redis.maxTotal")
  val redis_maxidle = config.getInt("redis.maxidle")
  val redis_minidle = config.getInt("redis.minidle")
  val redis_TestOnBoorow = config.getBoolean("redis.TestOnBoorow")
  val redis_maxwaitmillis = config.getInt("redis.maxwaitmillis")

  //Kafka配置




}
