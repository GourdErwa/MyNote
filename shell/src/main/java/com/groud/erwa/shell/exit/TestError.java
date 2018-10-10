package com.groud.erwa.shell.exit;

/**
 * @author wei.Li by 2018/10/10
 */
public class TestError {

    public static void main(String[] args) {

        System.out.println(TestError.class.getName() + " invoke");
        throw new Error("error");
    }
}
