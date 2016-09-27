/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * Blog  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 */

package com.gourd.erwa.util.corejava.basis.regex;

import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wei.Li by 14-9-4.
 */
public class RegexpExample {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RegexpExample.class);

    //处理内存 Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
    private static void example_1() {
        final String s = "处理内存 Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers";
        final Pattern pattern = Pattern.compile("(\\d+)k\\s\\w+,");
        final Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            LOGGER.info("<{}>", matcher.group(1));
        }
    }

    private static void runExample(String regexp, String s) {
        Pattern pattern = Pattern.compile(regexp);
        final Matcher matcher = pattern.matcher(s);
        boolean b = matcher.matches();
        LOGGER.info("[ {} ] matcher [ {} ] , result [ {} ]", pattern.pattern(), s, b);
    }

    private static void runExampleGroup(String regexp, String s) {
        Pattern pattern = Pattern.compile(regexp);
        final Matcher matcher = pattern.matcher(s);
        String result = null;
        while (matcher.find()) {
            result = matcher.group();
        }
        LOGGER.info("[ {} ] matcher [ {} ] , result [ {} ]", pattern.pattern(), s, result);
    }

    private static void runTableMsg() {
        final String DEFAULT_TEMPLATE = "02|1|%{com.gourd.erwa.date}|%{time}|192.168.1.233|ezsonar_host|EZSonar_BIZ|EZSonar_App|EZSonar_Instance_Name|EZSonar_Instance_value|%{id}|%{type}|%{metric_name}|%{real_value}|%{start_time}|%{end_time}|%{stream_id}|%{topo_id}|%{topo_name}|%{topo_node_id}|%{topo_node_name}|%{root_node_name}|300|%{severity}";
        Pattern pattern = Pattern.compile("(.*?)\\|");

        /*Matcher matcher = pattern.matcher(DEFAULT_TEMPLATE);
        while (matcher.find()) {
            System.out.println(matcher.group(1));

        }*/


        String s = DEFAULT_TEMPLATE.replaceAll("\\|", "</td><td>");
        s += "</td></tr>";
        System.out.println("<tr><td>" + s);
    }

    public static void main(String[] args) {
        runTableMsg();
    }

}
