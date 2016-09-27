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

package com.gourd.erwa.util.corejava.basis.loop;

/**
 * @author wei.Li by 15/1/16 (gourderwa@163.com).
 */
public class Loop_ {

    private static void aVoid() {

        loop_x:
        for (int x = 0; x < 10; x++) {
            System.out.println("x:" + x);
            loop_y:
            for (int i = 0; i < 10; i++) {
                System.out.println("i:" + i);

                for (int j = 0; j < 10; j++) {
                    if (j == 3) {
                        System.out.println("j==3 continue loop_1");
                        continue loop_x;
                        //break;
                    }
                    System.out.println("j:" + j);
                }
            }
        }
    }

    public static void main(String[] args) {
        aVoid();
    }
}
