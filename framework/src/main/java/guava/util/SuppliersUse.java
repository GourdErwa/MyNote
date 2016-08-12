package guava.util;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import java.util.concurrent.TimeUnit;

/**
 * @author wei.Li
 */
public class SuppliersUse {

    private static final Supplier<Integer> DELEGATE = new Supplier<Integer>() {
        @Override
        public Integer get() {
            System.out.println("get method run ...");
            return 1;
        }
    };

    public static void main(String[] args) {

        memoizeUse();
        memoizeWithExpirationUse();

        //making it thread-safe.
        final Supplier<Integer> integerSupplier = Suppliers.synchronizedSupplier(DELEGATE);
        //Suppliers.ofInstance()
    }

    /**
     * 第一次get()的时候，它会调用真正Supplier，得到结果并保存下来，下次再访问就返回这个保存下来的值
     */
    private static void memoizeUse() {


        final Supplier<Integer> memoize = Suppliers.memoize(DELEGATE);

        System.out.println(memoize);
        for (int i = 0; i < 10; i++) {
            System.out.println(memoize.get());
        }
    }

    /**
     * 只在一段时间内是有效的，Guava还给我们提供了一个另一个函数，让我们可以设定过期时间
     */
    private static void memoizeWithExpirationUse() {

        final Supplier<Integer> memoizeWithExpiration = Suppliers.memoizeWithExpiration(
                DELEGATE,
                2L,
                TimeUnit.SECONDS);

        System.out.println(memoizeWithExpiration);
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ignored) {
            }
            System.out.println(memoizeWithExpiration.get());
        }

    }

}
