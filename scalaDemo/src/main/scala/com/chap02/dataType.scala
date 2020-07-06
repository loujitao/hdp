package com.chap02

//值类型数据
object dataType {
  def main(args: Array[String]): Unit = {
    // 1) 整型： Byte  Short  Int  Long
    val a : Byte = 12
    val b : Short = 128
    val c : Int = 10000
    val c2 = 10000  //没有指明类型的数字，默认是Int
    val d : Long = 999999
    val d2 = 9999999L

  //2) 浮点类型 ：  Float  Double
    var  f1: Float =1.1f
        f1=1.2f
    var double1 = 1.2    //默认类型是Double
    var  double2: Double= 1.3
    var  double3 :Double = 1.2f

  //3)字符类型  Char
    var c1: Char = 'a'
    var cc: Char='\n'
    var c3: Char= '中'
    var c4: Char= 97

  //4） 布尔类型   Boolean
    val bool= true
    val flag= false

  //Unit  Null  Nothing
  //Unit只有一个实例值，写成()。
//    Unit类型用来标识过程，也就是没有明确返回值的函数。
//    由此可见，Unit类似于Java里的void。Unit只有一个实例: ()，
//    这个实例也没有实质的意义

  //Null 类型只有一个实例值 null
//    null可以赋值给任意引用类型(AnyRef)，但是不能赋值
// 给值类型(AnyVal: 比如 Int, Float, Char, Boolean, Long, Double, Byte, Short)

  //Nothing类型在Scala的类层级的最低端；它是任何其他类型的子类型。
//    Nothing，可以作为没有正常返回值的方法的返回类型，
//    非常直观的告诉你这个方法不会正常返回，而且由于Nothing是其他任意类型的子类，
//    他还能跟要求返回值的方法兼容。



  }

}
