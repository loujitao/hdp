package com.chap03

import java.util.Date


/**
  * 方法定义语法 用def来定义
  * 可以定义传入的参数，要指定传入参数的类型
  * 方法可以写返回值的类型也可以不写，会自动推断。递归方法不能省返回值类型
  * scala中方法有返回值时，可以写return，也可以不写return，
  *     会把方法中最后一行当做结果返回。
  * 如果返回值可以一行搞定，可以将{}省略不写
  * 传递给方法的参数可以在方法中使用，并且scala规定
  *     方法传过来的参数为val的，不是var的。
  * 如果去掉方法体前面的等号，那么这个方法返回类型必定是Unit的。
  */
object functionDemo {

  //有参方法
  def say(word: String):String ={
    return  word +"...."
  }
  //无参方法
  def playing(): Unit ={
    println("playing....")
  }
  //递归方法  某个数的阶乘。 返回值类型Int不能省略，否则报错
  def fun1(num: Int):Int={
    if(num<=1){
      1
    }else{
      num * fun1(num-1)
    }
  }
  //参数有默认值的方法
  def addNum(a:Int=1,b:Int=10):Int={
      a+b
  }
// 可变参数的方法
  def fun2(arg:String*): Unit ={
//    for(elem <- arg){
//      println(elem)
//    }
    arg.foreach(println)
  }

  //==========匿名函数============
  //1、有参匿名函数
  val f1=(a:Int)=>{
    println(a)
  }
  //2、无参匿名函数
  val f2=()=>{
    println("f2 function")
  }
  //3、有返回值的匿名函数
  val f3=(a:Int,b:Int)=>{
       a+b
  }

  //嵌套方法
  def fun3(x:Int) ={
    def fun2(y:Int):Int={
      x*y
    }
    fun2 _
  }
//  偏应用函数是一种表达式，不需要提供函数需要的所有参数，
//  只需要提供部分，或不提供所需参数。
/**
*    偏应用函数
*/
  def log(date :Date, s :String)= {
    println("date is "+ date +",log is "+ s)
  }
  val date = new Date()
  log(date ,"log1")
  log(date ,"log2")
  log(date ,"log3")
  //想要调用log，以上变化的是第二个参数，可以用偏应用函数处理
  val logWithDate = log(date,_:String)
  logWithDate("log11")
  logWithDate("log22")
  logWithDate("log33")




  def main(args: Array[String]): Unit = {
    //测试递归方法
    println(fun1(5))

    //测试有默认值方法
    println(addNum())  //使用默认值1，10
    println(addNum(5))  //使用默认值b=10
    println(addNum(3,3))  //使用传入值
    println(addNum(b=3))  //使用默认值a=1

    //测试可变参数方法
    fun2("1","a","A")

    //测试匿名函数
    f1(100)
    f2()
    println(f3(4,5))

    println(fun3(4)(3))
  }

}
