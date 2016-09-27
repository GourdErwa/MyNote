/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * CSDN  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 *
 * Personal home page: http://grouderwa.com
 */

package com.gourd.erwa.concurrent.jdkpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/join 并发
 *
 * @author wei.Li by 15/4/14 (gourderwa@163.com).
 */
public class ForkJion_ {

    public static void main(String[] args) {

        ForkJoinPool pool = new ForkJoinPool();
        final ForkJoinTask<Integer> forkJoinTask = pool.submit(new Summation(0, 10000000));

         /*   final Integer invoke = pool.invoke(new Summation(0, 100));
            System.out.println(invoke);*/
        System.out.println("task  middle 。。。。");

        try {
            System.out.println(forkJoinTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        /**
         * getException方法返回Throwable对象，如果任务被取消了则返回CancellationException。如果任务没有完成或者没有抛出异常则返回null。
         */
        /*if (forkJoinTask.isCompletedAbnormally()) {
            System.out.println(forkJoinTask.getException().getMessage());
        }*/
    }
}

class Summation extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 10;
    private int start = 0;
    private int end = 0;

    public Summation(int start, int end) {
        this.start = start;
        this.end = end;
        // System.out.println(start + " / " + end);
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Integer compute() {
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int r = 0;
        if ((end - start) <= THRESHOLD) {
            for (int i = start; i <= end; i++) {
                r += i;
            }
        } else {

            int mi = (start + end) / 2;
            Summation leftFork = new Summation(start, mi - 1);
            Summation rightFork = new Summation(mi, end);
            leftFork.fork();
            rightFork.fork();

            int leftR = leftFork.join();
            int rightR = rightFork.join();
            r += (leftR + rightR);
        }

        return r;
    }

}
