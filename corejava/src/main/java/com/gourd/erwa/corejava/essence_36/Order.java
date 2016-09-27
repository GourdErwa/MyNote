package com.gourd.erwa.util.corejava.essence_36;

import java.util.Arrays;

/**
 * Created by lw on 14-5-18.
 * 注释仅代表个人想法，具体结果run后查看
 * <p>
 * 运算顺序的详细挖掘
 * <p>
 * 操作数从左向右的计算规则与运算符的结合性无关，就算运算符是由右向左结合的，也会在运算之前先确定左侧的操作数。
 * <p>
 * 复合运算符自动将右侧的计算结果类型转换为左侧操作数的结果类型，如demo_5方法实例
 * <p>
 * 方法参数传递也是这样吗？如demo_7方法实例
 */
public class Order {

    private static void demo_1() {

        int i = 5;
        int j = (i++) + (i++) + (i++);//j=5+6+7

        System.out.println("demo_1()->i=" + i);
        System.out.println("demo_1()->j=" + j);
    }

    private static void demo_2() {

        int[] a = {0, 0, 0, 0, 0, 0, 0};

        int i = 2;
        a[++i] = i++;   //a[3]=3;i=3+1;
        System.out.println("demo_2()->i=" + i);
        System.out.println("demo_2()->a[]=" + Arrays.toString(a));
    }

    private static void demo_3() {

        int[] a = {10, 10, 10, 10, 10, 10, 10};

        int i = 2;
        a[i] = i = 4;//a[2]=4
        System.out.println("demo_3()->i=" + i);
        System.out.println("demo_3()->a[]=" + Arrays.toString(a));
    }

    private static void demo_4() {

        int[] a = {10, 10, 10, 10, 10, 10, 10};
        int[] b = {20, 20, 20, 20, 20, 20, 20};
        int[] c = a;
        int i = 5;
        a[--i] = (a = b)[i]; //a[4]=b[4];
        System.out.println("demo_4()->i=" + i);
        System.out.println("demo_4()->a[]=" + Arrays.toString(a));
        System.out.println("demo_4()->b[]=" + Arrays.toString(b));
        System.out.println("demo_4()->c[]=" + Arrays.toString(c));
    }


    private static void demo_5() {
        byte i = 1;
        //i=i+1;//error
        i += i;
    }

    private static void demo_6() {

        int a = 10;
        a += ++a;//a=a+(++a);10+11?
        System.out.println("demo_6()->a=" + a);

        int array[] = {0, 1, 2, 3, 4, 5};
        int b = 1;
        array[b] *= b = 2;//array[1]=array[1]*2;
        System.out.println("demo_6()->b=" + b);
        System.out.println("demo_6()->array[]=" + Arrays.toString(array));
    }


    private static void demo_7(int x, int y, int z) {
        System.out.println("demo_7()->B=" + x);
        System.out.println("demo_7()->y=" + y);
        System.out.println("demo_7()->z=" + z);
    }

    public static void main(String[] args) {
        //demo_1----6();

        int i = 1;
        demo_7(++i, --i, i++);
    }
}
