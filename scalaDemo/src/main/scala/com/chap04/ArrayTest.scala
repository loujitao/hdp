package com.chap04



object ArrayTest  extends  App{

/**
  * 创建数组两种方式：
  * 1.new Array[String](3)
  * 2.直接Array
*/
  //创建类型为Int 长度为3的数组
  val arr1 = new Array[Int](3)
  //创建String 类型的数组，直接赋值
  val arr2 = Array[String]("s100","s200","s300")
  //赋值
  arr1(0) = 100
  arr1(1) = 200
  arr1(2) = 300
  val array1=Array(1,2,3,4);
  array1.foreach(println)

  /**
    * 遍历两种方式
    */
  for(i <- arr1){
    println(i)
  }
  arr1.foreach(i => {
    println(i)
  })

  /**
    * 创建二维数组和遍历
    */
  val arr4 = Array[Array[Int]](Array(1,2,3),Array(4,5,6))

  val arr3 = new Array[Array[String]](3)
  arr3(0)=Array("1","2","3")
  arr3(1)=Array("4","5","6")
  arr3(2)=Array("7","8","9")

  for(i <- 0 until arr3.length){
    for(j <- 0 until arr3(i).length){
      print(arr3(i)(j)+"	")
    }
    println()
  }



  /**
    * 可变长度数组的定义
    */
  import scala.collection.mutable.ArrayBuffer
  val arr = ArrayBuffer[String]("a","b","c")
  arr.append("hello","scala")//添加多个元素
  arr.+=("end")//在最后追加元素
  arr.+=:("start")//在开头添加元素
  arr.foreach(println)
}
