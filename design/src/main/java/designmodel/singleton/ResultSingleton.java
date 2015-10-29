package designmodel.singleton;

/**
 * Created by lw on 14-4-30.
 * 内部类式加载
 */
public class ResultSingleton {

    private ResultSingleton() {
    }

    public static ResultSingleton getSingleton() {
        return Singleton.resultSingleton;
    }

    private static class Singleton {

        private static ResultSingleton resultSingleton = new ResultSingleton();
    }
}
