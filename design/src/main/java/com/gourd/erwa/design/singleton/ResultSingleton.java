package com.gourd.erwa.design.singleton;

/**
 * 内部类式加载
 *
 * @author lw
 */
class ResultSingleton {

    private ResultSingleton() {
    }

    public static ResultSingleton getSingleton() {
        return Singleton.resultSingleton;
    }

    private static class Singleton {

        private static ResultSingleton resultSingleton = new ResultSingleton();
    }
}
