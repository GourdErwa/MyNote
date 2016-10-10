package com.gourd.erwa.design.singleton;

/**
 * 饿汉式加载
 *
 * @author lw
 */
class Singleton {

    private static Singleton singleton = new Singleton();

    private Singleton() {
    }

    public static Singleton getSingleton() {
        return singleton;
    }
}
