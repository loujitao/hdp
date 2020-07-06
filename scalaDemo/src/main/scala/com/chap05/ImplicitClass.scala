package com.chap05
//若一个变量A没有某些方法或者某些变量时，
//而这个变量A可以调用某些方法或者某些变量时，
//可以定义一个隐式类，隐式类中定义这些方法
//或者变量，隐式类中传入A即可。
package test
class Rabbit(s:String){
  val name = s
}

object ImplicitClass {

//  1).隐式类必须定义在类，包对象，伴生对象中。
//  2).隐式类的构造必须只有一个参数，同一个类，包对象，伴生对象中
//     不能出现同类型构造的隐式类。
 implicit class Animal(rabbit:Rabbit){
    val tp = "Animal"
    def canFly() ={
      println(rabbit.name +" can fly...")
    }
  }

  def main(args: Array[String]): Unit = {
    val rabbit = new Rabbit("rabbit")
    rabbit.canFly()
    println(rabbit.tp)
  }
}
