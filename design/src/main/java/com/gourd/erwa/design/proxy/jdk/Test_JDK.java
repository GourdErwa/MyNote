package designmodel.proxy.jdk;


/**
 * @author lw by 14-5-1.
 */
public class Test_JDK {

    public static void main(String[] args) {
        BookFacadeJDKProxy proxy_jdk = new BookFacadeJDKProxy();

        IBookFacadeJDK bookFacade
                = (IBookFacadeJDK) proxy_jdk.bind(new IBookFacadeJDKImpl());

        bookFacade.seeBook();
    }

}
