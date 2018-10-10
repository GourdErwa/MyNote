#!/usr/bin/env bash

##!不可用写法，无法正确获取返回值
##echo `${command}`  =》通过$?获取执行后的状态结果，该处获取的为 echo 命令执行结果返回值，导致永远为 0

command="
java \
-cp :/lw/workfile/intellij_work/MyNote/shell/target/shell##2018.1_etc_java.jar \
com.groud.erwa.shell.exit.TestNormality
"

echo "command is:$command"

echo `${command}`
#通过$?获取执行后的状态结果，该处获取的为 echo 命令执行结果返回值，导致永远为 0
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
