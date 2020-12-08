package com.dbUtil

import org.apache.commons.dbutils.QueryRunner

object ResultInsert {
  private val runner = new QueryRunner(Druidpool.getPool())
def insert4[T](iterator: Iterator[(String,String,String,T)]): Unit ={

  val params = iterator.map(line=>Array(line._1,line._2,line._3,line._4.asInstanceOf[AnyRef])).toArray
  val sql = "INSERT INTO BIGDATA_GATHERCOUNTRESULT VALUES (?,?,?,?)"
  runner.batch(sql,params)
  }
  def insert3[T](iterator: Iterator[(String,String,T)]): Unit ={
    insert4[T](iterator.map(line=>(line._1,line._2,"",line._3)))
  }
def deleteTable()={
  val sql = "delete from BIGDATA_GATHERCOUNTRESULT"
  runner.update(sql)
}
}
