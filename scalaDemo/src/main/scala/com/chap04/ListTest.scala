package com.chap04


object ListTest  extends App{
  //创建
  val list = List(1,2,3,4,5)
  //Nil是长度为0的list

  //遍历
  list.foreach { x => println(x)}
  //    list.foreach { println}

  //filter  过滤元素
//  val list1  = list.filter { x => x>3 }
  val list1  = list.filter(_>3 )
  list1.foreach { println}

  //count    计算符合条件的元素个数
  val value = list1.count { x => x>3 }
  println(value)

  //map   对元素操作
  val nameList = List(
    "hello bjsxt",
    "hello xasxt",
    "hello shsxt"
  )
  val mapResult:List[Array[String]] = nameList.map{ x => x.split(" ") }
  mapResult.foreach{println}

  //flatmap   压扁扁平,先map再flat
  val flatMapResult : List[String] = nameList.flatMap{ x => x.split(" ") }
  flatMapResult.foreach { println }



  /**
    * 可变长list
    */
  import scala.collection.mutable.ListBuffer
  val listBuffer: ListBuffer[Int] = ListBuffer[Int](1,2,3,4,5)
  listBuffer.append(6,7,8,9)//追加元素
  listBuffer.+=(10)//在后面追加元素
  listBuffer.+=:(100)//在开头加入元素
  listBuffer.foreach(println)
}
