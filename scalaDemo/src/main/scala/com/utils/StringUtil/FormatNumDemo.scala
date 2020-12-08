package com.utils.StringUtil

/**
  * 字符串格式化实例集合
  *
  */
object FormatNumDemo {

  def main(args: Array[String]): Unit = {

    val str1 = "%1$s-%2$s-%3$s".format("spark","scala","ml")
    println(str1) //    =>    spark-scala-ml

    val str2 = "%d%%".format(86)
    println(str2)   // 数字百分比格式化 =>  86%

    val str3 = "%8.3f".format(11.56789)
    println(str3)   // 精度为3，长度为8  =>  "  11.568"

    val str4 = "%08.3f".format(11.56789)
    println(str4)   // 精度为3，长度为8，不足的用0填补  => "0011.568"

    val str5 = "%09d".format(11)
    println(str5)   // 长度为9，不足的用0填补  => 000000011

    val str6 = "%.2f".format(11.23456)
    println(str6)   // 小数点后保留2位   =>   11.23

    val wx:Double =36.6789
    val str7 = f"${wx}%.2f"
    println(str7)    // 小数点后保留2位   =>   36.67

  }
}
