package com.gourd.erwa.hadoop.eventcount;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.InputStream;
import java.net.URI;

/**
 * @author wei.Li by 16/1/12
 */
public class Test {
    public static void main(String[] args) throws Exception {

        String uri = "hdfs://localhost:8020/";
        Configuration config = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), config);
        FileSystem[] childFileSystems = fs.getChildFileSystems();

        // 列出hdfs上/user/fkong/目录下的所有文件和目录
        Path f = new Path("/data/hadoop/hadoop-2.6.2/hdfs-my/name/");

        FileStatus[] statuses = fs.listStatus(f);
        for (FileStatus status : statuses) {
            System.out.println(status);
        }

        // 在hdfs的/user/fkong目录下创建一个文件，并写入一行文本
        FSDataOutputStream os = fs.create(new Path("/data/hadoop/hadoop-2.6.2/hdfs-my/name/test.log"));
        os.write("Hello World!".getBytes());
        os.flush();
        os.close();

        // 显示在hdfs的/user/fkong下指定文件的内容
        InputStream is = fs.open(new Path("/user/fkong/data/hadoop/hadoop-2.6.2/hdfs-my/name/test.log"));
        IOUtils.copyBytes(is, System.out, 1024, true);
    }
}
