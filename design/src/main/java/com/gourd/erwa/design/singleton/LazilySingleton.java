package com.gourd.erwa.design.singleton;

/**
 * 懒汉式加载
 *
 * @author lw
 */
class LazilySingleton {

    private static LazilySingleton lazilySingleton = null;

    private LazilySingleton() {
    }

    public static synchronized LazilySingleton getSingleton() {
        if (lazilySingleton == null) {
            lazilySingleton = new LazilySingleton();
        }
        return lazilySingleton;
    }
}
