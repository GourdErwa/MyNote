#!/usr/bin/env bash

################## 版本管理 ##################

# version 为更新版本号 .DEV 为开发版本 .RELEASE 为发布版本 .BETA 为测试版本
mvn clean versions:set -DnewVersion=version

#你对所做的更改满意进行提交
mvn versions:commit

#不满意的话也可以使用 mvn versions:revert 进行撤销
mvn versions:revert


################## pom 依赖查询 ##################

#用来查看当前工程的完整的pom文件, 比如从父类pom以及默认pom继承的内容
mvn help:effective-pom

#查看项目依赖情况
mvn dependency:resolve

#打印出项目的整个依赖树
mvn dependency:tree

#帮助你分析依赖关系, 用来取出无用, 重复依赖的好帮手
mvn dependency:analyze

#追踪依赖的完整轨迹
mvn install -X
