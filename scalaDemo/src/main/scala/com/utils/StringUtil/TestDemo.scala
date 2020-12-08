package com.utils.StringUtil

import com.utils.MyStringUtil


object TestDemo {

  def main(args: Array[String]): Unit = {

    //判断是否为空
    val str1= ""
    println(MyStringUtil.isEmpty(str1))

    //字符串当作Map使用
    val str2 = "a=1|b='dsd'|d=8.5"
    println(MyStringUtil.getFieldFromConcatString(str2,"\\|","a"))
    println(MyStringUtil.getFieldFromConcatString(str2,"\\|","b"))
    println(MyStringUtil.getFieldFromConcatString(str2,"\\|","d"))

    println(MyStringUtil.setFieldInConcatString(str2,"\\|","d","2.33"))

  }
}
