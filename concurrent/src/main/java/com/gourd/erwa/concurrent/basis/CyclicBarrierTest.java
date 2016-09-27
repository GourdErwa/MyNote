package com.gourd.erwa.concurrent.basis;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。
 * 在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。
 * 因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。
 * <p>
 * 需要所有的子任务都完成时，才执行主任务，这个时候就可以选择使用CyclicBarrier。
 * <p>
 * 演示所有选手进入场地后，全部准备好后开始起跑！
 *
 * @author wei.Li by 14-8-23.
 */
public class CyclicBarrierTest {

    //参加跑步比赛的人数
    private static final int FOOTRACE_NUM = 5;

    //线程池
    private static ExecutorService executor
            = Executors.newFixedThreadPool(FOOTRACE_NUM);

    private static CyclicBarrier barrier
            = new CyclicBarrier(FOOTRACE_NUM);

    public static void main(String[] args)
            throws IOException, InterruptedException {

        for (int i = 0; i < FOOTRACE_NUM; i++) {
            executor.submit(new Thread(
                    new Footrace(barrier, i + "号选手")
            ));
        }
        executor.shutdown();
    }

    // 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)
    private static class Footrace implements Runnable {

        private CyclicBarrier barrier;

        private String name;

        Footrace(CyclicBarrier barrier, String name) {
            super();
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {
            try {

                System.out.println(name + "  in the location...");
                Thread.sleep(1000 * (new Random()).nextInt(8));//准备中
                System.out.println(name + "  say : I am ready ...");

                // barrier的await方法，在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(name + " go ！");
        }
    }
}


