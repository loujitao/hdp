package com.chap05

trait Read {
  val readType = "Read"
  val gender = "m"
  def read(name:String){
    println(name+" is reading")
  }
}

trait Listen {
  val listenType = "Listen"
  val gender = "m"
  def listen(name:String){
    println(name + " is listenning")
  }
}

class Person() extends Read with Listen{
//  继承的多个trait中如果有同名的方法和属性，必须要在类中使用“override”重新定义。
  override val gender = "f"
}


object TraitTest {

  def main(args: Array[String]): Unit = {
    val person = new Person()
    person.read("zhangsan")
    person.listen("lisi")
    println(person.listenType)
    println(person.readType)
    println(person.gender)
  }
}
