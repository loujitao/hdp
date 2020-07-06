package com.steve.core.extDemo

import java.sql.DriverManager

import org.apache.spark.rdd.{JdbcRDD, RDD}
import org.apache.spark.{SparkConf, SparkContext}

//操作mysql
object Chap01_mysql {

  def read_mysql: Unit ={
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[*]").setAppName("jdbcRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)
    //3.定义连接mysql的参数
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://192.168.40.101:3306/mytest"
    val userName = "root"
    val passWd = "123456"

    //创建JdbcRDD    参数数量少了：Parameter index out of range (2 > number of parameters, which is 1)
    val rdd = new JdbcRDD(sc, () => {
      Class.forName(driver)
      DriverManager.getConnection(url, userName, passWd)
    },
      "select `nid`,`pname`,`page` from `person` where `nid`>=? and `nid`<=?;",
      1,   //lowerBound 下界  param1
      10,  //upperBound 上界  param2
      1   //numPartitions
      , r => (r.getInt(1), r.getString(2), r.getString(3))
    )
    //打印最后结果
    println(rdd.count())
    rdd.foreach(println)

    sc.stop()
  }

  def write_mysql: Unit ={
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[*]").setAppName("jdbcRDD")
    //创建sparkcontext上下文
    val sc = new SparkContext(conf)
    //3.定义连接mysql的参数
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://192.168.40.101:3306/mytest"
    val userName = "root"
    val passWd = "123456"

    //构建数据
    val data: RDD[(String, String)] = sc.parallelize(List(("Tom","22"), ("Jim","27")))

    //可能会报错：  org.apache.spark.SparkException: Task not serializable
    //因为dataRDD的遍历是action算子，会用到conn对象，而conn没有实现序列话
    //
    Class.forName ("com.mysql.jdbc.Driver").newInstance()
    val conn = java.sql.DriverManager.getConnection("jdbc:mysql://192.168.40.101:3306/mytest",
      "root", "123456")
//      data.foreach(data => {
      data.collect().foreach(data => {    //这样在collect的时候把数据收集到一个分区上了
        val ps = conn.prepareStatement("insert into person(pname,page) values (?,?)")
        ps.setString(1, data._1)
        ps.setString(2, data._2)
        ps.executeUpdate()
    })

  }


  def main(args: Array[String]): Unit = {
    write_mysql
   }


}
