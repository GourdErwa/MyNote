package designmodel.proxy.jdk;

/**
 * @author lw by 14-5-1.
 */
public class IBookFacadeJDKImpl implements IBookFacadeJDK {

    @Override
    public void seeBook() {
        System.out.println("BookFacadeJDKImpl_JDK see book ing ...");
    }

}
