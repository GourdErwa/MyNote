package com.gourd.erwa.concurrent.basis;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wei.Li by 14-8-25.
 */
public class AtomicTest {

    private static AtomicInteger atomicInteger
            = new AtomicInteger(0);

    public static void main(String[] args) {
        atomicInteger.getAndIncrement();
        atomicInteger.incrementAndGet();
        System.out.println(atomicInteger);
    }
}
