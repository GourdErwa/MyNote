package com.gourd.erwa.concurrent.basis;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch ：
 * 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 * 用给定的计数 初始化 CountDownLatch。
 * 由于调用了 countDown()方法，所以在当前计数到达零之前，await方法会一直受阻塞。
 * 之后，会释放所有等待的线程，await的所有后续调用都将立即返回。
 * 这种现象只出现一次——计数无法被重置。
 * <p>
 * CountDownLatch 很适合用来将一个任务分为n个独立的部分，等这些部分都完成后继续接下来的任务，
 * CountDownLatch 只能出发一次，计数值不能被重置。
 *
 * @author wei.Li by 14-8-22.
 */
public class CountDownLatchTest {

    //同步完成一个事件的执行的线程数量
    private static final int SYNCHRONIZED_DONE_THREAD_NUM = 3;
    //线程池
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(SYNCHRONIZED_DONE_THREAD_NUM + 1);
    //声明同步辅助类
    private static CountDownLatch countDownLatch
            = new CountDownLatch(SYNCHRONIZED_DONE_THREAD_NUM);

    /**
     * 模拟多个线程执行任务
     */
    private static void analogThreads() {
        for (int i = 0; i < SYNCHRONIZED_DONE_THREAD_NUM; i++) {

            EXECUTOR_SERVICE.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {

                    Thread.sleep(new Random().nextInt(20000));
                    //执行结束后计数器-1
                    countDownLatch.countDown();

                    System.out.println(this + " -> done ! " +
                            ".the current countDownLatch is " + countDownLatch.getCount());

                    return true;
                }
            });
        }
        EXECUTOR_SERVICE.shutdown();
    }

    public static void main(String[] args) {

        System.out.println("~~~~~~~~ start ~~~~~~~~");

        //模拟多线程协作执行
        analogThreads();

        try {
            //等等计数器归 0 ，即线程全部完成
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("~~~~~~~~ done ~~~~~~~~");

    }

}
