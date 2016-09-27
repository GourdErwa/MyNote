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

package com.gourd.erwa.util.corejava.basis.math;

import java.math.BigDecimal;

/**
 * @author wei.Li by 14-9-11.
 */
public class BigDecimal_ {

    private static void aVoid() {

        BigDecimal bigDecimal = BigDecimal.ONE;
        String s = bigDecimal.divide(new BigDecimal("0.3"), BigDecimal.ROUND_DOWN).toString();
        System.out.println(s);

    }


    public static void main(String[] args) {
        aVoid();
    }
}

class SimplifyToBigDecimal {

}
