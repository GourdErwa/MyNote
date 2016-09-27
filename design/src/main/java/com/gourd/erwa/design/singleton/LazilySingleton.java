package designmodel.singleton;

/**
 * Created by lw on 14-4-30.
 * 懒汉式加载
 */
public class LazilySingleton {
    private static LazilySingleton lazilySingleton = null;

    private LazilySingleton() {
    }

    public static synchronized LazilySingleton getSingleton() {
        if (lazilySingleton == null) {
            lazilySingleton = new LazilySingleton();
        }
        return lazilySingleton;
    }
}
