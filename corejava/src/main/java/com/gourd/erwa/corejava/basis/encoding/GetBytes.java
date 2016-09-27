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

package com.gourd.erwa.util.corejava.basis.encoding;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 在Javac编译期间，也会先从OS中取得现在使用的字符集，此处设为A，
 * 之后把送入的字符串转化为Unicode编码，在编译之后再从Unicode转化为A型字符集。
 *
 * @author wei.Li by 14/9/28.
 */
public class GetBytes {

    private static final String STR = "中";

    /********************************************************/
    /** new String(str.getBytes(oldEncoding), newEncoding) **/
    /********************************************************/

    /**
     * @param str
     * @param oldEncoding
     * @param newEncoding
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String decode(String str, String oldEncoding, String newEncoding)
            throws UnsupportedEncodingException {

        //采用设置的编码 oldEncoding 返回该字符串在改编码下的字节数组表示形式
        byte[] bytes = str.getBytes(oldEncoding);

        //使用 newEncoding 指定的编码来将byte[]解析成字符串
        String s = new String(bytes, newEncoding);

        // return new String(str.getBytes(oldEncoding), newEncoding);

        return s;
    }


    /**
     * 相同的编码进行编码与解码测试
     *
     * @throws UnsupportedEncodingException
     */
    private static void createRestoreBySame()
            throws UnsupportedEncodingException {
        decode(STR, "UTF8", "UTF8");            //中
        decode(STR, "UTF16", "UTF16");          //中
        decode(STR, "GBK", "GBK");              //中
        decode(STR, "ISO8859-1", "ISO8859-1");  //?

        /**
         * ISO8859-1编码的编码表中,根本就没有包含汉字字符,
         * 当然也就无法通过"中".getBytes("ISO8859-1");来得到正确的"中"字在ISO8859-1中的编码值
         * 所以再通过new String()来还原就无从谈起了.
         * 因此,通过String.getBytes(String decode)方法来得到byte[]时,
         * 一定要确定decode的编码表中确实存在String表示的码值这样得到的byte[]数组才能正确被还原
         */

    }


    /**
     * 为了让中文字符适应某些特殊要求(如http header头要求其内容必须为iso8859-1编码),
     * 可能会通过将中文字符按照字节方式来编码的情况，读到后台在进行解码
     *
     * @throws UnsupportedEncodingException
     */
    private static void createRestoreTest()
            throws UnsupportedEncodingException {

        String create = decode(STR, "UTF8", "ISO8859-1");

        String restore = decode(create, "ISO8859-1", "utf8");

        /**
         *  运行结果：
         *  create  Str:ä¸­
         *  restore Str:中
         */
    }


    /********************************************************/
    /************ URLEncoder,URLDecoder 的编码解码 ************/
    /********************************************************/
    /**
     * URLEncoder,URLDecoder 的编码解码
     *
     * @throws UnsupportedEncodingException
     */
    private static void coder()
            throws UnsupportedEncodingException {

        URLDecoder.decode(URLEncoder.encode(STR, "UTF8"), "UTF8");           //中
        URLDecoder.decode(URLEncoder.encode(STR, "UTF16"), "UTF16");         //中
        URLDecoder.decode(URLEncoder.encode(STR, "GBK"), "GBK");             //中
        URLDecoder.decode(URLEncoder.encode(STR, "ISO8859-1"), "ISO8859-1"); //?

    }

    public static void main(String[] args)
            throws UnsupportedEncodingException {

        createRestoreBySame();
        createRestoreTest();
        coder();

        //%E4%B8%AD
        System.out.println(URLEncoder.encode("http://gourderwa.com/字符编码/JAVA 编码之各字符编码的区别联系，常见编码问题汇总", "ISO8859-1"));

    }
}


