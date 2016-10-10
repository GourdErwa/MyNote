package com.gourd.erwa.design.singleton;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author lw by 14-4-30.
 */
class Test extends Thread {

    private static final String[] classNames = {"Singleton", "LazilySingleton", "ResultSingleton"};
    //private static Test test = new Test();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Test().start();
        }
    }

    private static void getTimes(String[] classNames) {
        for (String className : classNames) {

            long start = System.currentTimeMillis();
            Class c = null;
            try {
                c = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Method method = null;
            try {
                if (c != null) {
                    method = c.getMethod("getSingleton");
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                if (method != null) {
                    method.invoke(c);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            System.out.println(className + "单例获取耗时-》" + (System.currentTimeMillis() - start) + "ms");
        }
        System.out.println();
    }

    @Override
    public void run() {
        try {
            System.out.println(this.toString() + "Thread run ....");
            Thread.sleep((int) (Math.random() * 1000));
            getTimes(classNames);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
