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

package com.gourd.erwa.util.corejava.collections;

import com.google.common.base.Splitter;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author wei.Li by 15/3/4 (gourderwa@163.com).
 */
public class Iterator_ {

    public static void main(String[] args) {

        Splitter splitter = Splitter.on(" ").limit(20).trimResults().omitEmptyStrings();
        final String sequence = " 1 3 5 7 9";
        final List<String> splitToList = splitter.splitToList(sequence);
        System.out.println(splitToList);
        final String[] strings = sequence.split(" ");
        System.out.println(Arrays.toString(strings));


        Iterator iterator = new Iterator() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Object next() {
                return null;
            }
        };
    }
}
