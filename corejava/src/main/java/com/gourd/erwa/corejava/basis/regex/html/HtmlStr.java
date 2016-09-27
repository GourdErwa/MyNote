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

package com.gourd.erwa.util.corejava.basis.regex.html;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wei.Li by 15/4/8 (gourderwa@163.com).
 */
public class HtmlStr {

    public static void main(String[] args) {

        try {
            File file = new File("/lw/workfile/intellij_work/my_note/src/main/java/com/java/basis/regex/html/exec_sum.html");
            String string = Files.toString(file, Charset.defaultCharset());

            String s = "A HREF=(\\S+)>\\s*<IMG src=\"icons/details\\.gif";
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(string);
            matcher.reset();
            System.out.println(matcher.groupCount());

            while (matcher.find()) {
                System.out.println(matcher.group(1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
