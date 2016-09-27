package com.gourd.erwa.concurrent.queue.linkedblockingqueue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 * <p>
 * 一个基于已链接节点的、范围任意的 blocking queue。此队列按 FIFO（先进先出）排序元素。
 * 队列的头部 是在队列中时间最长的元素。队列的尾部 是在队列中时间最短的元素。
 * 新元素插入到队列的尾部，并且队列获取操作会获得位于队列头部的元素。
 * 链接队列的吞吐量通常要高于基于数组的队列，但是在大多数并发应用程序中，其可预知的性能要低。
 * <p>
 * 可选的容量范围构造方法参数作为防止队列过度扩展的一种方法。如果未指定容量，则它等于 Integer.MAX_VALUE。
 * 除非插入节点会使队列超出容量，否则每次插入后会动态地创建链接节点。
 *
 * @author wei.L by 14-7-22.
 * @see thread.concurrent.queue.concurrentlinkedqueue.ConcurrentLinkedQueue_ 并发队列
 */
public class LinkedBlockingQueue_ {


    private static void linkedBlockingQueue2Void() {

        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
        //将指定元素插入到此队列的尾部（如果立即可行且不会超出此队列的容量），在成功时返回 true，如果此队列已满，则返回 false。
        boolean isOffer = queue.offer("aa");
        queue.offer("ee");

        try {
            // 等待1s
            isOffer = queue.offer("bb", 1000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("offer(E e, long timeout, TimeUnit unit) run ... 在等待时被中断...");
        }

        //获取但不移除此队列的头；如果此队列为空，则返回 null。
        String head = queue.peek();

        //获取并移除此队列的头，如果此队列为空，则返回 null。
        head = queue.poll();

        try {
            //获取并移除此队列的头部，在指定的等待时间前等待可用的元素（如果有必要）。
            head = queue.poll(1000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("poll(long timeout, TimeUnit unit) run ... 在等待时被中断...");
        }

        try {
            //TODO 获取并移除此队列的头部，在元素变得可用之前一直等待（如果有必要）。
            queue.take();
        } catch (InterruptedException e) {
            System.out.println("take()  run ... 在等待时被中断...");
        }
        try {
            //将指定元素插入到此队列的尾部，如有必要，则等待空间变得可用。
            queue.put("cc");
        } catch (InterruptedException e) {
            System.out.println("put(E e) run ... 在等待时被中断...");
        }

        //返回理想情况下（没有内存和资源约束）此队列可接受并且不会被阻塞的附加元素数量。
        int remainingCapacity2Size = queue.remainingCapacity();

        //移除元素，如果存在
        queue.remove("dd");

        queue.size();//return count.get();

    }

    public static void main(String[] args) {
        linkedBlockingQueue2Void();

    }
}
