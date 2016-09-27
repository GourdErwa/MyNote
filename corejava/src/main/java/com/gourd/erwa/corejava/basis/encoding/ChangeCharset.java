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

/**
 * @author wei.Li by 14/9/26.
 */
public class ChangeCharset {


    /**
     * 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块
     */
    public static final String US_ASCII = "US-ASCII";

    /**
     * ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1
     */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * 8 位 UCS 转换格式
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序
     */
    public static final String UTF_16BE = "UTF-16BE";

    /**
     * 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序
     */
    public static final String UTF_16LE = "UTF-16LE";

    /**
     * 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识
     */
    public static final String UTF_16 = "UTF-16";

    /**
     * 中文超大字符集
     */
    public static final String GBK = "GBK";

    public static void main(String[] args) throws UnsupportedEncodingException {
        ChangeCharset test = new ChangeCharset();
        String str = "This is a 中文的 String!";
        System.out.println("str: " + str + "\t中文的 占" + "中文的".getBytes().length + "个字节");
        String gbk = test.toGBK(str);
        System.out.println("转换成GBK码: " + gbk);
        System.out.println();
        String ascii = test.toASCII(str);
        System.out.println("转换成US-ASCII码: " + ascii);
        gbk = test.changeCharset(ascii, ChangeCharset.US_ASCII, ChangeCharset.GBK);
        System.out.println("再把ASCII码的字符串转换成GBK码: " + gbk);
        System.out.println();
        String iso88591 = test.toISO_8859_1(str);
        System.out.println("转换成ISO-8859-1码: " + iso88591);
        gbk = test.changeCharset(iso88591, ChangeCharset.ISO_8859_1, ChangeCharset.GBK);
        System.out.println("再把ISO-8859-1码的字符串转换成GBK码: " + gbk);
        System.out.println();
        String utf8 = test.toUTF_8(str);
        System.out.println("转换成UTF-8码: " + utf8);
        gbk = test.changeCharset(utf8, ChangeCharset.UTF_8, ChangeCharset.GBK);
        System.out.println("再把UTF-8码的字符串转换成GBK码: " + gbk);
        System.out.println();
        String utf16be = test.toUTF_16BE(str);
        System.out.println("转换成UTF-16BE码:" + utf16be);
        gbk = test.changeCharset(utf16be, ChangeCharset.UTF_16BE, ChangeCharset.GBK);
        System.out.println("再把UTF-16BE码的字符串转换成GBK码: " + gbk);
        System.out.println();
        String utf16le = test.toUTF_16LE(str);
        System.out.println("转换成UTF-16LE码:" + utf16le);
        gbk = test.changeCharset(utf16le, ChangeCharset.UTF_16LE, ChangeCharset.GBK);
        System.out.println("再把UTF-16LE码的字符串转换成GBK码: " + gbk);
        System.out.println();
        String utf16 = test.toUTF_16(str);
        System.out.println("转换成UTF-16码:" + utf16);
        gbk = test.changeCharset(utf16, ChangeCharset.UTF_16LE, ChangeCharset.GBK);
        System.out.println("再把UTF-16码的字符串转换成GBK码: " + gbk);
        String s = new String("中文".getBytes("UTF-8"), "UTF-8");
        System.out.println(s);
    }

    /**
     * 将字符编码转换成US-ASCII码
     */
    public String toASCII(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, US_ASCII);
    }

    /**
     * 将字符编码转换成ISO-8859-1码
     */
    public String toISO_8859_1(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, ISO_8859_1);
    }

    /**
     * 将字符编码转换成UTF-8码
     */
    public String toUTF_8(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UTF_8);
    }

    /**
     * 将字符编码转换成UTF-16BE码
     */
    public String toUTF_16BE(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UTF_16BE);
    }

    /**
     * 将字符编码转换成UTF-16LE码
     */
    public String toUTF_16LE(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UTF_16LE);
    }

    /**
     * 将字符编码转换成UTF-16码
     */
    public String toUTF_16(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UTF_16);
    }

    /**
     * 将字符编码转换成GBK码
     */
    public String toGBK(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, GBK);
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public String changeCharset(String str, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            //用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            //用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param oldCharset 原编码
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public String changeCharset(String str, String oldCharset, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            //用旧的字符编码解码字符串。解码可能会出现异常。
            byte[] bs = str.getBytes(oldCharset);
            //用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }
}
/**
 * java中的String类是按照unicode进行编码的，当使用String(byte[] bytes, String encoding)构造字符串时，
 * encoding所指的是bytes中的数据是按照那种方式编码的，而不是最后产生的String是什么编码方式，
 * 换句话说，是让系统把bytes中的数据由encoding编码方式转换成unicode编码。如果不指明，bytes的编码方式将由jdk根据操作系统决定。
 * <p>
 * 当我们从文件中读数据时，最好使用InputStream方式，然后采用String(byte[] bytes, String encoding)指明文件的编码方式。
 * 不要使用Reader方式，因为Reader方式会自动根据jdk指明的编码方式把文件内容转换成unicode编码。
 * <p>
 * 当我们从数据库中读文本数据时，采用ResultSet.getBytes()方法取得字节数组，同样采用带编码方式的字符串构造方法即可。
 * <p>
 * ResultSet rs;
 * bytep[] bytes = rs.getBytes();
 * String str = new String(bytes, "gb2312");
 * <p>
 * 不要采取下面的步骤。
 * <p>
 * ResultSet rs;
 * String str = rs.getString();
 * str = new String(str.getBytes("iso8859-1"), "gb2312");
 * <p>
 * 这种编码转换方式效率底。之所以这么做的原因是，ResultSet在getString()方法执行时，默认数据库里的数据编码方式为iso8859-1。
 * 系统会把数据依照iso8859-1的编码方式转换成unicode。使用str.getBytes("iso8859-1")把数据还原，
 * 然后利用new String(bytes, "gb2312")把数据从gb2312转换成unicode，中间多了好多步骤。
 * <p>
 * 从HttpRequest中读参数时，利用reqeust.setCharacterEncoding()方法设置编码方式，读出的内容就是正确的了。
 */
