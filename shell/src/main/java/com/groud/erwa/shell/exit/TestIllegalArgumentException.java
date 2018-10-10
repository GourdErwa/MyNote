package com.groud.erwa.shell.exit;

/**
 * @author wei.Li by 2018/10/10
 */
public class TestIllegalArgumentException {

    public static void main(String[] args) {

        System.out.println(TestIllegalArgumentException.class.getName() + " invoke");
        throw new IllegalArgumentException();
    }
}
