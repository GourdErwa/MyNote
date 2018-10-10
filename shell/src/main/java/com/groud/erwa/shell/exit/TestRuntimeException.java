package com.groud.erwa.shell.exit;

/**
 * @author wei.Li by 2018/10/10
 */
public class TestRuntimeException {

    public static void main(String[] args) {

        System.out.println(TestRuntimeException.class.getName() + " invoke");
        throw new RuntimeException("RuntimeException");
    }
}
