# SpringBoot Netty
基于 SpringBoot 的 Quartz 集群调度程序，集群模式需要基于数据库。

* JDK：1.8+
* SpringBoot：1.5.4.RELEASE
* Quartz：2.2.1

# 集群模式库表创建
如果是基于数据库的集群模式，运行前需要先运行 SQL 创建语句，存在于 Quartz 的 docs\dbTables 目录下，根据不同的数据库类型创建。

* MySQL 创建语句：tables_mysql_innodb.sql

# 测试实例
* 自运行实例：com.iceblock.springboot.quartz.runner.SampleJobRunner
* 开启或者关闭：修改 samplejob.enabled，如果不关闭启动后程序重启仍会继续执行