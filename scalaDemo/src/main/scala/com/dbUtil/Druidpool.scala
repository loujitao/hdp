package com.dbUtil

import com.alibaba.druid.pool.DruidDataSource
import com.config.ConfigHander

/**
  *   数据源工具类模板
  *   1）引入依赖包：   对应版本数据源jar、 数据库驱动jar、
  */

object Druidpool {
 private var source:DruidDataSource = null

  def getPool() = {
    if (source == null) {
      //初始化数据源
      source = new DruidDataSource()
      //设置基本信息
      source.setDriverClassName(ConfigHander.db_driver)
      source.setUsername(ConfigHander.db_user)
      source.setUrl(ConfigHander.db_url)
      source.setPassword(ConfigHander.db_password)
      source.setInitialSize(ConfigHander.db_initialPoolSize)
      source.setMinIdle(1)
      source.setMaxActive(64)
      source.setPoolPreparedStatements(true)
      source.setMaxOpenPreparedStatements(100)
      source.setTestWhileIdle(false)
      source
    }
    else {
      source
    }
  }

}
