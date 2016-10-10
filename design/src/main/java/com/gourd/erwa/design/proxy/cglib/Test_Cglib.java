package com.gourd.erwa.design.proxy.cglib;


/**
 * @author lw by 14-5-1.
 */
class Test_Cglib {

    public static void main(String[] args) {
        BookFacadeCglibProxy proxy_cglib = new BookFacadeCglibProxy();

        BookFacadeCglib bookImpl
                = (BookFacadeCglib) proxy_cglib.getInstance(new BookFacadeCglib());

        bookImpl.seeBook();
    }
}
