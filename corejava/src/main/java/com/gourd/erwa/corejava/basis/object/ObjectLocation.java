/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/xiaohulu
 * Blog  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 */

package com.gourd.erwa.corejava.basis.object;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Java中对象的地址
 *
 * @author wei.Li by 14-8-28.
 */
public class ObjectLocation {

    private static int apple = 10;
    private int orange = 101;

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        Field appleField = ObjectLocation.class.getDeclaredField("apple");
        System.out.println("Location of Apple   : "
                + unsafe.staticFieldOffset(appleField));

        Field orangeField = ObjectLocation.class.getDeclaredField("orange");
        System.out.println("Location of Orange  : "
                + unsafe.objectFieldOffset(orangeField));
    }

    private static Unsafe getUnsafeInstance() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }

}
