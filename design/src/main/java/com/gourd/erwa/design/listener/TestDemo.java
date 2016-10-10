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

package com.gourd.erwa.design.listener;

/**
 * @author wei.Li by 15/3/19 (gourderwa@163.com).
 */
class TestDemo {

    public static void main(String args[]) {

        Lights ds = new Lights();

        ds.addDemoListener(new DemoListener() {
            @Override
            public void handleEvent(DemoEvent demoEvent) {
                System.out.println(this.toString());
            }
        });

        ds.updateOk();
    }
}
