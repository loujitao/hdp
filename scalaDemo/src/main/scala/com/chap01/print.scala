package com.chap01

object print {

  def main(args: Array[String]): Unit = {
    val name="zhangsan"
    val age=1
    val url="www.aaaa.com"
    //字符串通过+号连接（类似java）
    println("name=" + name + " age=" + age + " url=" + url)

    //  printf用法 （类似C语言）字符串通过 % 传值。(格式化输出)
    printf("name=%s, age=%d, url=%s \n", name, age, url)

    //  字符串插值：通过$引用(类似PHP）
    println(s"name=$name, age=$age, url=$url")
  }

}
