package com.chap03

/**
  *1、scala object相当于Java的单例，object中定义的全是静态的；
  *         object不可以传参，传参使用apply方法
  * 2、定义变量使用var，常量使用val，变量可以改变，常量不能改变；
  *         可以推断出类型的，可以不需指明类型
  * 3、一条语句一行时，句尾的分号可以省略
  * 4、Scala中建议使用驼峰命名法
  * 5、scala类中可以传参，参数一定要指定类型；类自带默认构造器；
  *         类中的属性默认有getter和setter方法
  * 6、类中重写构造时，第一行必须先调用默认构造
  * 7、Scala中当new class时， 除了非构造方法外，依次从上到下执行
  * 8、在同一个Scala文件中，class名称和object名称一致时，这个类叫做对象的伴生类，
  *       这个对象叫做类的伴生对象，它们之间可以互相访问私有属性。
  */
class  ScalaClass(name:String , age:Int){

    private val sName=name
    var sAge=age
    var sNo=1

  println("---------- in ScalaClass  class-----------")
  //重写构造
    def this(name:String , age:Int, No: Int){
      this(name:String , age:Int)
      sNo=No
    }
}

object ScalaClass {

  val number1=100  //可以推断出类型的，可以不需指明类型
  val str1:String ="ssss"

  println("=======  in  ScalaClass  object========")

  def main(args: Array[String]): Unit = {

    ScalaClass("zhangsan",12)
    val sc=new ScalaClass("lisi",23)
    println(sc.sName)
  }

  def apply(name: String ,age: Int): Unit ={
    println("ScalaClass name="+name+",age="+age)
  }
  def apply(name: String ,age: Int,sex: Char): Unit ={
    println("ScalaClass name="+name+",age="+age+",char="+sex)
  }

}
