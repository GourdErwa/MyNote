package com.gourd.erwa.design.proxy.jdk;


/**
 * @author lw by 14-5-1.
 */
class testJDK {

    public static void main(String[] args) {
        BookFacadeJDKProxy bookFacadeJDKProxy = new BookFacadeJDKProxy();

        IBookFacadeJDK bookFacade
                = (IBookFacadeJDK) bookFacadeJDKProxy.bind(new IBookFacadeJDKImpl());

        bookFacade.seeBook();
    }

}
