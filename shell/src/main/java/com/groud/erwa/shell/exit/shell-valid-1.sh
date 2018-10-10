#!/usr/bin/env bash

##可用写法

java \
-cp :/lw/workfile/intellij_work/MyNote/shell/target/shell##2018.1_etc_java.jar \
com.groud.erwa.shell.exit.TestNormality

#通过$?获取执行后的状态结果
exitCode=$?

echo "shell invoke java exitCode:" ${exitCode}

if [ ${exitCode} == 0 ]
    then
    echo "shell invoke exitCode==0 , exit 0"
    exit 0
    else
    echo "shell invoke exitCode!=0 , exit 1"
    exit 1
fi
