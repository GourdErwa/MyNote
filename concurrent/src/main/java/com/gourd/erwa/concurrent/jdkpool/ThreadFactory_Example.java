package com.gourd.erwa.concurrent.jdkpool;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author wei.Li by 14-8-21.
 */
public class ThreadFactory_Example {

    private static ThreadFactory DEFAULTTHREADFACTORY = Executors.defaultThreadFactory();

    public static ExecutorService EXECUTOR_SERVICE//创建线程池
            = Executors.newSingleThreadExecutor(DEFAULTTHREADFACTORY);

    /**
     * 由ThreadFactory 添加线程
     */
    private static void runTask() {
        EXECUTOR_SERVICE.execute(DEFAULTTHREADFACTORY.newThread(new ExampleThread()));
    }

    public static void main(String[] args) {
        runTask();
    }

    static class ExampleThread implements Runnable {
        @Override
        public void run() {
            System.out.println();
        }
    }
}
