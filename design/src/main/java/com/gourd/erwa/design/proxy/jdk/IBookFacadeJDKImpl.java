package com.gourd.erwa.design.proxy.jdk;

/**
 * @author lw by 14-5-1.
 */
class IBookFacadeJDKImpl implements IBookFacadeJDK {

    @Override
    public void seeBook() {
        System.out.println("BookFacadeJDKImpl_JDK see book ing ...");
    }

}
