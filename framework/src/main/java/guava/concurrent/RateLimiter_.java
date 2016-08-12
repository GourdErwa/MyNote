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

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.Executors;

/**
 * 信号量
 *
 * @author wei.Li by 15/3/19 (gourderwa@163.com).
 */
public class RateLimiter_ {

    static ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public static void main(String[] args) {

        RateLimiter limiter = RateLimiter.create(4.0); //每秒不超过4个任务被提交
        limiter.acquire();  //请求RateLimiter, 超过permits会被阻塞
        Runnable runnable = null;

        executorService.submit(runnable); //提交任务

        if (limiter.tryAcquire()) { //未请求到limiter则立即返回false

            doSomething();
        } else {

            doSomethingElse();
        }

    }

    private static void doSomethingElse() {

    }

    private static void doSomething() {

    }
}
