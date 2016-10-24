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

package other;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wei.Li by 15/4/1 (gourderwa@163.com).
 */
public class DictionaryTest {

    public static void main(String[] args) {

        String arithmetic = "1+22%1-4*100/30-200e";
        //final String[] split = arithmetic.split("[/+-/*/%]");
        String[] split = arithmetic.split("[/+-/*//%]");
        System.out.println(Arrays.toString(split));


        Pattern pattern = Pattern.compile("([\\d]+)([/+-/*/%])");
        final Matcher matcher = pattern.matcher(arithmetic);

        while (matcher.find()) {
            final String group1 = matcher.group(1);
            final String group2 = matcher.group(2);
            System.out.println("group1\t" + group1);
            System.out.println("group2\t" + group2);
        }


        System.out.println();
        arithmetic();
    }

    private static void arithmetic() {

        String arithmetic = "1+22%1-4*100/30-200e";


        /*final CharMatcher charMatcher = CharMatcher.anyOf("+-*//*%").or(CharMatcher.DIGIT);
        final char[] chars = arithmetic.toCharArray();
        for (char aChar : chars) {
            if (!charMatcher.matches(aChar)) {
                System.err.println("运算语句含有非法字符:" + aChar);
                return;
            }
        }*/

    }


    private static void analyzierParam() {
        String s = "'',null,123,'0,1,2,3','2014-03-12 18:38:26.467319'";

        //以逗号分隔数据
        final String[] split = s.split(",");

        List<String> stringList = new ArrayList<>();

        //临时值存储、例如 '0,1,2,3' 字符串中包含,的参数值
        String temp = "";

        try {
            for (final String one : split) {

                final int oneLength = one.length();

                //是否以 ' 开始或者结束 boolean 值
                final boolean isStartSingleQuotes = one.indexOf("'") == 0;
                final boolean isEndSingleQuotes = one.lastIndexOf("'") == oneLength - 1;

                //如果当前不是处理'0,1,2,3'的数据
                if (!temp.isEmpty()) {
                    //如果以 ' 结束 表示追加数据完毕，否则继续添加
                    if (isEndSingleQuotes) {
                        temp += "," + one;
                        final String substring = temp.substring(1, temp.length() - 1);
                        stringList.add(substring);
                        temp = "";
                    } else {
                        temp += "," + one;
                    }
                    continue;
                }

                //如果以'开始，判断是否以'结束，如果是表示是字符串
                //否则为 '1,2,3'类似的参数值记录到临时 temp String 中
                if (isStartSingleQuotes) {
                    if (isEndSingleQuotes) {
                        final String substring = one.substring(1, oneLength - 1);
                        stringList.add(substring);
                    } else {
                        temp += one;
                    }
                } else {
                    stringList.add(one);
                }
            }
            System.out.println(stringList);
        } catch (Exception e) {
            System.err.printf("参数值不正确、解析错误 error :" + e.getMessage());
        }
    }
}
