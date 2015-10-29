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

package commons.apache.lang;

import org.apache.commons.lang.CharUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author wei.Li by 14/9/26.
 */
public class CharUtils_ {


    /**
     * java是用unicode来表示字符，"我"这个中文字符的unicode就是2个字节。
     * String.getBytes(encoding)方法是获取指定编码的byte数组表示，通常gbk/gb2312是2个字节，utf-8是3个字节。
     * 如果不指定encoding则取系统默认的encoding。
     * <p>
     * char在java中是以 两字节表示且非负，所以可以取的范围是0~65535(十六进制为0~OxFFFF)
     */
    private static void explain() {
        //char c = '我';
        String s = "我";

        try {

            System.out.println("GBK编码格式     ->大小为"
                    + s.getBytes("GBK").length);

            System.out.println("UTF-8编码格式   ->大小为"
                    + s.getBytes("UTF-8").length);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * GBK编码格式     ->大小为2
         * UTF-8编码格式   ->大小为3
         */
    }

    private static void simpleExample() {

        //return ch < 128;
        //CharUtils.isAscii('我');

        //判断是否为ascii字母，即值在65到90或者97到122
        CharUtils.isAsciiAlpha('A');

        //判断范围是 65 到 90，即a 到 z.
        CharUtils.isAsciiAlphaLower('a');

        //判断范围是97到122.即A 到Z.
        CharUtils.isAsciiAlphaUpper('a');

        //判断是否为ascii字符数字，即值在48到57，65到90或者97到122.
        CharUtils.isAsciiAlphanumeric('3');

        //判断是否为数字 ch >= ‘0’ && ch <= ‘9’
        CharUtils.isAsciiNumeric('1');

        //判断是否为控制字符 ch < 32 || ch = 127;
        CharUtils.isAsciiControl('\n');

        //判断是否可打印出得ascii字符 ch >= 32 && ch < 127;
        CharUtils.isAsciiPrintable('3');

        //数字转换  if(isAsciiNumeric) return ch - 48;
        CharUtils.toIntValue('E');

        //将ch转换为unicode表示的字符串形式
        CharUtils.unicodeEscaped('S');

    }


    public static void main(String[] args) {


    }
}
