package com.steve.main

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd._
import scala.collection.immutable.Map
/**
  * ALS算法做电影推荐 案例
  *
  * 数据来源：Movielens   ml-100k推荐数据
  * http://grouplens.org/datasets/movielens
  */
object ALSRecommend {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(
              new SparkConf()
                .setAppName("ALSRecommend")
                .setMaster("local[4]"))

    //1、创建用户评分数据
    //原始数据 196	242	3	881250949
    val rawUserData=sc.textFile("E:\\ideawork\\hdp\\aclRecommend\\data\\u.data")
    val rawRatings= rawUserData.map(_.split("\t").take(3))
    val ratingsRDD: RDD[Rating] = rawRatings.map {
      case Array(user, movie, rating) => Rating(user.toInt, movie.toInt, rating.toDouble)
    }
    println("共计："+ratingsRDD.count.toString()+"条ratings")

    //2、创建电影ID和名称对照表
    //原始数据： 1|Toy Story (1995)|01-Jan-1995||http://us.imdb.com/M/title-exact?Toy%20Story%20(1995)|0|0|0|1|1|1|0|0|0|0|0|0|0|0|0|0|0|0|0
    val itemRDD = sc.textFile("E:\\ideawork\\hdp\\aclRecommend\\data\\u.item")
    //    （1，Toy Story (1995)）
    val movieTitle=itemRDD.map( line => line.split("\\|").take(2) )
            .map(array => (array(0).toInt, array(1)))
              .collect().toMap
    //3、训练产生模型
    val model =ALS.train(ratingsRDD,5,25,0.1)
    //4、进行推荐阶段
    recommend(model,movieTitle)
  }



  def RecommendMovies(model: MatrixFactorizationModel, movieTitle: Map[Int, String], inputUserId: Int) = {
      //针对inputUserId用户推荐前十部电影
      val recommendMovies = model.recommendProducts(inputUserId,10)
      var i = 1
      print("给用户id"+inputUserId+"推荐下列电影：")
      recommendMovies.foreach{ r =>
        println(i.toString+"."+movieTitle(r.product)+" 评分:"+r.rating.toString)
        i +=1
      }
  }

  def RecommondUsers(model: MatrixFactorizationModel, movieTitle: Map[Int, String], toInt: Int) = {
    //针对toInt电影推荐前十位用户
      val recommendUser = model.recommendUsers(toInt,10)
      var i = 1
      print("给电影id"+toInt+"推荐下列用户：")
      recommendUser.foreach{ m =>
        println(i.toString+"用户id"+m.user +" 评分:"+m.rating)
      }
  }

  def recommend(model: MatrixFactorizationModel, movieTitle:Map[Int,String]): Unit ={
      var choose = ""
      while (choose != "N"){  //3，结束程序
        print("请选择要推荐类型 1.针对用户推荐电影；2.针对电影推荐给感兴趣得用户； N.离开")
        import java.io.BufferedReader
        import java.io.InputStreamReader
        val br = new BufferedReader(new InputStreamReader(System.in))
        val choose = br.readLine         //读取用户输入
        if(choose == "1"){
          print("请输入用户 id： ")
          val br1 = new BufferedReader(new InputStreamReader(System.in))
          val inputUserID =  br1.readLine
          RecommendMovies(model,movieTitle,inputUserID.toInt);  //针对用户推荐电影
        }else if (choose == "2"){
          print("请输入电影的id: ")
          val br2 = new BufferedReader(new InputStreamReader(System.in))
          val inputMovieID =  br2.readLine
          RecommondUsers(model,movieTitle,inputMovieID.toInt) //针对电影筛选推荐用户
        }
      }

  }


//  def prepareData():(RDD[Rating],Map[Int,String])={
//    //1.创建用户评分数据
//    //2.创建电影ID与名称对照表
//    //3.显示数据记录数
//    -
//  }


}
