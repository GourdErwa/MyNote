package com.test.cm;

interface Son {
    void getSon();
}

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-7-27
 * Time: 15:55
 */
public class Anonymous {
    public static void main(String[] args) {
        System.out.printf("------Anonymous-------");
        Anonymous Anonymous = new Anonymous();
    }

    public Son getSon() {
        return new Son() {
            @Override
            public void getSon() {
                System.out.println("Son匿名内部类");
            }
        };
    }
}
