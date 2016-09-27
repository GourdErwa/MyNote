package com.gourd.erwa.util.corejava.essence_36;

enum Sex {
    Man, Woman
}

/**
 * Created by lw on 14-5-18.
 * <p>
 * JDK1.7后除支持的byte、char、short、int、（4个）包装类、枚举
 * 增加了String类型
 */
public class SwitchExpression {

    private static void demo_1() {
        byte b = 1;
        char c = 1;
        short s = 1;
        int i = 1;
        //以上基本类型的包装类型
        Sex sex = Sex.Man;

        String str;
    }

    public static void main(String[] args) {
        SwitchWraooer.demo_1();
        SwitchEnum.demo_1();
        SwitchString.demo_1();
    }
}

//对于包装类型的处理
class SwitchWraooer {

    public static void demo_1() {
        Integer integer = new Integer(10);
        //相当于拆箱处理
        // switch (integer.intValue())
        switch (integer) {
            case 10:
                System.out.println("10");
                break;
            case 20:
                System.out.println("20");
                break;
        }
    }
}

//对于枚举类型的处理
class SwitchEnum {

    public static void demo_1() {
        Sex sex = Sex.Man;
        //替换为内部类数组（下注释后的代码）的值,
        // switch (sex)替换为1.$SwitchMap$com$java$essence_36$Sex[sex.ordinal()];
        switch (sex) {
            case Man:
                System.out.println("this is man !");
                break;

            case Woman:
                System.out.println("this is man !");
                break;
        }
    }

    /* 隐式的生成一个匿名内部类,编译后可以反编译查看
    static class SwitchEnum$1 {
        static final int array[];

        static {
            array = new int[Sex.values().length];

            array[Sex.Man.ordinal()] = 1;
            array[Sex.Woman.ordinal()] = 2;
        }
    }
    */
}

//String类型的处理
class SwitchString {
    public static void demo_1() {
        String str = "ErWa";
        //分2个switch执行,如下面注释掉的方法demo_2()
        // 第一根据对象哈希码对一个临时变量赋值，第二个根据临时变量进行判断
        switch (str) {
            case "YiWa":
                System.out.println("一娃");
                break;
            case "ErWa":
                System.out.println("贰娃");
                break;

        }
    }
    /**
     private static void demo_2() {
     String str = "ErWa";
     String temp = str;
     byte b = -1;
     switch (temp.hashCode()) {
     case 283748254:
     if ("YiWa".equals(temp)) b = 0;
     case 834583458:
     if ("ErWa".equals(temp)) b = 1;
     }

     switch (b) {
     case 0:
     System.out.println("一娃");
     break;
     case 1:
     System.out.println("贰娃");
     break;
     }
     }
     **/
}

