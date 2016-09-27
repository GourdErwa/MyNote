package com.gourd.erwa.concurrent.jdkpool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: lw
 * Date: 14-7-1
 */
public class FixedThreadPool_Example {

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * 添加任务到线程池
     *
     * @param task 任务编号
     */
    private static void run2FixedThreadPool(String task) {
        // executor.execute(new FixedThreadPool_Handle());
        FutureTask<String> future;
        future = new FutureTask<String>(() -> {
            try {
                //模拟执行时间为随机值
                Thread.sleep(new Random().nextInt(6000));
                System.out.println("run task :" + task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //真正的任务在这里执行，这里的返回值类型为String，可以为任意类型
            return null;
        });
        //executor.submit(future);
        executor.execute(future);
        try {
            //接受任务返回，可以设置超时
            System.out.println(future.get(3, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            try {
                System.out.println("timeOut task->" + future.get());
            } catch (InterruptedException | ExecutionException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
    }

    public static void main(String[] args) {
        String task = "";//模拟任务
        for (int i = 0; i < 5; i++) {
            run2FixedThreadPool(i + task);
        }
        System.out.println("---------- add task done ----------");
        if (!executor.isShutdown()) {
            executor.shutdown();
        }
        System.out.println("executor is shutdown ->" + executor.isShutdown());
    }
}
