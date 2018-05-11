package com.gourd.erwa.corejava.exception;

/**
 * @author wei.Li by 2017/3/7
 */
public class ExceptionThrow {


    public static void main(String[] args) {

        try {
            throw new ExceptionB();
        } catch (Exception a) {
            a.printStackTrace();
        }
    }

}

class ExceptionA extends Exception {

    private static final long serialVersionUID = -4924914164494203290L;
}

class ExceptionB extends ExceptionA {

    private static final long serialVersionUID = -1645945813147169377L;
}
