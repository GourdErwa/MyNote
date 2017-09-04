package com.gourd.erwa.hadoop.userbasestation;

import com.gourd.erwa.hadoop.PathConstant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author wei.Li by 2017/3/31
 */
public class OperatorsUserBaseStation {


    /*
     原始数据分为位置和网络两种
     位置数据格式为：
     用户标识 设备标识 开关机信息 基站位置 通讯的时间
     example:
     0000009999 0054785806 3 00000089 2016-02-21 21:55:37

     网络数据格式为：
     用户标识 设备标识 基站位置 通讯的时间 访问的URL
     example:
     0000000999 0054776806 00000109 2016-02-21 23:35:18 www.baidu.com

     需要得到的数据格式为：
     用户标识 时段 基站位置 停留时间

     example:
     00001 09-18 00003 15
     用户00001在09-18点这个时间段在基站00003停留了15分钟

     两个reducer：

     1.统计每个用户在不同时段中各个基站的停留时间
     2.在1的结果上只保留停留时间最长的基站位置信息
     */


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {

        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 1) {
            System.err.println("Usage: OperatorsUserBaseStation <HDFS-host>");
            System.exit(1);
        }

        String host = otherArgs[0];
        String inPathStr = PathConstant.getHDFSDirectory(host, PathConstant.OPERATORS_USER_BASE_STATION_IN);
        String outPathStr = PathConstant.getHDFSDirectory(host, PathConstant.OPERATORS_USER_BASE_STATION_OUT);


        Job job = Job.getInstance(conf, "OperatorsUserBaseStation");

        job.setJarByClass(OperatorsUserBaseStation.class);

        job.setMapperClass(MyMapper.class);

        job.setCombinerClass(MyReducer.class);
        job.setReducerClass(MyReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        final Path inPath = new Path(inPathStr);
        FileInputFormat.addInputPath(job, inPath);
        final Path outPath = new Path(outPathStr);
        FileOutputFormat.setOutputPath(job, outPath);

        //删除历史输出目录
        FileSystem fileSystem = FileSystem.get(new URI(inPathStr), new Configuration());
        if (fileSystem.exists(outPath)) {
            fileSystem.delete(outPath, true);
        }

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class MyMapper extends Mapper<LongWritable, Text, Text, Text> {

        private boolean posTypeFile = false;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);

            //获取输入文件名称
            final FileSplit fileSplit = ((FileSplit) context.getInputSplit());
            final String name = fileSplit.getPath().getName();
            this.posTypeFile = name.startsWith("pos");
        }

        @Override
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {

            LineData lineData = new LineData();

            try {
                lineData.format(value.toString(), this.posTypeFile);
            } catch (LineException e) {
                e.printStackTrace();
            }
            context.write(lineData.outKey(), lineData.outValue());
        }
    }


    public static class MyReducer extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
        }

        @Override
        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            context.write(key, new Text(values.spliterator().estimateSize() + ""));
        }
    }

}
