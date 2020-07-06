package com.chap04

object MapTest  extends App{

  //创建map的方式
  val map = Map( "1" -> "bjsxt",
    2 -> "shsxt", (3,"xasxt")
  )
  val m1=("ds"-> "aaaaa")
  val m2=("2", "aaaaa")

  //获取值
  println(map.get("1").get)
  val result = map.get(8).getOrElse("no value")
  println(result)

  //map遍历
  for(x <- map){
    println("====key:"+x._1+",value:"+x._2)
  }
  map.foreach(f => {
    println("key:"+ f._1+" ,value:"+f._2)
  })

  //遍历key
  val keyIterable = map.keys
  keyIterable.foreach { key => {
    println("key:"+key+", value:"+map.get(key).get)
  } }
  println("---------")
  //遍历value
  val valueIterable = map.values
  valueIterable.foreach { value => {
    println("value: "+ value)
  } }

  //合并map    合并map会将map中的相同key的value替换
//  ++  例：map1.++(map2)   =====    map1中加入map2
//  ++:  例：map1.++:(map2)   =====   map2中加入map1
  val map1 = Map(
    (1,"a"),
    (2,"b"),
    (3,"c")
  )
  val map2 = Map(
    (1,"aa"),
    (2,"bb"),
    (2,90),
    (4,22),
    (4,"dd")
  )
  map1.++:(map2).foreach(println)

  /**
    * map方法
    */
  //count    统计符合条件的记录数
  val countResult  = map.count(p => {
    p._2.equals("shsxt")
  })
  println(countResult)
  //filter     过滤，留下符合条件的记录
  map.filter(_._2.equals("shsxt")).foreach(println)
  //contains     map中是否包含某个key
  println(map.contains(2))
  //exist      符合条件的记录存在不存在
  println(map.exists(f =>{
    f._2.equals("xasxt")
  }))

  /**
    * 可变长Map
    */
  import scala.collection.mutable.Map
  val map11 = Map[String,Int]()
  map11.put("hello",100)
  map11.put("world",200)
  map11.foreach(println)


}
