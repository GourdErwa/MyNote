package com.gourd.erwa.util.corejava.essence_36;

/**
 * Created by lw on 14-5-18.
 * <p>
 * 类型转换的神秘
 */
class Int2Byte {

    int i = 10;
    byte b = 1;
    char c = 1;
    short s = 1;

    //隐式的进行类型转换
    float f = i;
    long l = i;

    //显式的进行类型转换，注释掉的为编译错误
    //byte b1=i;
    byte b1 = (byte) i;
    //char c1=i;
    char c1 = (char) i;
    //short s1=i;
    short s1 = (short) i;

}

/**
 * char无符号类型(0~65535)
 * byte(-128~127)
 * short(-32768~32767)
 * <p>
 * 因此char与byte、short不存在子集关系，需要做类型转换
 * <p>
 * 其次当byte、char、short（或者三者之间进行混合运算）结果为int
 * 并非与较高的类型相同
 */
class Conversion {

    private static void demo_1() {
        //隐式转换
       /* byte b = -23;
        short s = 60;
       // char c = '二';
        char c1 = 89;

        //需要显式转换
        b = (byte) c;
        c = (char) b;
        s = (short) b;
        b = (byte) -b;
        s = (short) (b + c);
        b = (byte) (b + 1);
        b += 1;*/
    }
}

/**
 * 扩展收缩转换
 * 从byte-char
 * 1.将byte扩展为int
 * 2.将int收缩为char
 */
public class TypeConversion {


    private static void demo_1() {
        //byte为非负数
        byte b = 10;
        char c = (char) b;
        int i = c;
        System.out.println("TypeConversion.demo_1()->i=" + i);

        //byte为负数
        b = -10;
        c = (char) b;
        i = c;
        System.out.println("TypeConversion.demo_1()->i=" + i);
        /**
         *  1.byte b=-10;
         *      补码为：
         *      1 1 1 1  0 1 1 0
         *  2.扩展为int型，因为byte是有符号类型，所以执行符号位扩展，
         *    扩展的24位符号为（1），补码为：
         *    1 1 1 1  1 1 1 1  1 1 1 1  ...1 1 1 1  0 1 1 0
         *
         *  3.将int转为char，直接截取低16位，补码为
         *      1 1 1 1  1 1 1 1  1 1 1 1   0 1 1 0
         *
         *  4.然后将char转为int，因为char为无符号类型，ku扩展的16位为0
         *      16个0 +  1 1 1 1  1 1 1 1  1 1 1 1   0 1 1 0
         *
         * 因此结果为65526
         *
         */


        //输出所有转换后值改变的byte值
        int temp = 0;
        for (int m = Byte.MIN_VALUE, n = Byte.MAX_VALUE; m < n; m++) {
            b = (byte) m;
            c = (char) b;
            i = c;
            if (b != i) {
                System.out.print("[b=" + b + ",i=" + i + "]");
                if (++temp % 4 == 0) {
                    System.out.println();
                }
            }
        }
        System.out.println("共有[" + temp + "]个值转换后改变！");
    }

    // final修饰的 short变量赋值给 byte，可以通过编译吗？
    private static void test() {
        final short s = 20;
        byte b = s;

    }

    public static void main(String[] args) {
        demo_1();

        //test();
    }
}

