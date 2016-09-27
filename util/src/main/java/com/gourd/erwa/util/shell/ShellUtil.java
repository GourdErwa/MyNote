package com.gourd.erwa.util.shell;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * The type Shell util.
 *
 * @author wei.Li by 14-8-25.
 */
public class ShellUtil {

    /**
     * The constant ILLEGAL_LETTERS.
     */
    private static final String[] ILLEGAL_LETTERS = new String[]{"rm", "ll"};
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ShellUtil.class);

    /**
     * 校验字符非法性
     *
     * @param arg 校验的字符
     * @return 含有非法字符则返回 true
     */
    private static boolean checkInspectionLegal(String... arg) {

        for (String s : arg) {
            for (String letter : ILLEGAL_LETTERS) {
                if (StringUtils.equals(s, letter)) {
                    LOGGER.error("checkInspectionLegal arg have illegal letters <{}> . return false .", s);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * shell打开文件或者目录
     *
     * @param filePath 文件或者目录地址
     */
    public static void openFile(String filePath) {

        if (checkInspectionLegal(filePath))
            return;
        try {

            Runtime runtime = Runtime.getRuntime();
            runtime.exec("open " + filePath);

        } catch (IOException e) {
            LOGGER.error("openFile error . <{}>", e.getMessage());
        }
    }

    /**
     * 执行 com.gourd.erwa.shell
     *
     * @param s the s
     */
    private static void runShell(String s) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(s);
            InputStream inputStream = process.getInputStream();
            byte b[] = new byte[1024];
            int len = 0;
            int temp;          //所有读取的内容都使用temp接收
            while ((temp = inputStream.read()) != -1) {    //当没有读取完时，继续读取
                b[len] = (byte) temp;
                len++;
            }
            inputStream.close();
            System.out.println(new String(b, 0, len));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        //openFile("/lw");
        runShell("free");
    }
}
