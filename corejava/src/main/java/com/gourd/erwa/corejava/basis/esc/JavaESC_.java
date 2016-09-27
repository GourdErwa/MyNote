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

package com.gourd.erwa.util.corejava.basis.esc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wei.Li by 15/4/1 (gourderwa@163.com).
 */
public class JavaESC_ {


    public static void main(String[] args) {

        System.out.println("----------- JAVA ESC -----------");

        System.out.println('\'');

        System.out.println("\\");
        System.out.println("/");
        System.out.println("\\\\\'");

        System.out.println("----------- JAVA ESC -----------");
        System.out.println();
        System.out.println("----------- Pattern ESC -----------");
        String s = "abc/\\+34/ff\\";

        Pattern pattern = Pattern.compile("\\+");

        final Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            final String group = matcher.group();
            System.out.println(group);
        }

        System.out.println("----------- Pattern ESC -----------");
    }

}
