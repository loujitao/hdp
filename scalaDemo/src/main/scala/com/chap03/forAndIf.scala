package com.chap03

object forAndIf {

  def main(args: Array[String]): Unit = {
    //=============1、if 单分支，多分支，嵌套
    val i=12
    if(i>13){
      println(i)
    }

    if(i<20){
      println(i)
    }else{
      println(20)
    }

    //============2、循环   while   do....while  for  foreach
//    println( 1.to(10))
    println( 1 to 10)     //包区间
    println( 1 to (10,2))  //包区间，步长为2

    println( 1 until  10)     //半开区间
    println( 1 until  (10,3))  //半开区间，步长为3

    //一般结构
//    for(i <- 1 to 10 ){
//      println(i)
//    }

    //守卫模式
//    for(i <- 1 to 10  if(i%2==0)){
//      println(i)
//    }

    //守卫模式 结果转换   yield后面跟相应的操作表达式
        val seq = for(i <- 1 to 10  if(i%2==0) ) yield i
        println(seq)  //结果为转换后的Vector

    //双重for嵌套
//    for(i <- 1 to 9; j<- 1 to 9){
//      if(i>=j){
//        print(s"$i * $j ="+(i*j)+"\t")
//      }
//      if(i==j){
//        println()
//      }
//    }

    //foreach遍历
    val arr=1 to 5
    arr.foreach(println)

    /**
      * while 循环
      */
    var index = 0
    while(index < 10 ){
      println("第"+index+"次while 循环")
      index += 1
    }
    index = 0
    do{
      index +=1
      println("第"+index+"次do while 循环")
    }while(index <10 )

  }
}
