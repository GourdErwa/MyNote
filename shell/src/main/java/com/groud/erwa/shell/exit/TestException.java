package com.groud.erwa.shell.exit;

/**
 * @author wei.Li by 2018/10/10
 */
public class TestException {

    public static void main(String[] args) throws Exception {

        System.out.println(TestException.class.getName() + " invoke");
        throw new Exception("Exception");
    }
}
