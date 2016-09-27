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

package com.gourd.erwa.util.corejava.string;

/**
 * @author wei.Li by 15/1/6 (gourderwa@163.com).
 */
public class StringBuffer_ {

    public static final String STRING = "业务路径A 节点相关A#aa、A#bb 流A#aaa 于13:10:00 在90分钟内连续触发了吞吐量告警事件（交易吞吐量在10分钟内总数大于200）20次";

    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer(100);

        System.out.println(stringBuffer);
    }
}
