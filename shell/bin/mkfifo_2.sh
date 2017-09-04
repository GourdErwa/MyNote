#!/usr/bin/env bash

tmp_fifo_file="/tmp/$$.fifo"
thread=6

mkfifo ${tmp_fifo_file}      # 新建一个fifo类型的文件
exec 6<>${tmp_fifo_file}
# 将fd6指向fifo类型，如果没有这句，在向文件$tmp_fifo_file或者&6写入数据时，程序会被阻塞，直到有read读出了管道中的数据位置，
# 而执行了上述之后，可以在程序运行期间不断向fifo类型文件写入数据而不会阻塞，并且数据会被保存下来以供read程序读出。
# 事实上就是在fd6中放置了$thread个回车符

for ((i=0;i<${thread};i++));
do
bash管道echo
bash管道done >&6
done
