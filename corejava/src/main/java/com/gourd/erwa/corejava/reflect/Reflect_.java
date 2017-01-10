/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * CSDN  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 *
 * Personal home page: http://grouderwa.com
 */

package com.gourd.erwa.util.corejava.reflect;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.Li by 15/3/19 (gourderwa@163.com).
 */
public class Reflect_ {

    //java 文件所在目录地址
    public static final String JAVA_FILE_PATH = "/Users/lw/Downloads/IcEtcCarFileItem.java";

    //字段注解名称
    public static final String[] ANNOTATION_NAMES = new String[]{"@CChar"};

    //结果集
    public static final List<String> result = new ArrayList<>();

    //valueSum
    public static int valueSum = 0;

    public static void main(String[] args) {

        readJavaFile();

    }

    /**
     * 读取 java 文件进行处理
     */
    private static void readJavaFile() {

        File file = new File(JAVA_FILE_PATH);

        if (file.exists()) {

            final List<String> stringList = fileExistsHandle(file);
            try {
                analyzier(stringList);
                printResult();
            } catch (Exception e) {
                System.err.println("分析文件内容出错,error : " + e.getMessage());
            }

        } else {
            System.out.println(String.format("文件不存在 [%s]", JAVA_FILE_PATH));
        }
    }

    /**
     * 文件存在情况下读取所有字段
     *
     * @param file file
     * @return all readLine
     */
    private static List<String> fileExistsHandle(File file) {

        List<String> stringList = new ArrayList<>();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String readLine;

            while ((readLine = bufferedReader.readLine()) != null) {
                stringList.add(readLine.trim());
            }

        } catch (FileNotFoundException e) {
            System.err.println("文件不存在, error :" + e.getMessage());
        } catch (IOException e) {
            System.err.println("文件读取过程错误, error :" + e.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.err.println("输入流关闭过程错误, error :" + e.getMessage());
                }
            }
        }
        return stringList;
    }

    /*
     *
     * @CChar(value = 12, order = 90)
     * public String org;
     */

    /**
     * 分析文件内容 提取数据
     *
     * @param stringList 文件内容
     */
    private static void analyzier(List<String> stringList) throws Exception {

        final int allLine = stringList.size();
        loop_all:
        for (int i = 0; i < allLine; i++) {
            String line = stringList.get(i);

            //匹配注解
            for (String annotationName : ANNOTATION_NAMES) {
                if (line.startsWith(annotationName)) {
                    //提取注解内容
                    final String[] splitAnnotationValues = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"))
                            .split(",");
                    for (String aAnnotationVal : splitAnnotationValues) {
                        final String trim = aAnnotationVal.trim();
                        if (trim.startsWith("value")) {
                            final String s2 = stringList.get(++i);
                            final String fieldName = s2.substring(s2.lastIndexOf(" ") + Integer.SIZE, s2.lastIndexOf(";"));
                            final String value = trim.split("=")[1].trim();
                            valueSum += Integer.parseInt(value);

                            //收集匹配的结果
                            result.add(fieldName + ":" + aAnnotationVal);
                            continue loop_all;
                        }
                    }
                    //匹配某个注解后直接跳出
                    continue loop_all;
                }
            }
        }
    }

    /**
     * 打印结果集
     */
    private static void printResult() {

        result.forEach(System.out::println);

        System.out.println("valueSum = " + valueSum);

    }

}
