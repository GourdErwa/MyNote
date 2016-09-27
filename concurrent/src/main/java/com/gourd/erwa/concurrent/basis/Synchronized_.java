package com.gourd.erwa.concurrent.basis;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-6
 * Time: 16:55
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 对synchronized(this)的一些理解
 * <p>透析Java本质-谁创建了对象,this是什么
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
 */
public class Synchronized_ {
    public static void main(String[] args) {
        final OutPuter outPuter = new OutPuter();
        outPuter.aVoid2SetValue();
        outPuter.aVoid2GetValue();
    }
}

class OutPuter {

    private static List<String> STRING_LIST = new ArrayList<>();

    private void output(String name) {
        // 为了保证对name的输出不是一个原子操作，这里逐个的输出的name的每个字符
        for (int i = 0; i < name.length(); i++) {
            System.out.print(name.charAt(i));
        }
    }

    public void aVoid2Output() {
        final OutPuter output = new OutPuter();
        new Thread() {
            public void run() {
                output.output("11111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111111111111111111111111" +
                        "111111111111111111111111111111111111111111111");
            }
        }.start();

        new Thread() {
            public void run() {
                output.output("222");
            }
        }.start();
/**
 * aVoid2Output（）输出一次：
 * 1122211111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
 *
 * 这就是线程同步问题，我们希望output方法被一个线程完整的执行完之后再切换到下一个线程，Java中使用synchronized保证一段代码在多线程执行时是互斥的，
 * 有两种用法：
 *  1. 使用synchronized将需要互斥的代码包含起来，并上一把锁。
 synchronized (this) {
 for(int i = 0; i < name.length(); i++) {
 System.out.print(name.charAt(i));
 }
 }
 *  2.将synchronized加在需要互斥的方法上。
 public synchronized void output(String name) {...}
 */
    }

    void aVoid2SetValue() {
       /* Runnable runnable;
        runnable = () -> {
            for (int i = 0; i < 100; i++) {
                List<String> list = new ArrayList<>();
                list.add("currentThread -> " + i);
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                STRING_LIST = list;
                System.out.println("Update Value ...");

            }
        };


        Thread thread = new Thread(runnable);
        thread.start();*/
    }


    void aVoid2GetValue() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(new Random().nextInt(2000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Read Value : " + STRING_LIST);

                }
            }
        };


        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }
}


