package com.chap04

object TupleTest extends App{

  //创建，最多支持22个
  val tuple = new Tuple1(1)
  val tuple2 = Tuple2("zhangsan",2)
  val tuple3 = Tuple3(1,2,3)
  val tuple4 = (1,2,3,4)
  val tuple18 = Tuple18(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18)
  val tuple22 = new Tuple22(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22)

  //使用   取值用”._XX” 可以获取元组中的值
  println(tuple2._1 + "\t"+tuple2._2)
  val t = Tuple2((1,2),("zhangsan","lisi"))
  println(t._1._2)

  //遍历   tuple.productIterator得到迭代器，进而遍历
  val tupleIterator = tuple22.productIterator
  while(tupleIterator.hasNext){
    println(tupleIterator.next())
  }

  /**
    * 方法
    */
  //翻转，只针对二元组
  println(tuple2.swap)
  //toString
  println(tuple3.toString())

}
