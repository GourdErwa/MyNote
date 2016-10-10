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

package guava.concurrent;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wei.Li by 15/3/19 (gourderwa@163.com).
 */
public class ListenableFuture_ {


    private static void aVoid() {

        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

        final ListenableFuture<Integer> listenableFuture = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("call execute..");
                TimeUnit.SECONDS.sleep(1);
                return 7;
            }
        });

        //方法一：通过ListenableFuture的addListener方法
        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("get listenable future's result " + listenableFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, executorService);

        //方法二：通过Futures的静态方法addCallback给ListenableFuture添加回调函数
        Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                System.out.println("get listenable future's result with callback " + result);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });


    }

    private static void futures() {

        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

        final ListenableFuture<Integer> listenableFuture = executorService.submit(() -> {
            System.out.println("call run ...");
            TimeUnit.SECONDS.sleep(1);
            return 7;
        });

        /*final ListenableFuture<Integer> transform = Futures.transform(listenableFuture, (Integer input) -> {
            final Integer finalInput = input + 100;
            return executorService.submit(() -> {
                System.out.println("transform AsyncFunction run ...");
                return finalInput;
            });
        });

        try {
            System.out.println(transform.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/

    }

    public static void main(String[] args) {
        //aVoid();
        futures();
    }
}
