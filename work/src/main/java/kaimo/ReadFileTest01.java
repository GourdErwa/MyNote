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

package kaimo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 按规则解析
 * resource#cache.txt 文件内容
 *
 * @author wei.Li by 15/8/10
 */
public class ReadFileTest01 {

    private static final String READ_FILE_PATH = "/cache.txt";

    public static void main(String[] args) {

        final URL resource = ReadFileTest01.class.getResource(READ_FILE_PATH);
        BufferedReader bufferedReader = null;

        try {
            /**
             * 读取文件获取所有与规则有关系的内容
             * 按顺序读取保存到 List
             */
            bufferedReader = new BufferedReader(new FileReader(resource.getFile()));
            List<String> allMatchStrs = new ArrayList<>();
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                s = s.trim();
                if (!s.isEmpty() && (s.contains("public static void") || s.contains("@Cache(useClassName"))) {
                    allMatchStrs.add(s);
                }
            }

            //从读取的内容中按规则提取响应数值
            final int matchLevelStrLength = "level=".length();
            for (int i = 0, size = allMatchStrs.size(); i < size; i++) {

                String oneLine = allMatchStrs.get(i);
                oneLine = oneLine.replaceAll("\\s", "");
                final String useClassName = oneLine.substring(oneLine.indexOf("\"") + 1, oneLine.lastIndexOf("\""));
                final String level = oneLine.substring(oneLine.indexOf("level=") + matchLevelStrLength, oneLine.length() - 1);

                final String nextLine = allMatchStrs.get(++i);
                final String methodName = nextLine.substring(nextLine.lastIndexOf(" ") + 1, nextLine.indexOf("("));

                MacthVO macthVO = new MacthVO(methodName, useClassName, Integer.parseInt(level));
                System.out.println(macthVO);
            }

        } catch (Exception e) {
            System.err.println("解析过程错误:" + e.getMessage());

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.err.println("IO 关闭过程错误:" + e.getMessage());
                }
            }
        }

    }

    private static class MacthVO {
        /**
         * 方法名称
         */
        private String methodName;
        /**
         * 注解属性 useClassName 对应值
         */
        private String useClassName;
        /**
         * 注解属性 level 对应值
         */
        private int level;

        public MacthVO(String methodName, String useClassName, int level) {
            this.methodName = methodName;
            this.useClassName = useClassName;
            this.level = level;
        }

        @Override
        public String toString() {
            return "Macth{" +
                    "methodName='" + methodName + '\'' +
                    ", useClassName='" + useClassName + '\'' +
                    ", level=" + level +
                    '}';
        }
    }


}
