package com.steve.df

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SparkSession}

//创建DataFrame三种方式：
//1、通过spark数据源进行创建
//2、从一个存在得RDD转化
//3、从Hive Table进行查询返回
object RDDtoDF {

  case class People(name:String, age:Int)

  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设置部署模式为  local 和 应用名称
    val conf = new SparkConf()
    conf.setMaster("local[3]").setAppName("testRDD")

    val spark: SparkSession = SparkSession
      .builder()
      .appName("tesst")
      .config(conf)
      .getOrCreate()

    //获取spark上下文对象
    val sc: SparkContext = spark.sparkContext
    val peopleRDD: RDD[String] = sc.textFile("sparkSQLDemo/input/people.txt")
    peopleRDD.collect().foreach(println)

    //隐式转化，需要导入。  spark是上面SparkSession对象得名称
    import spark.implicits._
    //方式一： 手动转换，设置映射字段
//    val peopleDF: DataFrame = peopleRDD.map {
//      x => val para = x.split(","); (para(0), para(1).trim.toInt)
//    }.toDF("name", "age")
//    peopleDF.show()

    //方式二： 通过反射（需要样例类）
//    case class People(name:String, age:Int)  //样例类必须放到方法作用域外面
    val value: RDD[People] = peopleRDD.map {
      x => val para = x.split(","); People(para(0), para(1).trim.toInt)
    }
    val people2DF: DataFrame = value.toDF()
    people2DF.show()


  }
}
