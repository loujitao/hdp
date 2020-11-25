package com.steve.main

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.jfree.data.category.DefaultCategoryDataset

/**
  * ALS交替最小二乘法参数调整
  *
  * 数据来源：Movielens   ml-100k推荐数据
  * http://grouplens.org/datasets/movielens
  */

object ALSparamsAdjust {

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(
      new SparkConf()
        .setAppName("ALSparamsAdjust")
        .setMaster("local[*]"))

    println("====== 数据准备阶段 =========")
    val (trainData, validationData,testData) = PrepareData(sc)
    trainData.persist()
    validationData.persist()
    testData.persist()

    println("====== 训练验证阶段 =========")
    val bestModel = trainValidation(trainData,validationData)
    //测试结果：
    // 最佳参数组合：rank :5 ,validations: 25 ,lambda: 0.1 ,RMSE: 0.9221906935060521

    println("====== 测试阶段 =========")
    //均方根误差计算
    val testRmse = computeRmse(bestModel, testData)
    println("RMSE : "+testRmse)
  }

  def PrepareData(sc:SparkContext): (RDD[Rating], RDD[Rating], RDD[Rating] ) ={
    //1、 创建用户评分数据
    val rawUserData=sc.textFile("E:\\ideawork\\hdp\\aclRecommend\\data\\u.data")
    val rawRatings= rawUserData.map(_.split("\t").take(3))
    val ratingsRDD: RDD[Rating] = rawRatings.map {
      case Array(user, movie, rating) => Rating(user.toInt, movie.toInt, rating.toDouble)
    }
    //2、 创建电影ID与名称对照表
    val itemRDD = sc.textFile("E:\\ideawork\\hdp\\aclRecommend\\data\\u.item")
    val movieTitle=itemRDD.map( line => line.split("\\|").take(2) )
      .map(array => (array(0).toInt, array(1)))
      .collect().toMap
    //3、 显示数据记录数
    //4、 以随机方式将数据分为 3个部分并且返回
   val Array(trainData, validationData,testData) = ratingsRDD.randomSplit(Array(0.8, 0.1, 0.1))
    return (trainData, validationData,testData)
  }

  def trainValidation(trainData:RDD[Rating], validationData:RDD[Rating] ):MatrixFactorizationModel ={
    println("------- 评估特征维度rank参数使用 ----------")
//    evaluateParameter(trainData, validationData, "rank", Array(5, 10 ,15, 20, 50, 100), Array(10), Array(0.1))

    println("------- 评估迭代次数numIterations参数使用 ----------")
//    evaluateParameter(trainData, validationData, "numIterations", Array(10), Array(5, 10 ,15, 20,25), Array(0.1))

    println("------- 评估正则化系数lamda参数使用 ----------")
//    evaluateParameter(trainData, validationData, "lambda", Array(10), Array(10), Array(0.05, 0.1, 1, 5, 10.0))

    println("------- 所有参数交叉评估找出最好的组合 ----------")
   val  bestModel= evaluateAllParameter(trainData, validationData, Array(5, 10 ,15, 20, 25), Array(5, 10 ,15, 20, 25), Array(0.05, 0.1, 1, 5, 10.0))

    return bestModel
  }

  def evaluateParameter(trainData:RDD[Rating], validationData:RDD[Rating],
                        evalParam:String, ranks:Array[Int], numIterations:Array[Int],lambdas:Array[Double])={
      for(rank<- ranks; iterateNum<- numIterations;lambda<- lambdas){
        val rmse = trainModel(trainData,validationData,rank,iterateNum,lambda)
        println("rank: "+rank+" iterateNum: "+iterateNum+" lambda: "+lambda+" ==>RMSE : "+rmse)
    }
  }

  def trainModel(trainData: RDD[Rating], validationData: RDD[Rating],
                 rank: Int, iterater: Int, lambda: Double):(Double)={
    val model = ALS.train(trainData,rank,iterater,lambda)
    val rmse = computeRmse(model,validationData)
    return  rmse
  }


  def computeRmse(model: MatrixFactorizationModel, ratingRDD: RDD[Rating]):Double={
    val num = ratingRDD.count()
    val userMovie = ratingRDD.map{
      case Rating(user,movie,rate) => (user,movie)
    }
    val predictedRDD = model.predict(userMovie).map{
      case Rating(user, movie, rate) => ( (user, movie), rate)
    }
    val predictedAndRatings = ratingRDD.map{
      case Rating(user, movie, rate) => ( (user, movie), rate)
    }.join(predictedRDD)
      .values

    math.sqrt( predictedAndRatings.map{
      case (actValue, preValue) =>  (actValue - preValue) * (actValue - preValue)
    }.reduce(_+_)/num
    )
  }

  def evaluateAllParameter(trainData: RDD[Rating], validationData: RDD[Rating],
                           ranks: Array[Int], iterators: Array[Int], lambdas: Array[Double]):MatrixFactorizationModel={
    val evaluations =
      for(rank <- ranks; iterNum<- iterators; lambda<- lambdas)
        yield {
          val rmse = trainModel(trainData,validationData,rank,iterNum,lambda)
          (rank,iterNum,lambda,rmse)
        }

    val eval = evaluations.sortBy(_._4)
    val bestEval = eval(0)
    println("最佳参数组合：rank :"+bestEval._1+" ,validations: "+bestEval._2+" ,lambda: "+ bestEval._3+" ,RMSE: "+bestEval._4 )
    val bestModel = ALS.train(trainData,bestEval._1,bestEval._2,bestEval._3)
    (bestModel)
  }


}
