package com.gourd.erwa.concurrent.basis;

import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 实例对象同步锁测试
 */
class InstanceClass {

    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(InstanceClass.class);

    /**
     * 耗时较长的同步方法
     */
    synchronized void instanceMethodOfLongRunning() {
        LOGGER.info("into instanceMethodOfLongRunning() ...             obj:<{}>", this);

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            LOGGER.info("run instanceMethodOfLongRunning() error ...    obj:<{}> , e : <{}>", this);
        }
        LOGGER.info("get out instanceMethodOfLongRunning() ...          obj:<{}>", this);
    }

    /**
     * 瞬间可完成的同步方法
     */
    synchronized void instanceMethodOfInstantlyComplete() {
        LOGGER.info("into instanceMethodOfInstantlyComplete() ...             obj:<{}>", this);
    }

    /**
     * 瞬间可完成的非同步方法
     */
    synchronized void instanceMethodOfInstantlyCompleteNotSynchronized() {
        LOGGER.info("into instanceMethodOfInstantlyCompleteNotSynchronized() ...             obj:<{}>", this);
    }

}

/**
 * 静态方法同步锁测试
 */
class StaticClass {

    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(StaticClass.class);

    /**
     * 耗时较长的同步方法
     */
    synchronized static void staticMethodOfLongRunning() {
        LOGGER.info("into staticMethodOfLongRunning ...");
        try {
            Thread.sleep(8000L);
        } catch (InterruptedException e) {
            LOGGER.info("run staticMethodOfLongRunning error ...  e : <{}>", e.getMessage());
        }
        LOGGER.info("get out staticMethodOfLongRunning ...");
    }

    /**
     * 瞬间可完成的同步方法
     */
    synchronized static void staticMethodOfInstantlyComplete() {
        LOGGER.info("into staticMethodOfInstantlyComplete ...");
    }

    /**
     * 瞬间可完成的非同步方法
     */
    static void staticMethodOfInstantlyCompleteNotSynchronized() {
        LOGGER.info("into staticMethodOfInstantlyCompleteNotSynchronized ...");
    }

}


/**
 * 对synchronized(this)的一些理解
 * <p>
 * 一、当两个并发线程访问同一个对象object中的这个synchronized(this)同步代码块时，一个时间内只能有一个线程得到执行。
 * 另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。
 * 二、然而，另一个线程仍然可以访问该object中的非synchronized(this)同步代码块。
 * 三、尤其关键的是，当一个线程访问object的一个synchronized(this)同步代码块时，其他线程对object中所有其它synchronized(this)同步代码块的访问将被阻塞。
 * 四、第三个例子同样适用其它同步代码块，它就获得了这个object的对象锁。结果，其它线程对该object对象所有同步代码部分的访问都被暂时阻塞。
 * 五、以上规则对其它对象锁同样适用。
 * <p>
 * 每个锁对象都有两个队列，一个是就绪队列，一个是阻塞队列，就绪队列存储了将要获得锁的线程，阻塞队列存储了被阻塞的线程，
 * 当一个线程被唤醒 (notify)后，才会进入到就绪队列，等待CPU的调度，
 * 反之，当一个线程被wait后，就会进入阻塞队列，等待下一次被唤醒，这个涉及到线程间的通 信，
 * 当第一个线程执行输出方法时，获得同步锁，执行输出方法，恰好此时第二个线程也要执行输出方法，
 * 但发现同步锁没有被 释放，第二个线程就会进入就绪队列，等待锁被释放。
 * <p>
 * 一个线程执行互斥代码过程如下：
 * 1. 获得同步锁；
 * 2. 清空工作内存；
 * 3. 从主内存拷贝对象副本到工作内存；
 * 4. 执行代码(计算或者输出等)；
 * 5. 刷新主内存数据；
 * 6. 释放同步锁。
 * 所以，synchronized不仅保证了多线程的内存可见性，也解决了线程的随机执行性的问题，即保证了多线程的并发有序性。
 *
 * @author wei.Li by 14-8-28.
 */
public class WhatLock_ {

    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(WhatLock_.class);

    private static ExecutorService executorService
            = Executors.newFixedThreadPool(10);

    /**
     * 静态方法访问
     * result：方法上的锁为当前 class 对象
     * <p>
     * 执行结果
     * ~~~~~~~~~ runStaticTest() Submit ticket start ~~~~~~~~~
     * into staticMethodOfLongRunning ...
     * ~~~~~~~~~ runStaticTest() Submit ticket end ~~~~~~~~~
     * into staticMethodOfInstantlyCompleteNotSynchronized ...
     * get out staticMethodOfLongRunning ...
     * into staticMethodOfInstantlyComplete ...
     */
    private static void runStaticTest() {
        LOGGER.info("~~~~~~~~~ runStaticTest() Submit ticket start ~~~~~~~~~ ");

        executorService.execute(StaticClass::staticMethodOfLongRunning);

        executorService.execute(StaticClass::staticMethodOfInstantlyComplete);

        executorService.execute(StaticClass::staticMethodOfInstantlyCompleteNotSynchronized);

        LOGGER.info("~~~~~~~~~ runStaticTest() Submit ticket end ~~~~~~~~~ ");

    }

    /**
     * 相同实例对象 访问同一耗时较长的同步方法
     * result：方法上的锁为当前实例对象的锁，出现竞争，先得到的先执行
     * <p>
     * 执行结果：
     * ~~~~~~~~~ runSameObjInstanceTest() Submit ticket start ~~~~~~~~~
     * ~~~~~~~~~ runSameObjInstanceTest() Submit ticket end ~~~~~~~~~
     * into instanceMethodOfLongRunning() ...             obj:<InstanceClass@2ac41c60>
     * get out instanceMethodOfLongRunning() ...          obj:<InstanceClass@2ac41c60>
     * into instanceMethodOfInstantlyCompleteNotSynchronized() ...             obj:<InstanceClass@2ac41c60>
     * into instanceMethodOfInstantlyComplete() ...             obj:<InstanceClass@2ac41c60>
     */
    private static void runSameObjInstanceTest() {

        LOGGER.info("~~~~~~~~~ runSameObjInstanceTest() Submit ticket start ~~~~~~~~~ ");

        InstanceClass instanceClass_A
                = new InstanceClass();

        executorService.execute(instanceClass_A::instanceMethodOfLongRunning);

        executorService.execute(instanceClass_A::instanceMethodOfInstantlyComplete);

        executorService.execute(instanceClass_A::instanceMethodOfInstantlyCompleteNotSynchronized);

        LOGGER.info("~~~~~~~~~ runSameObjInstanceTest() Submit ticket end ~~~~~~~~~ ");

    }


    /**
     * 不同实例对象 访问同一耗时较长的同步方法
     * result：方法上的锁为当前实例对象的锁
     * <p>
     * 执行结果：
     * ~~~~~~~~~ runDifferentObjInstanceTest() Submit ticket start ~~~~~~~~~
     * ~~~~~~~~~ runDifferentObjInstanceTest() Submit ticket end ~~~~~~~~~
     * into instanceMethodOfLongRunning() ...             obj:<InstanceClass@68cbb050>
     * into instanceMethodOfInstantlyComplete() ...       obj:<InstanceClass@720fdae1>
     * get out instanceMethodOfLongRunning() ...          obj:<InstanceClass@68cbb050>
     */
    private static void runDifferentObjInstanceTest() {

        LOGGER.info("~~~~~~~~~ runDifferentObjInstanceTest() Submit ticket start ~~~~~~~~~ ");

        InstanceClass instanceClass_A
                = new InstanceClass();
        InstanceClass instanceClass_B
                = new InstanceClass();

        executorService.execute(instanceClass_A::instanceMethodOfLongRunning);

        executorService.execute(instanceClass_B::instanceMethodOfInstantlyComplete);

        LOGGER.info("~~~~~~~~~ runDifferentObjInstanceTest() Submit ticket end ~~~~~~~~~ ");

    }

    //静态方法
    private static synchronized void aVoidStatic_() {
    }

    private static void aVoidStatic() {

        synchronized (WhatLock_.class) {
            //...
        }
    }

    public static void main(String[] args) {
        //runStaticTest();

        //runDifferentObjInstanceTest();
        runSameObjInstanceTest();
        executorService.shutdown();
    }

    //实例方法
    private synchronized void aVoid_() {
    }

    private void aVoid() {

        synchronized (this) {
            //...
        }
    }
}
/*
 * 1.对于静态方法的访问，同步方法上的锁为当前Class 对象，访问一个同步的静态方法时候其它同步静态方法被阻塞
 * 2.对于实例对象访问同步方法，方法上的锁为当前实例对象，例如：
 *    A a = new A() ; B b = new B();
 *    同步方法上的锁对应自己的 a,b锁(this)，因此a和 b 并发访问同一同步方法，互相不阻塞
 *   当同一实例对象并发访问同步方法时候，会产生阻塞，因为锁都是this.
 * 3.一切皆对象，回归到1，2
 * 4.注意：缩减同步代码块的范围、根据需求使用同一锁、避免各种阻塞影响性能
 */


