package com.gourd.erwa.util.corejava.essence_36;

/**
 * Created by lw on 14-5-18.
 * <p>
 * 交换变量的几种方式
 */
public class Replace {

    //临时变量-赋值交换
    private static void temp_Replace(V v) {
        int temp = v.a;
        v.a = v.b;
        v.b = temp;
        System.out.println("temp_Replace(V v)->" + v);
    }

    //临时变量—相加
    private static void add_temp_Replace(V v) {
        int temp = v.a + v.b;
        v.a = temp - v.a;
        v.b = temp - v.b;
        System.out.println("add_temp_Replace(V v)->" + v);
    }

    //临时变量-相减
    private static void sub_temp_Replace(V v) {
        int temp = v.a - v.b;
        v.a = v.a - temp;
        v.b = v.b + temp;

        System.out.println("sub_temp_Replace(V v)->" + v);
    }

    //异或运算，即a=a^b^b;
    private static void yihuo_Replace(V v) {
        v.a = v.a ^ v.b;
        v.b = v.a ^ v.b;
        v.a = v.a ^ v.b;
        System.out.println("yihuo_Replace(V v)->" + v);
    }


    public static void main(String[] args) {
        temp_Replace(new V(1, 2));
        add_temp_Replace(new V(1, 2));
        sub_temp_Replace(new V(1, 2));
        yihuo_Replace(new V(1, 2));

    }


}

class V {
    int a, b;

    V(int a, int b) {
        this.a = a;
        this.b = b;
        System.out.println("初始化-V{" +
                "a=" + a +
                ", b=" + b +
                '}');
    }

    @Override
    public String toString() {
        return "替换后-V{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
