package com.gourd.erwa.io;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;

/**
 * The type Files utils.
 *
 * @author wei.Li by 14-8-20.
 */
public class FilesUtils {

    private static final String FILEPATH = "/lw/workfile/intellij_work/my_note/src/main/java/com/java/io/json.txt";
    private static final String CONTAINSSTR = "\"size\":20}";

    /**
     * 找出文件中每行不包含 containsStr 字符串的文本
     * 输出到当前目录下,open
     *
     * @param filePath    文件地址
     * @param containsStr 不包含的字符串
     */
    private static void deleteContainsStrForFile(final String filePath, final String containsStr) {
        File file = new File(filePath);
        Writer writer = null;
        try {
            List<String> stringList = Files.readLines(file, Charset.defaultCharset());
            String outPath = file.getName();
            outPath = file.getParent() + System.getProperty("file.separator")
                    + outPath.substring(0, outPath.indexOf(".")) + "_" + System.currentTimeMillis()
                    + outPath.substring(outPath.indexOf("."));
            writer = new FileWriter(new File(outPath));
            for (String s : stringList) {
                if (!s.contains(containsStr)) {
                    writer.append(s).append("\r\n");
                }
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("outPath -> " + outPath);
            //ShellUtil.openFile(outPath);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        deleteContainsStrForFile(FILEPATH, CONTAINSSTR);
    }
}
