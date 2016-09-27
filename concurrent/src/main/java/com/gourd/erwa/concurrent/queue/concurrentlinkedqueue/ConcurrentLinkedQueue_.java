/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/xiaohulu
 * Blog  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 */

package com.gourd.erwa.concurrent.queue.concurrentlinkedqueue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 并发队列
 * <p>
 * 一个基于链接节点的无界线程安全队列。
 * <p>
 * 此队列按照 FIFO（先进先出）原则对元素进行排序。
 * 队列的头部 是队列中时间最长的元素。队列的尾部 是队列中时间最短的元素。
 * 新的元素插入到队列的尾部，队列获取操作从队列头部获得元素。当多个线程共享访问一个公共 collection 时，ConcurrentLinkedQueue 是一个恰当的选择。
 * 此队列不允许使用 null 元素。
 *
 * @author wei.Li by 14-8-28.
 * @see thread.concurrent.queue.linkedblockingqueue.LinkedBlockingQueue_  阻塞队列
 */
public class ConcurrentLinkedQueue_ {

    private static ConcurrentLinkedQueue concurrentLinkedQueue
            = new ConcurrentLinkedQueue();

    /*
     * 方法摘要
     * boolean	add(E e)
     * 将指定元素插入此队列的尾部。
     *
     * boolean	contains(Object o)
     * 如果此队列包含指定元素，则返回 true。
     *
     * boolean	isEmpty()
     * 如果此队列不包含任何元素，则返回 true。
     *
     * Iterator<E>	iterator()
     * 返回在此队列元素上以恰当顺序进行迭代的迭代器。
     *
     * boolean	offer(E e)
     * 将指定元素插入此队列的尾部。
     *
     * E	peek()
     * 获取但不移除此队列的头；如果此队列为空，则返回 null。
     *
     * E	poll()
     * 获取并移除此队列的头，如果此队列为空，则返回 null。
     *
     * boolean	remove(Object o)
     * 从队列中移除指定元素的单个实例（如果存在）。
     *
     * int	size()
     * 返回此队列中的元素数量。
     *
     * Object[]	toArray()
     * 返回以恰当顺序包含此队列所有元素的数组。
     *
     * <T> T[]
     * toArray(T[] a)
     * 返回以恰当顺序包含此队列所有元素的数组；返回数组的运行时类型是指定数组的运行时类型。
     */
    private static void concurrentLinkedQueue_() {

        concurrentLinkedQueue.add("a");
        concurrentLinkedQueue.add("b");

        //返回此队列中的元素数量。如果此队列包含的元素数大于 Integer.MAX_VALUE，则返回 Integer.MAX_VALUE。
        //需要小心的是，与大多数 collection 不同，此方法不是 一个固定时间操作。
        // 由于这些队列的异步特性，确定当前的元素数需要进行一次花费 O(n) 时间的遍历。
        concurrentLinkedQueue.size();

        //使用 isEmpty 判断是否为空
        concurrentLinkedQueue.isEmpty();
    }
}
