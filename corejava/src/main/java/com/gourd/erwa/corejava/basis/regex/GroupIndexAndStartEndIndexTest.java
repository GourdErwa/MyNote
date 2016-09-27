package com.gourd.erwa.util.corejava.basis.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lw on 14-7-14.
 * <p>
 * group(),start(),end()所带的参数i就是正则表达式中的子表达式索引（第几个子表达式），
 * 当将“组”的概念与“子表达式”对应起来之后，理解matcher的group,start,end就完全没有障碍了。
 */
public class GroupIndexAndStartEndIndexTest {
    public static final String PATTERN_TEMPLATE = "%\\{([a-zA-Z0-9_]*)\\}";
    public static final Pattern pattern = Pattern.compile(PATTERN_TEMPLATE);

    //#customizable alarm variables: com.gourd.erwa.date, time, id, type, metric_name, real_value, start_time, end_time, stream_id, stream_name, topo_id, topo_name, severity, reverse_severity
    public static final String out_syslog_alarm_template = "02|1|%{com.gourd.erwa.date}|%{time}|192.168.1.233|ezsonar_host|EZSonar_BIZ|EZSonar_App|EZSonar_Instance_Name|EZSonar_Instance_value|%{id}|%{type}|%{metric_name}|%{real_value}|%{start_time}|%{end_time}|%{stream_id}|%{topo_id}|%{topo_name}|%{topo_node_id}|%{topo_node_name}|%{root_node_name}|300|%{severity}";

    // #customizable performance variables: topo_id, topo_name, node_id, node_name, count, success_rate, response_rate, _latency_msec, _in_bytes, _out_bytes, _in_pkts, _out_pkts, _in_retran, _out_retran, _tot_rst, _tot_rst_s, _in_ooo, _out_ooo, _tot_zero_client, _tot_zero_server,
    public static final String out_syslog_topo_performance_template = "02|0|%{com.gourd.erwa.date}|%{time}|192.168.1.233|ezsonar_host|EZSonar_BIZ|EZSonar_App|EZSonar_Instance_Name|EZSonar_Instance_value|%{topo_id}|%{topo_name}|%{count}|%{success_rate}|%{response_rate}|%{latency_msec}|%{in_bytes}|%{out_bytes}|%{in_pkts}|";
    public static final String out_syslog_node_performance_template = "02|0|%{com.gourd.erwa.date}|%{time}|192.168.1.233|ezsonar_host|EZSonar_BIZ|EZSonar_App|EZSonar_Instance_Name|EZSonar_Instance_value|%{topo_id}|%{topo_name}|%{topo_node_id}|%{topo_node_name}|%{count}|%{success_rate}|%{response_rate}|%{latency_msec}|%{in_bytes}|%{out_bytes}|%{in_pkts}|";
    public static final String out_syslog_stream_performance_template = "02|0|%{com.gourd.erwa.date}|%{time}|192.168.1.233|ezsonar_host|EZSonar_BIZ|EZSonar_App|EZSonar_Instance_Name|EZSonar_Instance_value|%{topo_id}|%{topo_name}|%{count}|%{success_rate}|%{response_rate}|%{latency_msec}|%{in_bytes}|%{out_bytes}|%{in_pkts}|";

    public static void main(String[] args) {
        matcher();
    }

    private static void matcher() {
        String str = "02|0|%{com.gourd.erwa.date}|%{time}|192.168.1.233|%{ezsonar_host}|EZSonar_BIZ|EZSonar_App|EZSonar_Instance_Name|EZSonar_Instance_value|%{topo_id}|%{topo_name}|%{count}|%{success_rate}|%{response_rate}|%{latency_msec}|%{in_bytes}|%{out_bytes}|%{in_pkts}|";
        Matcher matcher = pattern.matcher(str.trim());
        matcher.reset();
        System.out.println("matcher.groupCount()->\t" + matcher.groupCount());
        while (matcher.find()) {
            System.out.println("Group 1:\t" + matcher.group(1));//得到第一组匹配——与(or)匹配的
            //System.out.println("Start 1:\t" + matcher.start(1) + " End 1:\t" + matcher.end(1));//第一组匹配的索引
            //System.out.println(str.substring(matcher.start(0), matcher.end(1)));//从总匹配开始索引到第1组匹配的结束索引之间子串——Wor
        }
    }
}
