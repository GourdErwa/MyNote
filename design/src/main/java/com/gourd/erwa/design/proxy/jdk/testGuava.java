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

package com.gourd.erwa.design.proxy.jdk;

import com.google.common.reflect.Reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author wei.Li by 15/3/31 (gourderwa@163.com).
 */
class testGuava {

    public static void main(String[] args) {

        final IBookFacadeJDK iBookFacadeJDK = new IBookFacadeJDKImpl();

        IBookFacadeJDK bookFacadeJDK = Reflection.newProxy(IBookFacadeJDK.class, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                System.out.println("Test_Guava ... before");
                method.invoke(iBookFacadeJDK, args);
                System.out.println("Test_Guava ... after");

                return null;
            }
        });

        bookFacadeJDK.seeBook();

    }
}
