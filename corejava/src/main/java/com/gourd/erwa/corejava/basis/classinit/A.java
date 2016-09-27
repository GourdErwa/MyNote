package com.gourd.erwa.util.corejava.basis.classinit;

/**
 * @author lw by 14-4-23.
 */
public class A {

    private static final B b_1 = new B(10);
    private static String staticString = initStaticString();
    private static A a_1 = new A("类属性[开始]处初始化Class->");
    private static A a = new A("类属性[结尾]处初始化Class->");

    static {
        System.out.println("A、static代码块->->字符串-静态变量>->" + staticString);
    }

    String initString = initString();

    {
        System.out.println("A、构造方法公用代码块-字符串-成员变量>->" + initString);
        System.out.println("A、构造方法公用代码块-字符串-静态变量>->" + staticString);
    }


    public A() {
        System.out.println("A()-成员变量>->" + initString);
        System.out.println("A()-静态变量>->" + staticString);
        System.out.println();
    }

    public A(String str) {
        System.out.println("A(str)-成员变量>->" + str + initString);
        System.out.println("A(str)-静态变量>->" + str + staticString);
        System.out.println();
    }

    private static String initStaticString() {
        System.out.println("initStaticString()方法赋值静态变量执行->");
        return "[initStaticString()方法返回->->]";
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println("main 方法执行开始...");
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.println("for循环第" + i + "次");
            new A();
            System.out.println();
        }
    }

    private String initString() {
        System.out.println("initString()方法赋值成员变量执行->");
        return "[initString()方法返回->->]";
    }
}

class B {

    static {
        System.out.println("B、static代码块->->");
    }

    {
        System.out.println("B、构造方法公用代码块->->");
    }

    B() {
        System.out.println("B()->->");
        System.out.println();
    }

    B(int i) {
        System.out.println("B(int i)->->");
        System.out.println();
    }
}


