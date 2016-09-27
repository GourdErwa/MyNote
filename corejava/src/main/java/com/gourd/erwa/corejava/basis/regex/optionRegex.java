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

package com.gourd.erwa.util.corejava.basis.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wei.Li by 15/4/8 (gourderwa@163.com).
 */
public class optionRegex {

    public static void main(String[] args) {
        String s = "<select id=\"alarm_metric_name\" class=\"chzn-select\" data-placeholder=\"告警指标\" style=\"width: 218px; display: none;\"><option value=\"_count\">交易吞吐量</option><option value=\"_success_rate\">交易成功率</option><option value=\"_success_count\">交易成功笔数</option><option value=\"_response_rate\">交易响应率</option><option value=\"_response_count\">交易响应笔数</option><option value=\"_latency_msec\">交易响应时间</option><option value=\"_trans_transfer_ms\">交易传输时间</option><option value=\"_out_bytes\">传输字节数（S-&gt;C）</option><option value=\"_in_bytes\">传输字节数（C-&gt;S）</option><option value=\"_out_pkts\">传输数据包数（S-&gt;C）</option><option value=\"_in_pkts\">传输数据包数（C-&gt;S）</option><option value=\"_tot_synack\">SYN+ACK包(S-&gt;C)</option><option value=\"_tot_syn\">SYN包（C-&gt;S）</option><option value=\"_tot_fin_s\">FIN包（S-&gt;C）</option><option value=\"_tot_fin\">FIN包（C-&gt;S）</option><option value=\"_tot_rst_s\">RST包（S-&gt;C）</option><option value=\"_tot_rst\">RST包（C-&gt;S）</option><option value=\"_out_ooo\">乱序包（S-&gt;C）</option><option value=\"_in_ooo\">乱序包（C-&gt;S）</option><option value=\"_out_retran\">重传包（S-&gt;C）</option><option value=\"_in_retran\">重传包（C-&gt;S）</option><option value=\"_tot_zero_client\">服务器零窗口</option><option value=\"_tot_zero_server\">客户端零窗口</option></select>";

        Pattern pattern = Pattern.compile("<option value=\"(\\S+)\">");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
