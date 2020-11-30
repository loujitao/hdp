package com.chap03
/**
  * 高阶函数
  * 函数的参数是函数
  * 函数的返回是函数
  * 函数的参数和返回都是函数
  */
object func2Demo  extends App{
  //函数的参数是函数
  def hightFun(f : (Int,Int) =>Int, a:Int ) : Int = {
    f(a,100)
  }

  def f(v1 :Int,v2: Int):Int  = {
    v1+v2
  }

  println(hightFun(f, 1))


  //函数的返回是函数
  //1，2,3,4相加
  def hightFun2(a : Int,b:Int) : (Int,Int)=>Int = {
    def f2 (v1: Int,v2:Int) :Int = {
      v1+v2+a+b
    }
    f2
  }
  println(hightFun2(1,2)(3,4))

  //函数的参数是函数，函数的返回是函数
  def hightFun3(f : (Int ,Int) => Int) : (Int,Int) => Int = {
    f
  }
  println(hightFun3(f)(100,200))
  println(hightFun3((a,b) =>{a+b})(200,200))
  //以上这句话还可以写成这样
  //如果函数的参数在方法体中只使用了一次 那么可以写成_表示
  println(hightFun3(_+_)(200,200))



  /**
    * 柯里化函数
    */
  def fun7(a :Int,b:Int)(c:Int,d:Int) = {
    a+b+c+d
  }
  println(fun7(1,2)(3,4))

  //隐式参数传入
  implicit val a = 10
  def fun8(b:Int)(implicit a:Int): Int ={
    b + a
  }
  println(fun8(9))


}
