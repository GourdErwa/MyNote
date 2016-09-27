package com.gourd.erwa.util.corejava.essence_36;

/**
 * Created by lw on 14-5-18.
 * <p>
 * 1.静态方法不能重写
 * 2.成员变量也不可用重写，只可以隐藏，
 * 相对于方法的隐藏，成员变量的隐藏只要求父类与之类的成员变量名称相同
 * 并且父类的成员变量在子类中可见。与成员变量的访问权限、类型、实例变量还是静态变量无关。
 * 3.重写与隐藏的区别是：
 * 重写是动态绑定，隐藏是静态绑定，根据编译时引用的静态类型来决定调用相关的类成员。
 * 即：如果子类重写了父类的方法，当父类的引用指向子类时，通过父类的引用调用的是子类的方法。
 * 如果子类隐藏了父类的方法（成员变量），则调用的还是父类的方法（成员变量）。
 */
public class StaticOverride extends SuperStaticOverride {

    static String s2 = "StaticOverride_static_s2";
    protected String s1 = "StaticOverride_protected_s1";

    public static void static_demo() {
        System.out.println("StaticOverride->static_demo()->");
    }

    public static void main(String[] args) {
        SuperStaticOverride superStaticOverride = new SuperStaticOverride();
        System.out.println("父类的变量信息->");
        System.out.println(superStaticOverride.s1);
        System.out.println(superStaticOverride.s2);
        superStaticOverride.static_demo();
        superStaticOverride.demo();

        StaticOverride staticOverride = new StaticOverride();
        System.out.println("子类的变量信息->");
        System.out.println(staticOverride.s1);
        System.out.println(staticOverride.s2);
        staticOverride.static_demo();
        staticOverride.demo();

        superStaticOverride = staticOverride;
        System.out.println("父类指向子类后的变量信息->");
        System.out.println(superStaticOverride.s1);
        System.out.println(superStaticOverride.s2);
        superStaticOverride.static_demo();
        superStaticOverride.demo();

        superStaticOverride = null;
        System.out.println("对象实例为空时，是否可以调用静态方法呢？为什么？->");
        //superStaticOverride.static_demo();
    }

    public void demo() {
        System.out.println("StaticOverride->demo()->");
    }
}

class SuperStaticOverride {
    static String s2 = "SuperStaticOverride_static_s2";
    protected String s1 = "SuperStaticOverride_protected_s1";

    public static void static_demo() {
        System.out.println("SuperStaticOverride->static_demo()->");
    }

    public void demo() {
        System.out.println("SuperStaticOverride->demo()->");
    }
}
