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
import java.nio.charset.Charset;

/**
 * @author wei.Li by 14/9/26.
 */
public class Conversion {

    public static final String STRING = "Iam中国人";

    private static void conversion(String charSet) {

        byte[] bytes
                = STRING.getBytes(Charset.forName(charSet));

        System.out.println("\n\n" + charSet);

        for (byte aByte : bytes) {
            System.out.print(aByte + "\t");
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
       /* conversion("UTF-8");
        conversion("UTF-16");
        conversion("GBK");
        conversion("GB2312");
        conversion("ISO-8859-1");
        conversion("Unicode");*/

    }
}
