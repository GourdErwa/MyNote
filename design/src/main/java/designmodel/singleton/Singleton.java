package designmodel.singleton;

/**
 * Created by lw on 14-4-30.
 * 饿汉式加载
 */
public class Singleton {

    private static Singleton singleton = new Singleton();

    private Singleton() {
    }

    public static Singleton getSingleton() {
        return singleton;
    }
}
